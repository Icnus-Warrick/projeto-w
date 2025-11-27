package br.com.warrick.view;

import br.com.warrick.swing.*;
import br.com.warrick.db.DatabaseManager;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.*;
import java.util.Objects;
import javax.swing.*;

/**
 * Painel de login/registro com transição suave entre os formulários.
 * 
 * <p>Este componente é responsável por:</p>
 * <ul>
 *   <li>Exibir formulários de login e registro</li>
 *   <li>Gerenciar a transição entre os formulários</li>
 *   <li>Fornecer acesso aos campos de entrada e botões</li>
 *   <li>Manter o estado dos formulários</li>
 * </ul>
 *
 * @author Warrick
 * @version 1.0.0
 * @since 23/11/2025
 */
public class LoginPainel extends JPanel {

    // ============================================ CONSTANTES DE LAYOUT ============================================

    /**
     * Dimensões do painel
     */
    private static final int LARGURA_PAINEL = 350;
    private static final int ALTURA_PAINEL = 500;
    private static final int ARREDONDAMENTO = 20;

    /**
     * Constantes de posicionamento do título
     */
    private static final int TITULO_X = 100;
    private static final int TITULO_Y = 60;
    private static final int TITULO_WIDTH = 170;
    private static final int TITULO_HEIGHT = 40;

    /**
     * Constantes de posicionamento dos campos de entrada
     */
    private static final int CAMPO_X = 35;
    private static final int CAMPO_1_Y = 140;
    private static final int CAMPO_2_Y = 220;
    private static final int CAMPO_3_Y = 280;
    private static final int CAMPO_WIDTH = 280;
    private static final int CAMPO_HEIGHT = 50;

    /**
     * Constantes de posicionamento do botão
     */
    private static final int BOTAO_X = 35;
    private static final int BOTAO_Y = 340;
    private static final int BOTAO_WIDTH = 280;
    private static final int BOTAO_HEIGHT = 40;

    /**
     * Constantes de posicionamento do link "Esqueceu a senha?"
     */
    private static final int ESQUECEU_Y = 280;

    /**
     * Obtém uma cor do tema atual ou retorna a cor padrão se não estiver definida.
     *
     * @param key Chave da cor no tema
     * @param defaultColor Cor padrão a ser retornada se a chave não existir
     * @return A cor do tema ou a cor padrão
     */
    private Color getThemeColor(String key, Color defaultColor) {
        try {
            Color themeColor = UIManager.getColor(key);
            return themeColor != null ? themeColor : defaultColor;
        } catch (Exception e) {
            return defaultColor;
        }
    }
    
    /**
     * Cores padrão (usadas como fallback)
     */
    private static final Color DEFAULT_BG_COLOR = UIManager.getColor("Panel.background");
    private static final Color DEFAULT_TITLE_COLOR = new Color(51, 102, 255);
    private static final Color DEFAULT_TEXT_COLOR = UIManager.getColor("Label.foreground");
    private static final Color DEFAULT_BUTTON_BG = UIManager.getColor("Button.background");
    private static final Color DEFAULT_BUTTON_FG = UIManager.getColor("Button.foreground");

    // ================================================== ATRIBUTOS =================================================

    /**
     * Painéis de login e registro
     */
    private final JPanel painelLogin;
    private final JPanel painelRegistro;

    // Componentes do painel de login
    private JLabel lblTituloLogin;
    private WTextField txtUsuario;
    private WPasswordField txtSenhaLogin;
    private JButton btnLogin;
    private JLabel lblEsqueceu;

    // Componentes do painel de registro
    private JLabel lblTituloRegistro;
    private WTextField txtNome;
    private WTextField txtEmail;
    private WPasswordField txtSenhaRegistro;
    private JButton btnRegistrar;

    // ================================================= CONSTRUTOR =================================================

