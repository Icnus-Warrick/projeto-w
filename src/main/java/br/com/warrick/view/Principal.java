package br.com.warrick.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;
import com.formdev.flatlaf.FlatClientProperties;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.Timeline.TimelineState;
import org.pushingpixels.trident.callback.TimelineCallback;
import org.pushingpixels.trident.ease.Spline;

/**
 * Classe principal que gerencia a janela da aplicação e as animações de transição
 * entre os painéis de login e registro.
 * 
 * <p>Esta classe é responsável por:</p>
 * <ul>
 *   <li>Configurar a janela principal da aplicação</li>
 *   <li>Gerenciar os painéis de login e registro</li>
 *   <li>Controlar as animações de transição entre os painéis</li>
 *   <li>Manter o estado da aplicação</li>
 * </ul>
 *
 * @author Warrick
 * @version 1.0.1
 * @since 23/11/2025
 */
public class Principal extends JFrame {

    // ================================================ CONSTANTES =================================================
    
    /** Dimensões da janela principal */
    private static final int LARGURA_JANELA = 800;
    private static final int ALTURA_JANELA = 700;
    
    /** Dimensões dos painéis de login/registro */
    private static final int LARGURA_PAINEL = 350;
    private static final int ALTURA_PAINEL = 500;
    
    // Posições iniciais (tela de login)
    private static final int FRONTAL_LOGIN_X = 30;
    private static final int FRONTAL_LOGIN_Y = 30;
    private static final int PAINEL_LOGIN_X = 350;
    private static final int PAINEL_LOGIN_Y = 80;
    
    // Posições finais (tela de registro)
    private static final int FRONTAL_REGISTRO_X = 350;
    private static final int FRONTAL_REGISTRO_Y = 80;
    private static final int PAINEL_REGISTRO_X = 30;
    private static final int PAINEL_REGISTRO_Y = 30;
    
    // Propriedades para animação
    private static final int DURACAO_ANIMACAO = 800; // em milissegundos
    private static final int TAMANHO_MAXIMO_EFEITO = 30; // pixels para o efeito de escala
    
    // ================================================== ATRIBUTOS =================================================
    
    /** Estado atual da aplicação (login/registro) */
    private boolean isLogin = true;
    
    /** Painel frontal com animações */
    private final LoginFrontal loginFrontal;
    
    /** Painel de formulário (login/registro) */
    private final LoginPainel loginPainel;
    
    /** Controla se o painel já foi trocado durante a animação */
    private boolean painelTrocado = false;
    
    /** Timeline para controle das animações */
    private transient Timeline timeline;
    
    /** Painel principal que contém os demais componentes */
    private final JPanel bg;
    
    // Estado da animação
    private float animatedFrontalX;
    private float animatedFrontalY;
    private float animatedPainelX;
    private float animatedPainelY;
    private float animationProgress = 0f;

    // ============================================ CONSTANTES DE LAYOUT ============================================

    // ================================================= CONSTRUTOR =================================================
    
