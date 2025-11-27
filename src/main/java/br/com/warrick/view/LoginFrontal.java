package br.com.warrick.view;

import br.com.warrick.swing.ButtonOutLine;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import javax.swing.*;

/**
 * Painel frontal que exibe a interface de boas-vindas e alterna entre as telas
 * de login e registro com animações suaves.
 *
 * <p>Este componente é responsável por:</p>
 * <ul>
 *   <li>Exibir mensagens de boas-vindas</li>
 *   <li>Alternar entre os modos de login e registro</li>
 *   <li>Gerenciar animações de transição</li>
 *   <li>Manter o estado da interface do usuário</li>
 * </ul>
 *
 * @author Warrick
 * @version 1.0.0
 * @since 23/11/2025
 */
public class LoginFrontal extends JPanel {

    // ============================================ CONSTANTES DE LAYOUT ============================================
    
    /** Dimensões e posições do título */
    private static final int TITULO_X = 5;
    private static final int TITULO_Y = 100;
    private static final int TITULO_WIDTH = 340;
    private static final int TITULO_HEIGHT = 40;
    
    /** Dimensões e posições das descrições */
    private static final int DESCRICAO_X = 5;
    private static final int DESCRICAO_Y = 200;
    private static final int DESCRICAO1_Y = 250;
    private static final int DESCRICAO_WIDTH = 340;
    private static final int DESCRICAO_HEIGHT = 25;
    
    /** Dimensões e posições do botão */
    private static final int BOTAO_X = 95;
    private static final int BOTAO_Y = 350;
    private static final int BOTAO_WIDTH = 160;
    private static final int BOTAO_HEIGHT = 40;
    
    /** Cores e estilos */
    private static final Color COR_TEXTO = new Color(245, 245, 245);
    private static final Color COR_FUNDO = new Color(51, 102, 255);
    private static final int ARREDONDAMENTO = 20;
    
    // ================================================= ATRIBUTOS ==================================================
    
    /** Listener para eventos de clique no botão */
    private ActionListener event;
    
    /** Componentes da interface */
    private JLabel titulo;
    private JLabel descricao;
    private JLabel descricao1;
    private ButtonOutLine button;
    
    /** Estado do painel (login/registro) */
    private boolean isLogin;
    
    /** Controle de opacidade para transição suave */
    private float currentOpacity = 1.0f;

    // ================================================= CONSTRUTOR =================================================
    
    /**
     * Constrói uma nova instância do painel frontal de login/registro.
     * Inicializa os componentes e configura o layout.
     * 
     * @throws IllegalStateException Se ocorrer um erro durante a inicialização dos componentes
     */
    public LoginFrontal() {
        try {
            configurarPainel();
            inicializarComponentes();
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao inicializar o painel frontal", e);
        }
    }

    // ============================================== MÉTODOS PRIVADOS ==============================================
    
    /**
     * Configura as propriedades básicas do painel.
     */
    private void configurarPainel() {
        setOpaque(false);
        setLayout(null);
        setBackground(COR_FUNDO);
        putClientProperty(FlatClientProperties.STYLE, "arc:" + ARREDONDAMENTO + ";");
    }

    /**
     * Inicializa todos os componentes do painel.
     * 
     * @throws IllegalStateException Se ocorrer um erro durante a inicialização dos componentes
     */
    private void inicializarComponentes() {
        try {
            criarTitulo();
            criarDescricao();
            criarBotao();
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao inicializar os componentes do painel", e);
        }
    }

    /**
     * Cria e configura o título do painel.
     */
    private void criarTitulo() {
        titulo = new JLabel("Bem Vindo de Volta!", SwingConstants.CENTER);
        titulo.setFont(new Font("sansserif", Font.BOLD, 30));
        titulo.setForeground(COR_TEXTO);
        titulo.setBounds(TITULO_X, TITULO_Y, TITULO_WIDTH, TITULO_HEIGHT);
        add(titulo);
    }

    /**
     * Cria e configura as descrições do painel.
     */
    private void criarDescricao() {
        descricao = new JLabel("Para se manter conectado conosco, por favor", SwingConstants.CENTER);
        descricao.setForeground(COR_TEXTO);
        descricao.setBounds(DESCRICAO_X, DESCRICAO_Y, DESCRICAO_WIDTH, DESCRICAO_HEIGHT);
        add(descricao);

        descricao1 = new JLabel("Faça login com suas informações pessoais.", SwingConstants.CENTER);
        descricao1.setForeground(COR_TEXTO);
        descricao1.setBounds(DESCRICAO_X, DESCRICAO1_Y, DESCRICAO_WIDTH, DESCRICAO_HEIGHT);
        add(descricao1);
    }

