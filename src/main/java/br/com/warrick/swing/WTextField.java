package br.com.warrick.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.ease.Spline;

/**
 * Componente de campo de texto personalizado com suporte a animações, rótulo flutuante e validação.
 *
 * @author Warrick
 * @version 3.2.2
 * @since 25/11/2025
 * @see JTextField
 */
public class WTextField extends JTextField {
    // ============================================ CONSTANTES DE CORES ============================================
   
    protected static final Color DEFAULT_LINE_COLOR = new Color(3, 155, 216);
   
    protected static final Color DEFAULT_HOVER_COLOR = new Color(100, 180, 220);
    
    protected static final Color DEFAULT_TEXT_COLOR = new Color(50, 50, 50);

    protected static final Color DEFAULT_HINT_COLOR = new Color(150, 150, 150);
  
    protected static final Color DEFAULT_BG_COLOR = Color.WHITE;
 
    protected static final Color DEFAULT_LINE_BG_COLOR = new Color(200, 200, 200);

    protected static final Color ERROR_COLOR = new Color(220, 53, 69);

    protected static final Color SUCCESS_COLOR = new Color(40, 167, 69);

    // ============================================ CONSTANTES DE LAYOUT ============================================

    protected static final int LINE_HEIGHT = 1;
 
    protected static final int LINE_Y_OFFSET = 14;
 
    protected static final int PADDING_TOP = 20;
   
    protected static final int PADDING_LEFT = 10;

    protected static final int PADDING_BOTTOM = 15;

    protected static final int PADDING_RIGHT = 10;
  
    protected static final int LABEL_TOP_POSITION = 13;

    protected static final int ANIMATION_DURATION = 300;
  
    protected static final int ERROR_MESSAGE_Y_OFFSET = -2;

    // ============================================ ATRIBUTOS ============================================

    protected float lineAnimationProgress = 0f;

    protected float animationLocation = 0f;

    protected float errorAnimationLocation = 0f;

    protected boolean showLabel = false;
   
    protected boolean mouseOver = false;
  
    protected boolean obrigatorio = false;
   
    protected boolean hasError = false;

    protected boolean isSuccessMessage = false;

    protected String labelText = "";
 
    protected String errorMessage = "";

    protected Color lineColor = DEFAULT_LINE_COLOR;
 
    protected Color hoverColor = DEFAULT_HOVER_COLOR;

    protected Timeline timeline;

    protected Timeline errorTimeline;
 
    private boolean lineColorSet = false;
 
    private boolean hoverColorSet = false;
  
    private boolean usarCoresTema = false;

    // ============================================ CONSTRUTORES ============================================

    public WTextField() {
        this("");
    }   
    
    public WTextField(String labelText) {
        super();
        this.labelText = labelText;
        setupField();
    }

    // ============================================ MÉTODOS PRIVADOS ============================================
   
    protected Color getThemeColor(String key, Color defaultColor) {
        Color themeColor = UIManager.getColor(key);
        return themeColor != null ? themeColor : defaultColor;
    }   

    private void initAnimation() {
        animationLocation = getText().isEmpty() ? 0f : 1f;
        showLabel = !getText().isEmpty();
        errorAnimationLocation = 0f;
    }

    @Override
    public void updateUI() {
        super.updateUI();
        // Recarrega cores do tema (evita NPE se chamado antes do construtor terminar)
        if (getFont() != null) {
            applyThemeColors();
            repaint();
        }
    }
 
    private void applyThemeColors() {
        setBackground(getThemeColor("WTextField.bgColor", DEFAULT_BG_COLOR));
        setForeground(getThemeColor("WTextField.textColor", DEFAULT_TEXT_COLOR));
        setCaretColor(getThemeColor("WTextField.textColor", DEFAULT_TEXT_COLOR));
        // Só sobrescreve lineColor/hoverColor se não foram definidos manualmente
        if (!lineColorSet) {
            lineColor = getThemeColor("WTextField.lineColor", DEFAULT_LINE_COLOR);
        }
        if (!hoverColorSet) {
            hoverColor = getThemeColor("WTextField.hoverColor", DEFAULT_HOVER_COLOR);
        }
    }

