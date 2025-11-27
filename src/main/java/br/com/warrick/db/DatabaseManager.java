package br.com.warrick.db;

import br.com.warrick.util.PasswordHasher;
import java.sql.*;
import java.util.Properties;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/db/app_database.db";
    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() {
        try {
            Class.forName("org.sqlite.JDBC");
            createDatabase();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Falha ao carregar o driver do SQLite", e);
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            Properties props = new Properties();
            connection = DriverManager.getConnection(DB_URL, props);
        }
        return connection;
    }

    private void createDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Cria a tabela de usuários se não existir
            String sql = "CREATE TABLE IF NOT EXISTS usuarios (\n"
                      + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                      + " usuario TEXT NOT NULL UNIQUE,\n"
                      + " senha TEXT NOT NULL,\n"
                      + " nome TEXT NOT NULL,\n"
                      + " email TEXT NOT NULL UNIQUE,\n"
                      + " token_recuperacao TEXT,\n"
                      + " data_expiracao_token TIMESTAMP,\n"
                      + " data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP\n"
                      + ");";
            
            stmt.execute(sql);
            
            // Insere um usuário padrão se a tabela estiver vazia
            if (isTableEmpty("usuarios")) {
                insertDefaultUser();
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao criar banco de dados: " + e.getMessage());
        }
    }
    
    private boolean isTableEmpty(String tableName) throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM " + tableName)) {
            return rs.next() && rs.getInt("count") == 0;
        }
    }
    
    private void insertDefaultUser() {
        String sql = "INSERT INTO usuarios (usuario, senha, nome, email, data_criacao) VALUES (?, ?, ?, ?, DATETIME('now'))";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Gera um hash seguro para a senha
            String senhaHash = PasswordHasher.hash("admin123");
            
            pstmt.setString(1, "admin");
            pstmt.setString(2, senhaHash);
            pstmt.setString(3, "Administrador");
            pstmt.setString(4, "admin@example.com");
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário padrão: " + e.getMessage());
        }
    }
    
    /**
     * Valida as credenciais do usuário.
     * 
     * @param usuario Nome de usuário
     * @param senha Senha em texto puro
     * @return true se as credenciais forem válidas, false caso contrário
     */
    public boolean validarUsuario(String usuario, String senha) {
        String sql = "SELECT senha FROM usuarios WHERE usuario = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String senhaHash = rs.getString("senha");
                    return PasswordHasher.verificar(senha, senhaHash);
                }
                return false; // Usuário não encontrado
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao validar usuário: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Cadastra um novo usuário no sistema.
     * 
     * @param usuario Nome de usuário
     * @param senha Senha em texto puro (será convertida para hash)
     * @param nome Nome completo do usuário
     * @param email Email do usuário
     * @return true se o cadastro for bem-sucedido, false caso contrário
     */
    public boolean cadastrarUsuario(String usuario, String senha, String nome, String email) {
        // Verifica se o usuário já existe
        if (usuarioExiste(usuario, email)) {
            return false;
        }
        
        String sql = "INSERT INTO usuarios (usuario, senha, nome, email, data_criacao) VALUES (?, ?, ?, ?, DATETIME('now'))";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Gera o hash da senha
            String senhaHash = PasswordHasher.hash(senha);
            
            pstmt.setString(1, usuario);
            pstmt.setString(2, senhaHash);
            pstmt.setString(3, nome);
            pstmt.setString(4, email);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Verifica se um usuário ou email já está cadastrado.
     */
    private boolean usuarioExiste(String usuario, String email) {
        String sql = "SELECT COUNT(*) as count FROM usuarios WHERE usuario = ? OR email = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario);
            pstmt.setString(2, email);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt("count") > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao verificar usuário existente: " + e.getMessage());
            return true; // Em caso de erro, assume que o usuário existe para evitar duplicações
        }
    }
    
    /**
     * Inicia o processo de recuperação de senha.
     * 
     * @param email Email do usuário
     * @return Um token de recuperação ou null se o email não existir
     */
    public String iniciarRecuperacaoSenha(String email) {
        // Gera um token único
        String token = java.util.UUID.randomUUID().toString();
        
        String sql = "UPDATE usuarios SET token_recuperacao = ?, data_expiracao_token = DATETIME('now', '+1 hour') " +
                    "WHERE email = ? AND (token_recuperacao IS NULL OR data_expiracao_token < DATETIME('now'))";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, token);
            pstmt.setString(2, email);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0 ? token : null;
            
        } catch (SQLException e) {
            System.err.println("Erro ao iniciar recuperação de senha: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Redefine a senha do usuário usando um token de recuperação.
     * 
     * @param token Token de recuperação
     * @param novaSenha Nova senha em texto puro
     * @return true se a senha foi redefinida com sucesso
     */
    public boolean redefinirSenha(String token, String novaSenha) {
        String sql = "UPDATE usuarios SET senha = ?, token_recuperacao = NULL, data_expiracao_token = NULL " +
                    "WHERE token_recuperacao = ? AND data_expiracao_token > DATETIME('now')";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String senhaHash = PasswordHasher.hash(novaSenha);
            pstmt.setString(1, senhaHash);
            pstmt.setString(2, token);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao redefinir senha: " + e.getMessage());
            return false;
        }
    }
    
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}
