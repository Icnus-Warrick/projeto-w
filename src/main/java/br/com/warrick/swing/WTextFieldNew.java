package br.com.warrick.swing;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.EmptyBorder;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.ease.Spline;

/**
 * Componente de campo de texto personalizado com suporte a animações, rótulo flutuante e validação.
 *
 * <p>
 * Esta classe estende JTextField para fornecer um campo de texto com rótulo flutuante, feedback visual consistente e
 * sistema de validação integrado.</p>
 *
 * <p>
 * Para documentação completa, consulte:
 * <a href="docs/WTextField.md">Documentação Detalhada</a></p>
 *
 * @author Warrick
 * @version 3.2.3
 * @since 25/11/2025
 * @see JTextField
 */
public class WTextFieldNew extends JTextField {
    // ============================================ CONSTANTES DE CORES ============================================

    /**
     * Cor padrão da linha inferior quando o campo está em foco.
     */
    protected static final Color DEFAULT_LINE_COLOR = new Color(3, 155, 216);

    /**
     * Cor de destaque quando o mouse está sobre o componente.
     */
    protected static final Color DEFAULT_HOVER_COLOR = new Color(100, 180, 220);

    /**
     * Cor padrão para o texto digitado no campo.
     */
    protected static final Color DEFAULT_TEXT_COLOR = new Color(50, 50, 50);

    /**
     * Cor do texto de dica (hint) quando o campo está vazio.
     */
    protected static final Color DEFAULT_HINT_COLOR = new Color(150, 150, 150);

    /**
     * Cor de fundo padrão do campo.
     */
    protected static final Color DEFAULT_BG_COLOR = Color.WHITE;

    /**
     * Cor da linha inferior quando o campo não está em foco.
     */
    protected static final Color DEFAULT_LINE_BG_COLOR = new Color(200, 200, 200);

    /**
     * Cor usada para mensagens e indicadores de erro.
     */
    protected static final Color ERROR_COLOR = new Color(220, 53, 69);

    /**
     * Cor usada para mensagens e indicadores de sucesso.
     */
    protected static final Color SUCCESS_COLOR = new Color(40, 167, 69);

    // ============================================ CONSTANTES DE LAYOUT ============================================
    /**
     * Altura em pixels da linha inferior do campo.
     */
    protected static final int LINE_HEIGHT = 1;

    /**
     * Deslocamento vertical em pixels da linha inferior em relação à base do componente.
     */
    protected static final int LINE_Y_OFFSET = 14;

    /**
     * Espaçamento interno superior do campo em pixels.
     */
    protected static final int PADDING_TOP = 20;

    /**
     * Espaçamento interno esquerdo do campo em pixels.
     */
    protected static final int PADDING_LEFT = 10;

    /**
     * Espaçamento interno inferior do campo em pixels.
     */
    protected static final int PADDING_BOTTOM = 15;

    /**
     * Espaçamento interno direito do campo em pixels.
     */
    protected static final int PADDING_RIGHT = 10;

    /**
     * Posição vertical em pixels do rótulo quando na posição superior.
     */
    protected static final int LABEL_TOP_POSITION = 13;

    /**
     * Duração em milissegundos das animações do componente.
     */
    protected static final int ANIMATION_DURATION = 300;

    /**
     * Deslocamento vertical em pixels para posicionamento da mensagem de erro.
     */
    protected static final int ERROR_MESSAGE_Y_OFFSET = -2;

    // ============================================ ATRIBUTOS ============================================
    /**
     * Progresso atual da animação da linha (0.0 a 1.0).
     */
    protected float lineAnimationProgress = 0f;

    /**
     * Progresso atual da animação do rótulo (0.0 a 1.0).
     */
    protected float animationLocation = 0f;

    /**
     * Progresso atual da animação da mensagem de erro (0.0 a 1.0).
     */
    protected float errorAnimationLocation = 0f;

    /**
     * Indica se o rótulo deve ser mostrado na posição superior.
     */
    protected boolean showLabel = false;

    /**
     * Indica se o cursor do mouse está sobre o componente.
     */
    protected boolean mouseOver = false;

    /**
     * Indica se o campo é obrigatório.
     */
    protected boolean obrigatorio = false;

    /**
     * Indica se há um erro de validação ativo no campo.
     */
    protected boolean hasError = false;

    /**
     * Indica se a mensagem atual é uma mensagem de sucesso.
     */
    protected boolean isSuccessMessage = false;

    /**
     * Texto exibido como rótulo flutuante do campo.
     */
    protected String labelText = "";

    /**
     * Mensagem de erro ou sucesso atual do campo.
     */
    protected String errorMessage = "";