    private void setupField() {
        // Configuração de borda e cores (usando tema se disponível)
        setBorder(new EmptyBorder(PADDING_TOP, PADDING_LEFT, PADDING_BOTTOM, PADDING_RIGHT));
        setOpaque(false);

        // Inicializa as cores customizáveis do tema
        applyThemeColors();

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
     * Desenha a mensagem de erro/sucesso fora dos limites do componente.
     *
     * @param g2 Contexto gráfico 2D
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

    // ============================================ MÉTODOS AUXILIARES ============================================
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
   
    private void animateLabel(boolean show) {
        timeline = new Timeline(this);
        timeline.addPropertyToInterpolate("animationLocation",
                animationLocation,
                show ? 1f : 0f);
        timeline.setEase(new Spline(0.5f));
        timeline.setDuration(ANIMATION_DURATION);
        timeline.play();
    }
    
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
  
    private void animateLine(boolean show) {
        Timeline lineTimeline = new Timeline(this);
        lineTimeline.addPropertyToInterpolate("lineAnimationProgress",
                lineAnimationProgress,
                show ? 1f : 0f);
        lineTimeline.setDuration(ANIMATION_DURATION);
        lineTimeline.play();
    }

    // ============================================ MÉTODOS DE PINTURA ============================================
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
     * Verifica se o campo obrigatório está preenchido.
     * Se o campo tiver um rótulo definido, ele será incluído na mensagem de erro.
     *
     * @return true se o campo for válido, false caso contrário
     */
    private boolean validarObrigatorio() {
        if (!obrigatorio) {
            return true;
        }
        if (getText().trim().isEmpty()) {
            if (labelText != null && !labelText.trim().isEmpty()) {
                String rotulo = labelText.trim();
                if (rotulo.endsWith(":") || rotulo.endsWith(": ")) {
                    rotulo = rotulo.substring(0, rotulo.length() - 1).trim();
                }
                rotulo = rotulo.substring(0, 1).toUpperCase() + rotulo.substring(1).toLowerCase();
                setMensagem(rotulo + " é obrigatório", false);
            } else {
                setMensagem("Campo obrigatório", false);
            }
            return false;
        }
        return true;
    }

    // ========================================= MÉTODOS PÚBLICOS DE VALIDAÇÃO =========================================
     public void setMensagem(String mensagem, boolean sucesso) {
        this.errorMessage = mensagem;
        this.isSuccessMessage = sucesso;
        this.hasError = !sucesso; // Define hasError como true para erros, false para sucesso

        updateLabelState(isFocusOwner());
        animateError(true);
        repaint();
    }

    public void mostrarSucesso(String mensagem) {setMensagem(mensagem, true);}

    public boolean validar() {return validarObrigatorio();}
    
    public boolean validarComMensagem(String mensagem) {
        mostrarErro(mensagem);
        return false;
    }
    
    public void mostrarErro(String mensagem) { setMensagem(mensagem, false);}
    
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

   
    @Deprecated
    public void limparErro() { limparMensagem();}

    // ============================================ MÉTODOS DE CONFIGURAÇÃO ============================================
    @Override
    public void setText(String text) {
        super.setText(text);
        updateLabelState(isFocusOwner());
        if (hasError && text != null && !text.trim().isEmpty()) {
            limparErro();
        }
    }

    
    public void setLabelText(String labelText) {
        this.labelText = labelText;
        repaint();
    }
    
    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
        this.lineColorSet = true;
        repaint();
    }
   
    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
        this.hoverColorSet = true;
        repaint();
    }
    
    public void setObrigatorio(boolean obrigatorio) {
        boolean oldValue = this.obrigatorio;
        this.obrigatorio = obrigatorio;
        firePropertyChange("obrigatorio", oldValue, obrigatorio);
    }
    
    public void setAnimationLocation(float animationLocation) {
        this.animationLocation = animationLocation;
        repaint();
    }
    
    public void setErrorAnimationLocation(float errorAnimationLocation) {
        this.errorAnimationLocation = errorAnimationLocation;
        repaint();
    }
    
    public void setLineAnimationProgress(float lineAnimationProgress) {
        this.lineAnimationProgress = lineAnimationProgress;
        repaint();
    }

    // ============================================ MÉTODOS DE ACESSO ============================================
    public String getLabelText() {return labelText;}
   
    public Color getLineColor() {return lineColor;}
    
    public Color getHoverColor() {return hoverColor;}
   
    public float getAnimationLocation() {return animationLocation;}

    public float getErrorAnimationLocation() {return errorAnimationLocation;}

    public float getLineAnimationProgress() { return lineAnimationProgress;}

    public boolean isObrigatorio() {return obrigatorio;}

    public boolean hasError() { return hasError;}

    public String getErrorMessage() {return errorMessage;}
}