    /**
     * Cria e configura o botão de ação principal.
     */
    private void criarBotao() {
        try {
            button = new ButtonOutLine();
            button.setText("ENTRAR");
            button.setBackground(Color.WHITE);
            button.setForeground(Color.WHITE);
            button.setBounds(BOTAO_X, BOTAO_Y, BOTAO_WIDTH, BOTAO_HEIGHT);
            button.addActionListener(this::tratarCliqueBotao);
            add(button);
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao criar o botão", e);
        }
    }

    /**
     * Trata o evento de clique no botão principal.
     *
     * @param e Evento de ação gerado pelo clique
     */
    private void tratarCliqueBotao(ActionEvent e) {
        try {
            if (event != null) {
                event.actionPerformed(e);
            }
        } catch (Exception ex) {
            System.err.println("Erro ao processar clique no botão: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Atualiza a opacidade de todos os componentes visíveis do painel.
     * A opacidade é controlada pelo valor de currentOpacity (0.0 a 1.0).
     */
    private void atualizarOpacidade() {
        try {
            // Garante que a opacidade esteja no intervalo [0, 1]
            float opacidade = Math.max(0, Math.min(1, currentOpacity));
            int alpha = (int) (255 * opacidade);
            
            // Cores com a opacidade aplicada
            Color corComOpacidade = new Color(COR_TEXTO.getRed(), COR_TEXTO.getGreen(), 
                                           COR_TEXTO.getBlue(), alpha);
            Color corBotaoComOpacidade = new Color(255, 255, 255, alpha);

            // Aplica as cores aos componentes
            if (titulo != null) titulo.setForeground(corComOpacidade);
            if (descricao != null) descricao.setForeground(corComOpacidade);
            if (descricao1 != null) descricao1.setForeground(corComOpacidade);
            if (button != null) button.setForeground(corBotaoComOpacidade);
            
            // Força o repaint para atualizar as mudanças visuais
            repaint();
        } catch (Exception e) {
            System.err.println("Erro ao atualizar opacidade: " + e.getMessage());
        }
    }

    // ============================================= MÉTODOS DE PINTURA =============================================
    
    /**
     * Pinta o componente com cantos arredondados e o fundo configurado.
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
                g2.setColor(getBackground());
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
     * Define o listener para o evento de clique no botão principal.
     *
     * @param event ActionListener para o evento de clique, não pode ser nulo
     * @throws NullPointerException se o parâmetro event for nulo
     */
    public void addEvent(ActionListener event) {
        this.event = Objects.requireNonNull(event, "O listener de evento não pode ser nulo");
    }

    /**
     * Alterna entre os modos de login e registro, atualizando os textos e estilos
     * dos componentes conforme necessário.
     *
     * @param login true para modo login, false para modo registro
     * @throws IllegalStateException se ocorrer um erro ao atualizar a interface
     */
    public void login(boolean login) {
        try {
            if (this.isLogin != login) {
                if (login) {
                    // Configuração para tela de registro
                    titulo.setText("Olá, Amigo!");
                    descricao.setText("Insira seus dados pessoais");
                    descricao1.setText("e comece sua jornada conosco");
                    button.setText("INSCREVER-SE");
                } else {
                    // Configuração para tela de login
                    titulo.setText("Bem Vindo de Volta!");
                    descricao.setText("Para se manter conectado conosco, por favor");
                    descricao1.setText("Faça login com suas informações pessoais.");
                    button.setText("ENTRAR");
                }
                this.isLogin = login;
                currentOpacity = 1.0f;
                atualizarOpacidade();
                reposicionarComponentes();
            }
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao alternar modo de login/registro", e);
        }
    }

    /**
     * Reposiciona todos os componentes para suas posições padrão.
     * 
     * @throws IllegalStateException se algum componente não estiver inicializado
     */
    private void reposicionarComponentes() {
        try {
            // Verifica se os componentes foram inicializados
            if (titulo == null || descricao == null || descricao1 == null || button == null) {
                throw new IllegalStateException("Componentes não foram devidamente inicializados");
            }
            
            // Reposiciona cada componente
            titulo.setLocation(TITULO_X, TITULO_Y);
            descricao.setLocation(DESCRICAO_X, DESCRICAO_Y);
            descricao1.setLocation(DESCRICAO_X, DESCRICAO1_Y);
            button.setLocation(BOTAO_X, BOTAO_Y);
            
            // Força a atualização da interface
            revalidate();
            repaint();
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao reposicionar os componentes", e);
        }
    }

    // ============================================= MÉTODOS DE ANIMAÇÃO ============================================
    
    /**
     * Executa a animação do painel de registro vindo da esquerda.
     * A animação é dividida em duas fases:
     * 1. Fade out do conteúdo atual (0-50% do progresso)
     * 2. Fade in do novo conteúdo com movimento da esquerda (50-100% do progresso)
     *
     * @param progresso Valor entre 0 e 100 representando o progresso da animação
     * @throws IllegalArgumentException se o progresso for menor que 0 ou maior que 100
     */
    public void registroEsquerdo(double progresso) {
        validarProgresso(progresso);
        try {
            if (progresso <= 50) {
                // Fase 1: Fade out do conteúdo atual
                login(true);
                currentOpacity = 1.0f - ((float) progresso / 50f);
            } else {
                // Fase 2: Fade in do novo conteúdo + movimento
                login(false);
                animarComponentes(progresso, 1);
            }
            atualizarOpacidade();
        } catch (Exception e) {
            throw new IllegalStateException("Falha na animação de registro pela esquerda", e);
        }
    }

    /**
     * Executa a animação do painel de registro vindo da direita.
     * Similar a registroEsquerdo, mas o movimento é feito da direita para a esquerda.
     *
     * @param progresso Valor entre 0 e 100 representando o progresso da animação
     * @throws IllegalArgumentException se o progresso for menor que 0 ou maior que 100
     */
    public void registroDireito(double progresso) {
        validarProgresso(progresso);
        try {
            if (progresso <= 50) {
                // Fase 1: Fade out do conteúdo atual
                login(true);
                currentOpacity = 1.0f - ((float) progresso / 50f);
            } else {
                // Fase 2: Fade in do novo conteúdo + movimento
                login(false);
                animarComponentes(progresso, -1);
            }
            atualizarOpacidade();
        } catch (Exception e) {
            throw new IllegalStateException("Falha na animação de registro pela direita", e);
        }
    }

    /**
     * Executa a animação do painel de login vindo da esquerda.
     *
     * @param progresso Valor entre 0 e 100 representando o progresso da animação
     * @throws IllegalArgumentException se o progresso for menor que 0 ou maior que 100
     */
    public void loginEsquerdo(double progresso) {
        validarProgresso(progresso);
        try {
            if (progresso <= 50) {
                // Fase 1: Fade out do conteúdo atual
                login(false);
                currentOpacity = 1.0f - ((float) progresso / 50f);
            } else {
                // Fase 2: Fade in do novo conteúdo + movimento
                login(true);
                animarComponentes(progresso, 1);
            }
            atualizarOpacidade();
        } catch (Exception e) {
            throw new IllegalStateException("Falha na animação de login pela esquerda", e);
        }
    }

    /**
     * Executa a animação do painel de login vindo da direita.
     *
     * @param progresso Valor entre 0 e 100 representando o progresso da animação
     * @throws IllegalArgumentException se o progresso for menor que 0 ou maior que 100
     */
    public void loginDireito(double progresso) {
        validarProgresso(progresso);
        try {
            if (progresso <= 50) {
                // Fase 1: Fade out do conteúdo atual
                login(false);
                currentOpacity = 1.0f - ((float) progresso / 50f);
            } else {
                // Fase 2: Fade in do novo conteúdo + movimento
                login(true);
                animarComponentes(progresso, -1);
            }
            atualizarOpacidade();
        } catch (Exception e) {
            throw new IllegalStateException("Falha na animação de login pela direita", e);
        }
    }
    
    /**
     * Método auxiliar para animar os componentes com base no progresso e direção.
     * 
     * @param progresso Progresso da animação (0-100)
     * @param direcao 1 para movimento da esquerda para direita, -1 para direita para esquerda
     */
    private void animarComponentes(double progresso, int direcao) {
        float progressoNormalizado = ((float) progresso - 50f) / 50f;
        currentOpacity = progressoNormalizado;
        int deslocamento = (int) (100 * (1 - progressoNormalizado)) * direcao;

        // Aplica o deslocamento aos componentes
        if (titulo != null) titulo.setLocation(TITULO_X + deslocamento, TITULO_Y);
        if (descricao != null) descricao.setLocation(DESCRICAO_X + deslocamento, DESCRICAO_Y);
        if (descricao1 != null) descricao1.setLocation(DESCRICAO_X + deslocamento, DESCRICAO1_Y);
        if (button != null) button.setLocation(BOTAO_X + deslocamento, BOTAO_Y);
    }
    
    /**
     * Valida se o valor do progresso está dentro do intervalo permitido (0-100).
     * 
     * @param progresso Valor a ser validado
     * @throws IllegalArgumentException se o valor estiver fora do intervalo
     */
    private void validarProgresso(double progresso) {
        if (progresso < 0 || progresso > 100) {
            throw new IllegalArgumentException("O progresso deve estar entre 0 e 100");
        }
    }
}