    /**
     * Constrói uma nova instância do painel de login/registro.
     * Inicializa os componentes e configura o layout.
     *
     * @throws IllegalStateException se ocorrer um erro durante a inicialização
     */
    public LoginPainel() {
        try {
            configurarPainelPrincipal();

            // Cria os painéis de login e registro
            painelLogin = criarPainelLogin();
            painelRegistro = criarPainelRegistro();

            // Configura o gerenciador de layout e adiciona os painéis
            setLayout(new CardLayout());
            add(painelLogin, "login");
            add(painelRegistro, "registro");

            // Mostra o painel de login por padrão
            mostrarPainelLogin();
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao inicializar o painel de login/registro", e);
        }
    }

    // ============================================== MÉTODOS PRIVADOS ==============================================

    /**
     * Configura as propriedades básicas do painel principal.
     * Define o fundo transparente, tamanho preferencial e estilo de borda arredondada.
     */
    private void configurarPainelPrincipal() {
        try {
            setLayout(null);
            setBackground(new Color(0, 0, 0,0));
            setPreferredSize(new Dimension(LARGURA_PAINEL, ALTURA_PAINEL));
            putClientProperty(FlatClientProperties.STYLE, "arc: 20");
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao configurar o painel principal", e);
        }
    }

    /**
     * Cria e configura o painel de login com todos os seus componentes.
     *
     * @return JPanel configurado para o formulário de login
     * @throws IllegalStateException se ocorrer um erro durante a criação do painel
     */
    private JPanel criarPainelLogin() {
        try {
            JPanel painel = new JPanel(null);
            painel.putClientProperty(FlatClientProperties.STYLE, ""
                    + "arc: 20");

            // Título
            lblTituloLogin = criarRotulo("LOGIN", TITULO_X, TITULO_Y, TITULO_WIDTH, TITULO_HEIGHT, true);
            painel.add(lblTituloLogin);

            // Configuração do campo de usuário
            txtUsuario = criarCampoTexto("USUÁRIO", CAMPO_X, CAMPO_1_Y, CAMPO_WIDTH, CAMPO_HEIGHT);
           // txtUsuario.setObrigatorio(true);
            painel.add(txtUsuario);

            // Campo de senha
            txtSenhaLogin = criarCampoSenha("SENHA", CAMPO_X, CAMPO_2_Y, CAMPO_WIDTH, CAMPO_HEIGHT);
            painel.add(txtSenhaLogin);

            // Botão de login
            btnLogin = criarBotao("LOGIN", BOTAO_X, BOTAO_Y, BOTAO_WIDTH, BOTAO_HEIGHT);
            btnLogin.addActionListener(e -> {
                // Limpa mensagens de erro anteriores
                txtUsuario.limparMensagem();
               // txtSenhaLogin.limparMensagem();
                
                // Valida os campos
                boolean usuarioValido = !txtUsuario.getText().trim().isEmpty();
                boolean senhaValida = !new String(txtSenhaLogin.getPassword()).trim().isEmpty();
                
                if (!usuarioValido) {
                    txtUsuario.mostrarErro("Por favor, insira o usuário");
                }
                
                if (!senhaValida) {
                    txtSenhaLogin.mostrarErro("Por favor, insira a senha");
                }
                
                if (usuarioValido) {
                    // Validar usuário no banco de dados
                    String usuario = txtUsuario.getText();
                    String senha = new String(txtSenhaLogin.getPassword());
                    
                    try {
                        boolean autenticado = DatabaseManager.getInstance().validarUsuario(usuario, senha);
                        
                        if (autenticado) {
                            // TODO: Mostrar mensagem global de sucesso
                            // Exemplo: abrirTelaPrincipal();
                        } else {
                            // TODO: Mostrar mensagem global de credenciais inválidas
                            txtSenhaLogin.requestFocus();
                            txtSenhaLogin.selectAll();
                        }
                    } catch (Exception ex) {
                        // TODO: Mostrar mensagem global de erro na conexão
                        ex.printStackTrace();
                    }
                }
           //     if (senhaValida) {
                    // Aqui você pode adicionar a lógica de autenticação
            //        String senha = new String(txtSenhaLogin.getPassword());
                    // TODO: Implementar lógica de autenticação
            //    }


            });
            painel.add(btnLogin);

            // Configuração do link "Esqueceu a senha?"
            lblEsqueceu = new JLabel("Esqueceu a senha?");
            lblEsqueceu.setBounds(CAMPO_X, ESQUECEU_Y, CAMPO_WIDTH, 20);
            lblEsqueceu.setForeground(getThemeColor("Component.linkColor", DEFAULT_TITLE_COLOR));
            lblEsqueceu.setHorizontalAlignment(SwingConstants.RIGHT);
            lblEsqueceu.setCursor(new Cursor(Cursor.HAND_CURSOR));
           // lblEsqueceu.putClientProperty(FlatClientProperties.STYLE, "border: 0,0,0,0,shade($Component.linkColor,10%),,0");
            painel.add(lblEsqueceu);

            return painel;
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao criar o painel de login", e);
        }
    }