    /**
     * Cor da linha inferior quando o campo está em foco.
     */
    protected Color lineColor = DEFAULT_LINE_COLOR;

    /**
     * Cor de destaque quando o mouse está sobre o componente.
     */
    protected Color hoverColor = DEFAULT_HOVER_COLOR;

    /**
     * Controlador de animação para transições suaves do rótulo e outros elementos.
     */
    protected Timeline timeline;

    /**
     * Controlador de animação para mensagens de erro e sucesso.
     */
    protected Timeline errorTimeline;

    /**
     * Indica se a cor da linha foi definida manualmente (via IDE ou código).
     */
    private boolean lineColorSet = false;

    /**
     * Indica se a cor de hover foi definida manualmente (via IDE ou código).
     */
    private boolean hoverColorSet = false;

    /**
     * Indica se deve usar as cores do tema FlatLaf.
     * Se true, as cores do tema têm prioridade sobre cores definidas manualmente.
     * Se false, as cores definidas manualmente têm prioridade.
     */
    private boolean usarCoresTema = false;

    // ============================================ CONSTRUTORES ============================================
    /**
     * Cria um novo campo de texto com rótulo vazio.
     */
    public WTextFieldNew() {
        this("");
    }

    /**
     * Cria um novo campo de texto com o rótulo especificado.
     */
    public WTextFieldNew(String labelText) {
        super();
        this.labelText = labelText;
        setupField();
    }

    // ============================================ MÉTODOS PRIVADOS ============================================
    /**
     * Obtém uma cor do tema FlatLaf ou retorna a cor padrão fornecida.
     */
    protected Color getThemeColor(String key, Color defaultColor) {
        Color themeColor = UIManager.getColor(key);
        return themeColor != null ? themeColor : defaultColor;
    }

    /**
     * Inicializa os estados iniciais das animações do campo.
     */
    private void initAnimation() {
        animationLocation = getText().isEmpty() ? 0f : 1f;
        showLabel = !getText().isEmpty();
        errorAnimationLocation = 0f;
    }

    /**
     * Aplica as cores corretas baseado na configuração de tema.
     */
    private void aplicarCores() {
        if (usarCoresTema) {
            // Usa cores do tema com fallback para as cores atuais
            lineColor = getThemeColor("WTextField.lineColor",
                    lineColorSet ? lineColor : DEFAULT_LINE_COLOR);
            hoverColor = getThemeColor("WTextField.hoverColor",
                    hoverColorSet ? hoverColor : DEFAULT_HOVER_COLOR);
        } else {
            // Usa cores definidas manualmente ou padrões
            if (!lineColorSet) {
                lineColor = DEFAULT_LINE_COLOR;
            }
            if (!hoverColorSet) {
                hoverColor = DEFAULT_HOVER_COLOR;
            }
        }
        repaint();
    }

