package br.com.warrick.dao;

import br.com.warrick.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de Acesso a Dados (DAO) para a entidade Usuário.
 * Responsável por todas as operações de banco de dados relacionadas a usuários.
 */
public class UsuarioDAO {
    
    private Connection conexao;
    
    // Queries SQL
    private static final String INSERIR = "INSERT INTO usuarios (nome, email, senha, ativo) VALUES (?, ?, ?, ?)";
    private static final String BUSCAR_POR_EMAIL = "SELECT * FROM usuarios WHERE email = ?";
    private static final String BUSCAR_POR_ID = "SELECT * FROM usuarios WHERE id = ?";
    private static final String LISTAR_TODOS = "SELECT * FROM usuarios";
    private static final String ATUALIZAR = "UPDATE usuarios SET nome = ?, email = ?, senha = ?, ativo = ? WHERE id = ?";
    private static final String EXCLUIR = "DELETE FROM usuarios WHERE id = ?";
    
    public UsuarioDAO(Connection conexao) {
        this.conexao = conexao;
    }
    
    /**
     * Salva um novo usuário no banco de dados.
     * @param usuario O usuário a ser salvo
     * @return O usuário salvo com o ID gerado
     * @throws SQLException Em caso de erro no banco de dados
     */
    public Usuario salvar(Usuario usuario) throws SQLException {
        try (PreparedStatement stmt = conexao.prepareStatement(INSERIR, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setBoolean(4, usuario.isAtivo());
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        usuario.setId(rs.getInt(1));
                    }
                }
            }
            
            return usuario;
        }
    }
    
    /**
     * Busca um usuário pelo email.
     * @param email O email do usuário a ser buscado
     * @return O usuário encontrado ou null se não existir
     * @throws SQLException Em caso de erro no banco de dados
     */
    public Usuario buscarPorEmail(String email) throws SQLException {
        try (PreparedStatement stmt = conexao.prepareStatement(BUSCAR_POR_EMAIL)) {
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarUsuarioAPartirResultSet(rs);
                }
            }
        }
        return null;
    }
    
    /**
     * Busca um usuário pelo ID.
     * @param id O ID do usuário a ser buscado
     * @return O usuário encontrado ou null se não existir
     * @throws SQLException Em caso de erro no banco de dados
     */
    public Usuario buscarPorId(int id) throws SQLException {
        try (PreparedStatement stmt = conexao.prepareStatement(BUSCAR_POR_ID)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarUsuarioAPartirResultSet(rs);
                }
            }
        }
        return null;
    }
    
    /**
     * Lista todos os usuários do sistema.
     * @return Uma lista de usuários
     * @throws SQLException Em caso de erro no banco de dados
     */
    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        
        try (PreparedStatement stmt = conexao.prepareStatement(LISTAR_TODOS);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                usuarios.add(criarUsuarioAPartirResultSet(rs));
            }
        }
        
        return usuarios;
    }
    
    /**
     * Atualiza um usuário existente.
     * @param usuario O usuário com os dados atualizados
     * @return true se a atualização foi bem-sucedida, false caso contrário
     * @throws SQLException Em caso de erro no banco de dados
     */
    public boolean atualizar(Usuario usuario) throws SQLException {
        try (PreparedStatement stmt = conexao.prepareStatement(ATUALIZAR)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setBoolean(4, usuario.isAtivo());
            stmt.setInt(5, usuario.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Exclui um usuário pelo ID.
     * @param id O ID do usuário a ser excluído
     * @return true se a exclusão foi bem-sucedida, false caso contrário
     * @throws SQLException Em caso de erro no banco de dados
     */
    public boolean excluir(int id) throws SQLException {
        try (PreparedStatement stmt = conexao.prepareStatement(EXCLUIR)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Cria um objeto Usuario a partir de um ResultSet.
     * @param rs O ResultSet contendo os dados do usuário
     * @return Um objeto Usuario populado
     * @throws SQLException Em caso de erro ao acessar o ResultSet
     */
    private Usuario criarUsuarioAPartirResultSet(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setAtivo(rs.getBoolean("ativo"));
        return usuario;
    }
}