    /**
     * Cria e configura o painel de registro com todos os seus componentes.
     *
     * @return JPanel configurado para o formulário de registro
     * @throws IllegalStateException se ocorrer um erro durante a criação do painel
     */
    private JPanel criarPainelRegistro() {
        try {
            JPanel painel = new JPanel(null);
           // painel.setBackground(getThemeColor("Panel.background", DEFAULT_BG_COLOR));
            painel.putClientProperty(FlatClientProperties.STYLE, "arc: 20");

            // Título
            lblTituloRegistro = criarRotulo("Crie uma Conta", 50, TITULO_Y, 250, TITULO_HEIGHT, true);
            painel.add(lblTituloRegistro);

            // Campos de entrada
            txtNome = criarCampoTexto("Nome", CAMPO_X, CAMPO_1_Y, CAMPO_WIDTH, CAMPO_HEIGHT);
            txtEmail = criarCampoTexto("Email", CAMPO_X, CAMPO_2_Y, CAMPO_WIDTH, CAMPO_HEIGHT);
            txtSenhaRegistro = criarCampoSenha("Senha", CAMPO_X, CAMPO_3_Y, CAMPO_WIDTH, CAMPO_HEIGHT);

            painel.add(txtNome);
            painel.add(txtEmail);
            painel.add(txtSenhaRegistro);

            // Botão de registro
            btnRegistrar = criarBotao("REGISTRAR", BOTAO_X, BOTAO_Y, BOTAO_WIDTH, BOTAO_HEIGHT);
            painel.add(btnRegistrar);

            return painel;
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao criar o painel de registro", e);
        }
    }