    /**
     * Configura as propriedades iniciais do campo de texto.
     */
    private void setupField() {
        // Configuração de borda e cores (usando tema se disponível)
        setBorder(new EmptyBorder(PADDING_TOP, PADDING_LEFT, PADDING_BOTTOM, PADDING_RIGHT));
        setBackground(getThemeColor("WTextField.bgColor", DEFAULT_BG_COLOR));
        setForeground(getThemeColor("WTextField.textColor", DEFAULT_TEXT_COLOR));
        setCaretColor(getThemeColor("WTextField.textColor", DEFAULT_TEXT_COLOR));
        setOpaque(false);

        // Aplica as cores corretas baseado na configuração
        aplicarCores();

        // Inicializa a animação
        initAnimation();

        // Garante que o estado inicial do rótulo esteja correto
        updateLabelState(isFocusOwner());

        // Listener de foco
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                updateLabelState(true);
                animateLine(true);
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                updateLabelState(false);
                animateLine(false);
                repaint();
            }
        });

        // Listener de alteração de texto
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLabelState(isFocusOwner());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLabelState(isFocusOwner());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLabelState(isFocusOwner());
            }
        });

        // Listener de mouse
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                mouseOver = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent me) {
                mouseOver = false;
                repaint();
            }

            public void focusLost(FocusEvent fe) {
                if (getText().isEmpty()) {
                    animateLabel(false);
                }
                animateLine(false);
                repaint();
            }
        });

        // Listener para limpar erros durante a digitação
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                limparErroSeNecessario();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                limparErroSeNecessario();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                limparErroSeNecessario();
            }

            private void limparErroSeNecessario() {
                if (hasError && !getText().trim().isEmpty()) {
                    limparErro();
                }
            }
        });
    }

    /**
     * Método principal de renderização do componente.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            paintComponents(g2);
        } finally {
            g2.dispose();
        }
    }

    /**
     * Sobrescreve o método paint para permitir desenho fora dos limites padrão do componente.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Desenha a mensagem de erro/sucesso fora dos limites do componente
        if (hasError && errorMessage != null && !errorMessage.isEmpty() && errorAnimationLocation > 0) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                paintExternalMessage(g2);
            } finally {
                g2.dispose();
            }
        }
    }

    /**
     * Desenha a mensagem de erro ou sucesso abaixo do campo de texto.
     */
    private void paintExternalMessage(Graphics2D g2) {
        if (errorMessage == null || errorMessage.isEmpty()) {
            return;
        }

        // Obtém cor do tema ou usa padrão
        Color messageColor = isSuccessMessage
                ? getThemeColor("WTextField.successColor", SUCCESS_COLOR)
                : getThemeColor("WTextField.errorColor", ERROR_COLOR);

        // Aplica transparência baseada na animação
        int alpha = (int) (255 * errorAnimationLocation);
        g2.setColor(new Color(messageColor.getRed(), messageColor.getGreen(),
                messageColor.getBlue(), alpha));

        Font originalFont = g2.getFont();
        g2.setFont(originalFont.deriveFont(Font.PLAIN, 11f));

        int erroY = getHeight() + ERROR_MESSAGE_Y_OFFSET + (int) (3 * (1 - errorAnimationLocation));
        g2.drawString(errorMessage, PADDING_LEFT, erroY);

        g2.setFont(originalFont);
    }

    /**
     * Atualiza o estado do rótulo flutuante com base no foco e conteúdo do campo.
     */
    private void updateLabelState(boolean hasFocus) {
        boolean shouldShow = hasFocus || !getText().trim().isEmpty();

        if (showLabel == shouldShow && (timeline == null || timeline.isDone())) {
            return;
        }

        if (timeline != null && !timeline.isDone()) {
            timeline.abort();
        }

        showLabel = shouldShow;
        animateLabel(showLabel);
    }

    // ============================================ MÉTODOS DE ANIMAÇÃO ============================================
    /**
     * Anima a transição do rótulo entre os estados de repouso e flutuante.
     */
    private void animateLabel(boolean show) {
        timeline = new Timeline(this);
        timeline.addPropertyToInterpolate("animationLocation",
                animationLocation,
                show ? 1f : 0f);
        timeline.setEase(new Spline(0.5f));
        timeline.setDuration(ANIMATION_DURATION);
        timeline.play();
    }

    /**
     * Controla a animação de exibição/ocultação da mensagem de erro ou sucesso.
     */
    private void animateError(boolean show) {
        if (errorTimeline != null && !errorTimeline.isDone()) {
            errorTimeline.abort();
        }

        errorTimeline = new Timeline(this);
        errorTimeline.addPropertyToInterpolate("errorAnimationLocation",
                errorAnimationLocation,
                show ? 1f : 0f);
        errorTimeline.setEase(new Spline(0.5f));
        errorTimeline.setDuration(ANIMATION_DURATION);
        errorTimeline.play();
    }

    /**
     * Anima a linha inferior do campo para indicar o estado de foco ou hover.
     */
    private void animateLine(boolean show) {
        Timeline lineTimeline = new Timeline(this);
        lineTimeline.addPropertyToInterpolate("lineAnimationProgress",
                lineAnimationProgress,
                show ? 1f : 0f);
        lineTimeline.setDuration(ANIMATION_DURATION);
        lineTimeline.play();
    }

    // ============================================ MÉTODOS DE PINTURA ============================================
    /**
     * Desenha todos os componentes visuais personalizados do campo.
     */
    protected void paintComponents(Graphics2D g2) {
        int width = getWidth();
        int height = getHeight();
        int lineY = height - LINE_Y_OFFSET;

        // Obtém cor da linha de fundo do tema
        Color currentLineBgColor = getThemeColor("WTextField.lineBgColor", DEFAULT_LINE_BG_COLOR);

        // Desenha a linha de fundo
        g2.setColor(currentLineBgColor);
        g2.fillRect(2, lineY, width - 4, 1);

        // Linha de destaque (hover, foco ou erro)
        if (hasError) {
            Color errorLineColor = isSuccessMessage
                    ? getThemeColor("WTextField.successColor", SUCCESS_COLOR)
                    : getThemeColor("WTextField.errorColor", ERROR_COLOR);
            g2.setColor(errorLineColor);
            g2.fillRect(2, lineY - (LINE_HEIGHT / 2), width - 4, LINE_HEIGHT);
        } else if (mouseOver || isFocusOwner()) {
            Color highlightColor = mouseOver && !isFocusOwner() ? hoverColor : lineColor;
            g2.setColor(highlightColor);
            int lineWidth = isFocusOwner() ? (int) ((width - 4) * lineAnimationProgress) : (width - 4);
            g2.fillRect(2, lineY - (LINE_HEIGHT / 2), lineWidth, LINE_HEIGHT);
        }

        // Desenha o rótulo
        paintLabel(g2);
    }

    /**
     * Desenha o rótulo flutuante acima do campo de texto.
     */
    private void paintLabel(Graphics2D g2) {
        if (labelText == null || labelText.isEmpty()) {
            return;
        }

        Insets in = getInsets();

        // Define cor do rótulo baseada no estado
        Color labelColor;
        if (hasError) {
            labelColor = isSuccessMessage
                    ? getThemeColor("WTextField.successColor", SUCCESS_COLOR)
                    : getThemeColor("WTextField.errorColor", ERROR_COLOR);
        } else if (isFocusOwner()) {
            labelColor = lineColor;
        } else if (!getText().isEmpty()) {
            labelColor = getThemeColor("WTextField.textColor", DEFAULT_TEXT_COLOR);
        } else {
            labelColor = getThemeColor("WTextField.hintColor", DEFAULT_HINT_COLOR);
        }
        g2.setColor(labelColor);

        FontMetrics fm = g2.getFontMetrics();
        Rectangle2D textBounds = fm.getStringBounds(labelText, g2);

        // Calcula posições com animação
        double centerY = (getHeight() - textBounds.getHeight()) / 2 + fm.getAscent();
        double topY = LABEL_TOP_POSITION;
        double currentY = centerY - ((centerY - topY) * animationLocation);

        double bottomX = in.left;
        double topX = 5;
        double currentX = bottomX - ((bottomX - topX) * animationLocation);

        float scale = 1.0f + (0.04f * animationLocation);

        Graphics2D g2d = (Graphics2D) g2.create();
        try {
            g2d.translate(currentX, 0);
            g2d.scale(scale, scale);
            g2d.drawString(labelText, 0, (float) (currentY / scale));
        } finally {
            g2d.dispose();
        }
    }

    // ========================================= MÉTODOS PRIVADOS DE VALIDAÇÃO =========================================
    /**
     * Valida se um campo obrigatório foi preenchido.
     */
    private boolean validarObrigatorio() {
        if (!obrigatorio) {
            return true;
        }

        if (getText().trim().isEmpty()) {
            if (labelText != null && !labelText.trim().isEmpty()) {
                // Remove os dois pontos do final, se existirem
                String rotulo = labelText.trim();
                if (rotulo.endsWith(":") || rotulo.endsWith(": ")) {
                    rotulo = rotulo.substring(0, rotulo.length() - 1).trim();
                }
                // Capitaliza a primeira letra do rótulo
                rotulo = rotulo.substring(0, 1).toUpperCase() + rotulo.substring(1).toLowerCase();
                setMensagem(rotulo + " é obrigatório", false);
            } else {
                // Se não tiver rótulo definido, usa a mensagem genérica
                setMensagem("Campo obrigatório", false);
            }
            return false;
        }
        return true;
    }

    // ========================================= MÉTODOS PÚBLICOS DE VALIDAÇÃO =========================================
    /**
     * Define uma mensagem de feedback (erro ou sucesso) para ser exibida abaixo do campo.
     */
    public void setMensagem(String mensagem, boolean sucesso) {
        this.errorMessage = mensagem;
        this.isSuccessMessage = sucesso;
        this.hasError = !sucesso;

        updateLabelState(isFocusOwner());
        animateError(true);
        repaint();
    }

    /**
     * Exibe uma mensagem de sucesso estilizada abaixo do campo.
     */
    public void mostrarSucesso(String mensagem) {
        setMensagem(mensagem, true);
    }

    /**
     * Valida o campo de acordo com as regras configuradas.
     */
    public boolean validar() {
        return validarObrigatorio();
    }

    /**
     * Valida o campo e exibe uma mensagem de erro personalizada se a validação falhar.
     */
    public boolean validarComMensagem(String mensagem) {
        mostrarErro(mensagem);
        return false;
    }

    /**
     * Exibe uma mensagem de erro estilizada abaixo do campo.
     */
    public void mostrarErro(String mensagem) {
        setMensagem(mensagem, false);
    }

    /**
     * Remove qualquer mensagem de erro ou sucesso atualmente exibida no campo.
     */
    public void limparMensagem() {
        if (!hasError && (errorMessage == null || errorMessage.isEmpty())) {
            return;
        }

        if (errorTimeline != null && !errorTimeline.isDone()) {
            errorTimeline.abort();
        }

        animateError(false);

        new Thread(() -> {
            try {
                Thread.sleep(ANIMATION_DURATION);
                this.hasError = false;
                this.errorMessage = "";
                this.isSuccessMessage = false;
                updateLabelState(isFocusOwner());
                repaint();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    /**
     * @deprecated Use {@link #limparMensagem()} em vez deste método.
     */
    @Deprecated
    public void limparErro() {
        limparMensagem();
    }

    // ============================================ MÉTODOS DE CONFIGURAÇÃO ============================================
    /**
     * Define o texto do campo e atualiza o estado do rótulo flutuante.
     */
    @Override
    public void setText(String text) {
        super.setText(text);
        updateLabelState(isFocusOwner());
        if (hasError && text != null && !text.trim().isEmpty()) {
            limparErro();
        }
    }

    /**
     * Define o texto do rótulo flutuante do campo.
     */
    public void setLabelText(String labelText) {
        this.labelText = labelText;
        repaint();
    }

    /**
     * Define a cor da linha inferior do campo.
     */
    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
        this.lineColorSet = true;

        // Se estiver usando cores do tema, aplica a cor do tema se disponível
        if (usarCoresTema) {
            Color themeColor = getThemeColor("WTextField.lineColor", lineColor);
            this.lineColor = themeColor;
        }

        repaint();
    }

    /**
     * Define a cor de destaque quando o mouse está sobre o campo.
     */
    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
        this.hoverColorSet = true;

        // Se estiver usando cores do tema, aplica a cor do tema se disponível
        if (usarCoresTema) {
            Color themeColor = getThemeColor("WTextField.hoverColor", hoverColor);
            this.hoverColor = themeColor;
        }

        repaint();
    }

    /**
     * Define se o campo é obrigatório para preenchimento.
     */
    public void setObrigatorio(boolean obrigatorio) {
        boolean oldValue = this.obrigatorio;
        this.obrigatorio = obrigatorio;
        firePropertyChange("obrigatorio", oldValue, obrigatorio);
    }

    /**
     * Define se o componente deve usar as cores do tema FlatLaf.
     */
    public void setUsarCoresTema(boolean usarCoresTema) {
        this.usarCoresTema = usarCoresTema;
        aplicarCores();
    }

    /**
     * Define a posição atual da animação do rótulo.
     */
    public void setAnimationLocation(float animationLocation) {
        this.animationLocation = animationLocation;
        repaint();
    }

    /**
     * Define a posição atual da animação da mensagem de erro/sucesso.
     */
    public void setErrorAnimationLocation(float errorAnimationLocation) {
        this.errorAnimationLocation = errorAnimationLocation;
        repaint();
    }

    /**
     * Define o progresso da animação da linha inferior.
     */
    public void setLineAnimationProgress(float lineAnimationProgress) {
        this.lineAnimationProgress = lineAnimationProgress;
        repaint();
    }

    // ============================================ MÉTODOS DE ACESSO ============================================
    /**
     * Retorna o texto atual do rótulo flutuante do campo.
     */
    public String getLabelText() {
        return labelText;
    }

    /**
     * Retorna a cor atual da linha inferior do campo.
     */
    public Color getLineColor() {
        return lineColor;
    }

    /**
     * Retorna a cor de destaque usada quando o mouse está sobre o campo.
     */
    public Color getHoverColor() {
        return hoverColor;
    }

    /**
     * Retorna a posição atual da animação do rótulo.
     */
    public float getAnimationLocation() {
        return animationLocation;
    }

    /**
     * Retorna a posição atual da animação da mensagem de erro/sucesso.
     */
    public float getErrorAnimationLocation() {
        return errorAnimationLocation;
    }

    /**
     * Retorna o progresso atual da animação da linha inferior.
     */
    public float getLineAnimationProgress() {
        return lineAnimationProgress;
    }

    /**
     * Verifica se o campo é obrigatório para preenchimento.
     */
    public boolean isObrigatorio() {
        return obrigatorio;
    }

    /**
     * Verifica se o componente está usando as cores do tema FlatLaf.
     */
    public boolean isUsarCoresTema() {
        return usarCoresTema;
    }

    /**
     * Verifica se o campo atualmente possui uma mensagem de erro ativa.
     */
    public boolean hasError() {
        return hasError;
    }

    /**
     * Retorna a mensagem de erro/sucesso atualmente exibida no campo.
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}