    /**
     * Constrói uma nova instância da janela principal.
     * Inicializa todos os componentes e configura a interface do usuário.
     * 
     * @throws IllegalStateException Se ocorrer um erro durante a inicialização
     */
    public Principal() {
        try {
            // Inicializa os componentes básicos
            this.bg = criarPainelPrincipal();
            this.loginFrontal = new LoginFrontal();
            this.loginPainel = new LoginPainel();
            
            // Configura a janela e seus componentes
            configurarJanela();
            inicializarComponentes();
            configurarLayout();
            configurarEventos();
            
            // Exibe a janela
            exibirJanela();
        } catch (Exception e) {
            String mensagemErro = "Erro ao iniciar a aplicação: " + e.getMessage();
            JOptionPane.showMessageDialog(null, mensagemErro, "Erro", JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("Falha ao inicializar a janela principal", e);
        }
    }

    // ================================================ MÉTODOS PRIVADOS ============================================
    
    /**
     * Cria e configura o painel principal com as propriedades visuais.
     * 
     * @return JPanel configurado como painel principal
     */
    private JPanel criarPainelPrincipal() {
        JPanel painel = new JPanel();
        painel.setOpaque(false);
        painel.setLayout(null);
        painel.setBackground(new Color(0, 0, 0, 0));
        painel.putClientProperty(FlatClientProperties.STYLE, "arc:20;");
        return painel;
    }
    
    /**
     * Configura as propriedades básicas da janela principal.
     * Define o título, remove a decoração da janela, configura o fundo transparente,
     * o comportamento de fechamento, tamanho e posicionamento centralizado.
     * 
     * @throws HeadlessException Se o ambiente não suportar interação com o usuário
     */
    private void configurarJanela() {
        setTitle("Sistema de Login");
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(LARGURA_JANELA, ALTURA_JANELA);
        setLocationRelativeTo(null);
    }

    /**
     * Inicializa os componentes da interface e configura suas posições iniciais.
     * 
     * @throws NullPointerException Se algum dos componentes principais for nulo
     * @throws IllegalStateException Se ocorrer um erro ao configurar as posições
     */
    private void inicializarComponentes() {
        Objects.requireNonNull(loginFrontal, "LoginFrontal não pode ser nulo");
        Objects.requireNonNull(loginPainel, "LoginPainel não pode ser nulo");
        
        // Configura posições iniciais dos painéis
        configurarPosicoesIniciais();

        // Adiciona os painéis ao painel principal
        bg.add(loginFrontal);
        bg.add(loginPainel);
    }

    /**
     * Configura o layout da janela principal.
     * Define o painel principal como content pane da janela.
     */
    private void configurarLayout() {
        setContentPane(bg);
    }

    /**
     * Configura os ouvintes de eventos da interface.
     * Adiciona o listener para o clique no botão de alternar entre login e registro.
     */
    private void configurarEventos() {
        loginFrontal.addEvent(this::tratarCliqueBotao);
    }

    /**
     * Exibe a janela principal na tela.
     * Este método deve ser chamado após a configuração completa da interface.
     */
    private void exibirJanela() {
        setVisible(true);
    }

    /**
     * Configura as posições iniciais dos painéis e seu estado.
     * Define as posições iniciais com base no estado atual (login/registro).
     * 
     * @throws IllegalStateException Se ocorrer um erro ao configurar as posições
     */
    private void configurarPosicoesIniciais() {
        // Define as posições iniciais com base no estado atual
        if (isLogin) {
            animatedFrontalX = FRONTAL_LOGIN_X;
            animatedFrontalY = FRONTAL_LOGIN_Y;
            animatedPainelX = PAINEL_LOGIN_X;
            animatedPainelY = PAINEL_LOGIN_Y;
            loginPainel.mostrarPainelLogin();
            loginFrontal.login(false);
        } else {
            animatedFrontalX = FRONTAL_REGISTRO_X;
            animatedFrontalY = FRONTAL_REGISTRO_Y;
            animatedPainelX = PAINEL_REGISTRO_X;
            animatedPainelY = PAINEL_REGISTRO_Y;
            loginPainel.mostrarPainelRegistro();
            loginFrontal.login(true);
        }

        // Aplica as posições iniciais
        atualizarPosicoesPainel();
    }
    
    /**
     * Atualiza as posições dos painéis com base nas coordenadas atuais.
     * Este método é chamado durante as animações para atualizar as posições
     * dos painéis na tela.
     */
    private void atualizarPosicoesPainel() {
        // Atualiza o painel frontal
        loginFrontal.setBounds(
            Math.round(animatedFrontalX),
            Math.round(animatedFrontalY),
            LARGURA_PAINEL,
            ALTURA_PAINEL
        );

        // Atualiza o painel de formulário
        loginPainel.setBounds(
            Math.round(animatedPainelX),
            Math.round(animatedPainelY),
            LARGURA_PAINEL,
            ALTURA_PAINEL
        );
    }

    /**
     * Trata o evento de clique no botão de alternar entre login e registro.
     * Inicia a animação de transição se nenhuma animação estiver em andamento.
     * 
     * @param event O evento de ação gerado pelo clique (não utilizado)
     */
    private void tratarCliqueBotao(ActionEvent event) {
        if (podeIniciarAnimacao()) {
            animarTransicao();
        }
    }
    
    /**
     * Verifica se uma nova animação pode ser iniciada.
     * 
     * @return true se nenhuma animação estiver em andamento, false caso contrário
     */
    private boolean podeIniciarAnimacao() {
        return timeline == null || 
               (timeline.getState() != TimelineState.PLAYING_FORWARD &&
                timeline.getState() != TimelineState.PLAYING_REVERSE);
    }

    // ============================================= MÉTODOS DE ANIMAÇÃO ============================================
    
    /**
     * Inicia a animação de transição entre os painéis de login e registro.
     * Configura os parâmetros iniciais da animação e inicia a timeline.
     */
    private void animarTransicao() {
        painelTrocado = false;

        // Define os destinos da animação com base no estado atual
        float[] alvos = calcularAlvosAnimacao();
        
        // Configura a timeline da animação
        configurarTimelineAnimacao(alvos[0], alvos[1], alvos[2], alvos[3]);
    }
    
    /**
     * Calcula as posições de destino para a animação com base no estado atual.
     * 
     * @return Array com as posições de destino [frontalX, frontalY, painelX, painelY]
     */
    private float[] calcularAlvosAnimacao() {
        return isLogin ? 
            new float[]{FRONTAL_REGISTRO_X, FRONTAL_REGISTRO_Y, PAINEL_REGISTRO_X, PAINEL_REGISTRO_Y} :
            new float[]{FRONTAL_LOGIN_X, FRONTAL_LOGIN_Y, PAINEL_LOGIN_X, PAINEL_LOGIN_Y};
    }
    
    /**
     * Configura a timeline para a animação de transição entre os painéis.
     * 
     * @param targetFrontalX Posição X de destino do painel frontal
     * @param targetFrontalY Posição Y de destino do painel frontal
     * @param targetPainelX Posição X de destino do painel de formulário
     * @param targetPainelY Posição Y de destino do painel de formulário
     * @throws IllegalStateException Se ocorrer um erro ao configurar a animação
     */
    private void configurarTimelineAnimacao(float targetFrontalX, float targetFrontalY, 
                                           float targetPainelX, float targetPainelY) {
        try {
            // Cancela a animação atual se estiver em andamento
            if (timeline != null) {
                timeline.abort();
            }
            
            timeline = new Timeline(this);
            timeline.setDuration(DURACAO_ANIMACAO);

            // Configura as propriedades para animação com verificação de nulos
            timeline.addPropertyToInterpolate("animatedFrontalX", animatedFrontalX, targetFrontalX);
            timeline.addPropertyToInterpolate("animatedFrontalY", animatedFrontalY, targetFrontalY);
            timeline.addPropertyToInterpolate("animatedPainelX", animatedPainelX, targetPainelX);
            timeline.addPropertyToInterpolate("animatedPainelY", animatedPainelY, targetPainelY);
            timeline.addPropertyToInterpolate("animationProgress", 0f, 1f);

            // Configura a curva de aceleração/desaceleração
            timeline.setEase(new Spline(0.4f));

            // Adiciona callbacks para controle da animação
            timeline.addCallback(new TimelineCallback() {
                @Override
                public void onTimelineStateChanged(TimelineState oldState, TimelineState newState,
                        float durationFraction, float timelinePosition) {
                    try {
                        if (newState == TimelineState.DONE) {
                            finalizarAnimacao();
                        }
                    } catch (Exception e) {
                        handleAnimationError("Erro ao atualizar estado da animação", e);
                    }
                }

                @Override
                public void onTimelinePulse(float durationFraction, float timelinePosition) {
                    try {
                        atualizarAnimacao(durationFraction);
                    } catch (Exception e) {
                        handleAnimationError("Erro ao atualizar animação", e);
                    }
                }
            });

            timeline.play();
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao configurar a animação", e);
        }
    }
    
    /**
     * Trata erros ocorridos durante a animação.
     * 
     * @param mensagem Mensagem de erro
     * @param e Exceção original
     */
    private void handleAnimationError(String mensagem, Exception e) {
        System.err.println(mensagem + ": " + e.getMessage());
        e.printStackTrace();
        
        // Tenta restaurar o estado consistente
        try {
            finalizarAnimacao();
        } catch (Exception ex) {
            System.err.println("Erro ao tentar restaurar o estado após falha na animação: " + ex.getMessage());
        }
    }

    /**
     * Finaliza a animação e ajusta o estado final dos componentes.
     * Garante que os painéis estejam nas posições corretas e atualiza o estado da aplicação.
     * 
     * @throws IllegalStateException Se ocorrer um erro ao finalizar a animação
     */
    private void finalizarAnimacao() {
        try {
            // Inverte o estado de login/registro
            isLogin = !isLogin;
            animationProgress = 0f;

            // Garante que o painel correto esteja visível
            if (!painelTrocado) {
                if (isLogin) {
                    loginPainel.mostrarPainelLogin();
                } else {
                    loginPainel.mostrarPainelRegistro();
                }
            }

            // Ajusta as posições finais com base no novo estado
            if (isLogin) {
                animatedFrontalX = FRONTAL_LOGIN_X;
                animatedFrontalY = FRONTAL_LOGIN_Y;
                animatedPainelX = PAINEL_LOGIN_X;
                animatedPainelY = PAINEL_LOGIN_Y;
                loginFrontal.login(true);
                loginPainel.mostrarPainelLogin();
            } else {
                animatedFrontalX = FRONTAL_REGISTRO_X;
                animatedFrontalY = FRONTAL_REGISTRO_Y;
                animatedPainelX = PAINEL_REGISTRO_X;
                animatedPainelY = PAINEL_REGISTRO_Y;
                loginFrontal.login(false);
                loginPainel.mostrarPainelRegistro();
            }

            // Atualiza as posições finais
            atualizarPosicoesPainel();

            // Força a atualização da interface de forma otimizada
            SwingUtilities.invokeLater(() -> {
                loginFrontal.revalidate();
                loginPainel.revalidate();
                bg.repaint();
            });
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao finalizar a animação", e);
        }
    }

    /**
     * Atualiza o estado da animação em cada frame.
     * 
     * @param fraction Progresso da animação (0.0 a 1.0)
     * @throws IllegalStateException Se ocorrer um erro ao atualizar a animação
     */
    private void atualizarAnimacao(float fraction) {
        try {
            // Validação do parâmetro
            if (fraction < 0f || fraction > 1f) {
                throw new IllegalArgumentException("O progresso da animação deve estar entre 0.0 e 1.0");
            }
            
            // Calcula o tamanho do efeito de escala
            int sizeIncrease = calcularTamanhoEfeito(fraction);
            
            // Aplica o efeito de escala ao painel frontal
            aplicarEfeitoEscala(sizeIncrease);
            
            // Atualiza as animações de texto
            atualizarAnimacoesTexto(fraction);
            
            // Troca o painel na metade da animação, se necessário
            if (fraction >= 0.5f && !painelTrocado) {
                loginPainel.showRegistro(isLogin);
                painelTrocado = true;
            }

            // Otimização: só repinta se a animação estiver ativa
            if (timeline != null && timeline.getState() == Timeline.TimelineState.PLAYING_FORWARD) {
                bg.repaint();
            }
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao atualizar a animação", e);
        }
    }
    
    /**
     * Calcula o tamanho do efeito de escala com base no progresso da animação.
     * 
     * @param fraction Progresso da animação (0.0 a 1.0)
     * @return Tamanho do efeito em pixels
     * @throws IllegalArgumentException Se fraction estiver fora do intervalo [0.0, 1.0]
     */
    private int calcularTamanhoEfeito(float fraction) {
        if (fraction < 0f || fraction > 1f) {
            throw new IllegalArgumentException("O progresso da animação deve estar entre 0.0 e 1.0");
        }
        
        if (fraction <= 0.2f) {
            return (int) (TAMANHO_MAXIMO_EFEITO * (fraction / 0.2f));
        } else if (fraction <= 0.8f) {
            return TAMANHO_MAXIMO_EFEITO;
        } else {
            return (int) (TAMANHO_MAXIMO_EFEITO * ((1.0f - fraction) / 0.2f));
        }
    }
    
    /**
     * Aplica o efeito de escala ao painel frontal.
     * 
     * @param sizeIncrease Tamanho do efeito em pixels (deve ser não negativo)
     * @throws IllegalArgumentException Se sizeIncrease for negativo
     */
    private void aplicarEfeitoEscala(int sizeIncrease) {
        if (sizeIncrease < 0) {
            throw new IllegalArgumentException("O tamanho do efeito não pode ser negativo");
        }
        
        try {
            int frontalCurrentWidth = LARGURA_PAINEL + (sizeIncrease * 2);
            int frontalCurrentHeight = ALTURA_PAINEL + sizeIncrease;
            int frontalAdjustX = (int) animatedFrontalX - sizeIncrease;
            int frontalAdjustY = (int) animatedFrontalY - (sizeIncrease / 2);

            // Garante que as dimensões não sejam negativas
            frontalCurrentWidth = Math.max(0, frontalCurrentWidth);
            frontalCurrentHeight = Math.max(0, frontalCurrentHeight);
            
            // Aplica os novos limites
            loginFrontal.setBounds(frontalAdjustX, frontalAdjustY, frontalCurrentWidth, frontalCurrentHeight);
            loginPainel.setBounds((int) animatedPainelX, (int) animatedPainelY, LARGURA_PAINEL, ALTURA_PAINEL);
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao aplicar efeito de escala", e);
        }
    }
    
    /**
     * Atualiza as animações de texto com base no progresso da animação.
     * 
     * @param fraction Progresso da animação (0.0 a 1.0)
     * @throws IllegalArgumentException Se fraction estiver fora do intervalo [0.0, 1.0]
     */
    private void atualizarAnimacoesTexto(float fraction) {
        if (fraction < 0f || fraction > 1f) {
            throw new IllegalArgumentException("O progresso da animação deve estar entre 0.0 e 1.0");
        }
        
        try {
            if (isLogin) {
                if (fraction >= 0.5f) {
                    loginFrontal.registroDireito((1f - fraction) * 100);
                } else {
                    loginFrontal.loginDireito(fraction * 100);
                }
            } else {
                if (fraction <= 0.5f) {
                    loginFrontal.registroEsquerdo(fraction * 100);
                } else {
                    loginFrontal.loginEsquerdo((1f - fraction) * 100);
                }
            }
        } catch (Exception e) {
            // Log do erro, mas não interrompe a execução
            System.err.println("Erro ao atualizar animações de texto: " + e.getMessage());
        }
    }

    // ================================================== GETTERS ===================================================
    
    /**
     * @return Posição X atual do painel frontal
     */
    public float getAnimatedFrontalX() {
        return animatedFrontalX;
    }

    /**
     * @return Posição Y atual do painel frontal
     */
    public float getAnimatedFrontalY() {
        return animatedFrontalY;
    }

    /**
     * @return Posição X atual do painel de formulário
     */
    public float getAnimatedPainelX() {
        return animatedPainelX;
    }

    /**
     * @return Posição Y atual do painel de formulário
     */
    public float getAnimatedPainelY() {
        return animatedPainelY;
    }

    /**
     * @return Progresso atual da animação (0.0 a 1.0)
     */
    public float getAnimationProgress() {
        return animationProgress;
    }

    // ================================================== SETTERS ===================================================
    
    /**
     * Define a posição X do painel frontal.
     * 
     * @param animatedFrontalX Nova posição X
     */
    public void setAnimatedFrontalX(float animatedFrontalX) {
        this.animatedFrontalX = animatedFrontalX;
    }

    /**
     * Define a posição Y do painel frontal.
     * 
     * @param animatedFrontalY Nova posição Y
     */
    public void setAnimatedFrontalY(float animatedFrontalY) {
        this.animatedFrontalY = animatedFrontalY;
    }

    /**
     * Define a posição X do painel de formulário.
     * 
     * @param animatedPainelX Nova posição X
     */
    public void setAnimatedPainelX(float animatedPainelX) {
        this.animatedPainelX = animatedPainelX;
    }

    /**
     * Define a posição Y do painel de formulário.
     * 
     * @param animatedPainelY Nova posição Y
     */
    public void setAnimatedPainelY(float animatedPainelY) {
        this.animatedPainelY = animatedPainelY;
    }

    /**
     * Define o progresso da animação.
     * 
     * @param animationProgress Novo valor de progresso (0.0 a 1.0)
     */
    public void setAnimationProgress(float animationProgress) {
        this.animationProgress = animationProgress;
    }
}