    /**
     * Cria e configura um rótulo (JLabel) com as propriedades especificadas.
     *
     * @param texto  Texto a ser exibido no rótulo
     * @param x      Posição X do rótulo
     * @param y      Posição Y do rótulo
     * @param width  Largura do rótulo
     * @param height Altura do rótulo
     * @param titulo Se true, aplica formatação de título (fonte maior e cor de destaque)
     * @return JLabel configurado
     * @throws IllegalArgumentException se os parâmetros forem inválidos
     */
    private JLabel criarRotulo(String texto, int x, int y, int width, int height, boolean titulo) {
        try {
            Objects.requireNonNull(texto, "O texto do rótulo não pode ser nulo");
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("Largura e altura devem ser maiores que zero");
            }

            JLabel rotulo = new JLabel(texto, SwingConstants.CENTER);
            if (titulo) {
                rotulo.setFont(UIManager.getFont("h2.font"));
                rotulo.setForeground(getThemeColor("WTextField.lineColor", DEFAULT_TITLE_COLOR));
            } else {
                rotulo.setForeground(getThemeColor("Label.foreground", DEFAULT_TEXT_COLOR));
            }
            rotulo.setBounds(x, y, width, height);
            return rotulo;
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao criar o rótulo: " + e.getMessage(), e);
        }
    }

    /**
     * Cria e configura um campo de texto com o estilo padrão do painel.
     *
     * @param placeholder Texto de dica do campo
     * @param x           Posição X do campo
     * @param y           Posição Y do campo
     * @param width       Largura do campo
     * @param height      Altura do campo
     * @return WTextField configurado
     * @throws IllegalArgumentException se os parâmetros forem inválidos
     */
    private WTextField criarCampoTexto(String placeholder, int x, int y, int width, int height) {
        try {
            Objects.requireNonNull(placeholder, "O placeholder não pode ser nulo");
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("Largura e altura devem ser maiores que zero");
            }

            WTextField campo = new WTextField();
            campo.setLabelText(placeholder); // Define o texto do rótulo flutuante
            campo.setBounds(x, y, width, height);
            campo.setBackground(getThemeColor("TextField.background", Color.WHITE));
            campo.setForeground(getThemeColor("TextField.foreground", Color.BLACK));
            campo.setCaretColor(getThemeColor("TextField.caretForeground", Color.BLACK));
            return campo;
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao criar o campo de texto: " + e.getMessage(), e);
        }
    }

    /**
     * Cria e configura um campo de senha com as propriedades especificadas.
     * Inclui um botão para alternar a visibilidade da senha.
     *
     * @param placeholder Texto de sugestão exibido quando o campo está vazio
     * @param x           Posição X do campo
     * @param y           Posição Y do campo
     * @param width       Largura do campo
     * @param height      Altura do campo
     * @return WPasswordField configurado
     * @throws IllegalArgumentException se os parâmetros forem inválidos
     */
    private WPasswordField criarCampoSenha(String placeholder, int x, int y, int width, int height) {
        try {
            Objects.requireNonNull(placeholder, "O placeholder não pode ser nulo");
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("Largura e altura devem ser maiores que zero");
            }

            WPasswordField campoSenha = new WPasswordField();
            campoSenha.setLabelText(placeholder);
            campoSenha.setBounds(x, y, width, height);
            campoSenha.setBackground(getThemeColor("PasswordField.background", Color.WHITE));
            campoSenha.setForeground(getThemeColor("PasswordField.foreground", Color.BLACK));
            campoSenha.setCaretColor(getThemeColor("PasswordField.caretForeground", Color.BLACK));
            return campoSenha;
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao criar o campo de senha: " + e.getMessage(), e);
        }
    }

    /**
     * Cria e configura um botão com as propriedades especificadas.
     *
     * @param texto  Texto a ser exibido no botão
     * @param x      Posição X do botão
     * @param y      Posição Y do botão
     * @param width  Largura do botão
     * @param height Altura do botão
     * @return JButton configurado
     * @throws IllegalArgumentException se os parâmetros forem inválidos
     */
    private JButton criarBotao(String texto, int x, int y, int width, int height) {
        try {
            Objects.requireNonNull(texto, "O texto do botão não pode ser nulo");
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("Largura e altura devem ser maiores que zero");
            }

            JButton botao = new JButton(texto);
            botao.setBackground(getThemeColor("Button.background", DEFAULT_BUTTON_BG));
            botao.setForeground(getThemeColor("Button.foreground", DEFAULT_BUTTON_FG));
            botao.setFont(UIManager.getFont("Button.font"));
            botao.putClientProperty(FlatClientProperties.STYLE, "arc:10; borderWidth:0");
            botao.setBounds(x, y, width, height);

            // Melhoria de acessibilidade
            botao.setMnemonic(texto.charAt(0));
            botao.setToolTipText("Pressione para " + texto.toLowerCase());

            return botao;
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao criar o botão: " + e.getMessage(), e);
        }
    }

    /**
     * Alterna a exibição entre os painéis de login e registro.
     *
     * @param nomePainel Nome do painel a ser exibido ("login" ou "registro")
     * @throws IllegalArgumentException se o nome do painel for inválido
     */
    private void mostrarPainel(String nomePainel) {
        try {
            if (!"login".equals(nomePainel) && !"registro".equals(nomePainel)) {
                throw new IllegalArgumentException("Nome do painel inválido: " + nomePainel);
            }

            CardLayout cl = (CardLayout) getLayout();
            cl.show(this, nomePainel);

            // Força o foco no primeiro campo do painel exibido
            if ("login".equals(nomePainel) && txtUsuario != null) {
                txtUsuario.requestFocusInWindow();
            } else if (txtNome != null) {
                txtNome.requestFocusInWindow();
            }
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao alternar para o painel: " + nomePainel, e);
        }
    }

    // ============================================= MÉTODOS DE PINTURA =============================================

    /**
     * Desenha o componente com cantos arredondados e fundo branco.
     *
     * @param g o contexto gráfico no qual pintar
     */
    @Override
    protected void paintComponent(Graphics g) {
        try {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            try {
                // Configura a renderização para melhor qualidade
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                // Preenche o fundo com cantos arredondados
                g2.setColor(getThemeColor("Panel.background", DEFAULT_BG_COLOR));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARREDONDAMENTO, ARREDONDAMENTO);
            } finally {
                // Garante que o contexto gráfico seja liberado mesmo em caso de exceção
                g2.dispose();
            }
        } catch (Exception e) {
            System.err.println("Erro ao desenhar o componente: " + e.getMessage());
        }
    }

    // ============================================== MÉTODOS PÚBLICOS ==============================================

    /**
     * Alterna a exibição entre os painéis de login e registro.
     *
     * @param mostrarRegistro {@code true} para mostrar o painel de registro,
     *                        {@code false} para mostrar o painel de login
     * @throws IllegalStateException se ocorrer um erro ao alternar entre os painéis
     */
    public void showRegistro(boolean mostrarRegistro) {
        try {
            if (mostrarRegistro) {
                mostrarPainelRegistro();
            } else {
                mostrarPainelLogin();
            }
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao alternar entre os painéis", e);
        }
    }

    /**
     * Mostra o painel de login.
     *
     * @throws IllegalStateException se o painel de login não puder ser exibido
     */
    public void mostrarPainelLogin() {
        try {
            mostrarPainel("login");
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao exibir o painel de login", e);
        }
    }

    /**
     * Mostra o painel de registro.
     *
     * @throws IllegalStateException se o painel de registro não puder ser exibido
     */
    public void mostrarPainelRegistro() {
        try {
            mostrarPainel("registro");
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao exibir o painel de registro", e);
        }
    }

    // ============================================== MÉTODOS GETTERS ==============================================

    /**
     * Retorna o botão de login.
     *
     * @return JButton de login
     */
    public JButton getBtnLogin() {
        return btnLogin;
    }

    /**
     * Retorna o botão de registro.
     *
     * @return JButton de registro
     */
    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    /**
     * Retorna o rótulo "Esqueceu a senha?".
     *
     * @return JLabel do link "Esqueceu a senha?"
     */
    public JLabel getLblEsqueceu() {
        return lblEsqueceu;
    }

    /**
     * Retorna o campo de texto do nome de usuário.
     *
     * @return WTextField do nome de usuário
     */
    public WTextField getTxtUsuario() {
        return txtUsuario;
    }

    /**
     * Retorna o campo de senha do painel de login.
     *
     * @return JPasswordField da senha de login
     */
    public WPasswordField getTxtSenhaLogin() {
        return txtSenhaLogin;
    }

    /**
     * Retorna o campo de texto do nome completo.
     *
     * @return WTextField do nome completo
     */
    public WTextField getTxtNome() {
        return txtNome;
    }

    /**
     * Retorna o campo de texto do email.
     *
     * @return WTextField do email
     */
    public WTextField getTxtEmail() {
        return txtEmail;
    }

    /**
     * Retorna o campo de senha do painel de registro.
     *
     * @return JPasswordField da senha de registro
     */
    public WPasswordField getTxtSenhaRegistro() {
        return txtSenhaRegistro;
    }
}
