package br.com.warrick.swing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.ease.Spline;

/**
 * Componente de label personalizado com suporte a animações de linha inferior interativa.
 * Este componente estende JLabel e adiciona uma linha inferior que é animada quando o mouse passa sobre o label.
 * O WLabel é projetado para ser facilmente integrado em interfaces gráficas usando o tema FlatLaf, mas também pode ser usado com outros temas.
 * O WLabel suporta alinhamento horizontal e vertical do texto, e a linha inferior se ajusta dinamicamente com base no tamanho do texto e nas margens do componente.
 *
 * @author Warrick
 * @version 2.0.0
 * @since 27/11/2025
 * @see JLabel
 */
public class WLabel extends JLabel {
    // ============================================ CONSTANTES DE CORES ============================================

    /**
     * Cor padrão da linha inferior quando o mouse está sobre o componente.
     */
    protected static final Color DEFAULT_LINE_COLOR = new Color(3, 155, 216);

    /**
     * Cor padrão para o texto do label.
     */
    protected static final Color DEFAULT_TEXT_COLOR = new Color(50, 50, 50);

    /**
     * Cor da linha inferior quando o mouse não está sobre o componente.
     */
    protected static final Color DEFAULT_LINE_BG_COLOR = new Color(200, 200, 200);

    // ============================================ CONSTANTES DE LAYOUT ============================================

    /**
     * Altura em pixels da linha inferior do label.
     */
    protected static final int LINE_HEIGHT = 1;

    /**
     * Espaçamento em pixels entre o texto e a linha inferior.
     */
    protected static final int LINE_SPACING = 3;

    /**
     * Duração em milissegundos das animações do componente.
     */
    protected static final int ANIMATION_DURATION = 300;

    // ============================================ ATRIBUTOS ============================================

    /**
     * Progresso atual da animação da linha (0.0 a 1.0).
     */
    protected float lineAnimationProgress = 0f;

    /**
     * Indica se o cursor do mouse está sobre o componente.
     */
    protected boolean mouseOver = false;

    /**
     * Cor da linha inferior quando o mouse está sobre o label.
     */
    protected Color lineColor = DEFAULT_LINE_COLOR;

    /**
     * Controlador de animação para transições suaves da linha.
     */
    protected Timeline lineTimeline;

    // ============================================ CONSTRUTORES ============================================

    /**
     * Cria um novo label vazio.
     */
    private boolean fontSet = false;

    public WLabel() {
        this("");
    }

    /**
     * Cria um novo label com o texto especificado.
     *
     * @param text Texto a ser exibido no label
     */

    public WLabel(String text) {
        super(text);
        setupLabel();
    }

    /**
     * Cria um novo label com texto e alinhamento horizontal especificados.
     *
     * @param text Texto a ser exibido no label
     * @param horizontalAlignment Alinhamento horizontal (SwingConstants.LEFT, CENTER, RIGHT, LEADING, TRAILING)
     */

    public WLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
        setupLabel();
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
     * Configura as propriedades iniciais do label.
     */
    @Override
    public void updateUI() {
        super.updateUI();
        if (getFont() != null) {
            applyThemeColors();
            repaint();
        }
    }

    private void applyThemeColors() {
        setForeground(getThemeColor("WLabel.textColor", DEFAULT_TEXT_COLOR));
        lineColor = getThemeColor("WLabel.lineColor", DEFAULT_LINE_COLOR);
        // Fonte do componente
        if (!fontSet) {
            Font themeFont = UIManager.getFont("WLabel.font");
            if (themeFont != null) {
                super.setFont(themeFont);
            }
        }
    }

    private void setupLabel() {
        // Configuração de cores
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Inicializa a cor da linha do tema
        applyThemeColors();

        // Listener de mouse
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                mouseOver = true;
                animateLine(true);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                mouseOver = false;
                animateLine(false);
            }
        });
    }

    /**
     * Método principal de renderização do componente.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // Desenha o texto
            super.paintComponent(g2);

            // Desenha a linha
            paintLine(g2);
        } finally {
            g2.dispose();
        }
    }

    /**
     * Calcula a posição Y do texto baseado no alinhamento vertical.
     */
    private int calculateTextY() {
        FontMetrics fm = getFontMetrics(getFont());
        Insets insets = getInsets();
        int textHeight = fm.getHeight();
        int verticalAlignment = getVerticalAlignment();

        int availableHeight = getHeight() - insets.top - insets.bottom;

        if (verticalAlignment == SwingConstants.TOP) {
            return insets.top + fm.getAscent();
        } else if (verticalAlignment == SwingConstants.BOTTOM) {
            return getHeight() - insets.bottom - fm.getDescent();
        } else { // CENTER (padrão)
            return insets.top + (availableHeight - textHeight) / 2 + fm.getAscent();
        }
    }

    /**
     * Desenha a linha inferior do label.
     */
    private void paintLine(Graphics2D g2) {
        FontMetrics fm = getFontMetrics(getFont());
        String text = getText();

        if (text == null || text.isEmpty()) {
            return;
        }

        // Calcula a largura do texto
        int textWidth = fm.stringWidth(text);

        // Obtém as margens do componente
        Insets insets = getInsets();

        // Calcula a posição X baseada no alinhamento horizontal
        int x;
        int horizontalAlignment = getHorizontalAlignment();

        if (horizontalAlignment == SwingConstants.CENTER) {
            x = (getWidth() - textWidth) / 2;
        } else if (horizontalAlignment == SwingConstants.RIGHT || horizontalAlignment == SwingConstants.TRAILING) {
            x = getWidth() - textWidth - insets.right;
        } else { // LEFT ou LEADING
            x = insets.left;
        }

        // Calcula a posição Y da linha baseada no alinhamento vertical
        int textY = calculateTextY();
        int lineY = textY + fm.getDescent() + LINE_SPACING;

        // Obtém cor da linha de fundo do tema
        Color currentLineBgColor = getThemeColor("WLabel.lineBgColor", DEFAULT_LINE_BG_COLOR);

        // Desenha a linha de fundo
        g2.setColor(currentLineBgColor);
        g2.fillRect(x, lineY, textWidth, LINE_HEIGHT);

        // Linha de destaque (hover)
        if (mouseOver || lineAnimationProgress > 0) {
            g2.setColor(lineColor);
            int lineWidth = (int) (textWidth * lineAnimationProgress);
            g2.fillRect(x, lineY, lineWidth, LINE_HEIGHT);
        }
    }

    // ============================================ MÉTODOS DE ANIMAÇÃO ============================================

    /**
     * Anima a transição da linha inferior.
     */
    private void animateLine(boolean show) {
        if (lineTimeline != null && !lineTimeline.isDone()) {
            lineTimeline.abort();
        }

        lineTimeline = new Timeline(this);
        lineTimeline.addPropertyToInterpolate("lineAnimationProgress",
                lineAnimationProgress,
                show ? 1f : 0f);
        lineTimeline.setEase(new Spline(0.5f));
        lineTimeline.setDuration(ANIMATION_DURATION);
        lineTimeline.play();
    }

    
    @Override
    public void setFont(Font font) {
        super.setFont(font);
        fontSet = true;
        repaint();
    }

    // ============================================ MÉTODOS DE CONFIGURAÇÃO ============================================

    /**
     * Define a cor da linha inferior do label.
     *
     * @param lineColor A cor da linha inferior
     */
    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
        repaint();
    }

    /**
     * Define o progresso da animação da linha inferior.
     * Usado internamente pelo sistema de animação.
     *
     * @param lineAnimationProgress O progresso da animação (0.0 a 1.0)
     */
    public void setLineAnimationProgress(float lineAnimationProgress) {
        this.lineAnimationProgress = lineAnimationProgress;
        repaint();
    }

    /**
     * Sobrescreve setText para garantir que a linha seja redesenhada.
     */
    @Override
    public void setText(String text) {
        super.setText(text);
        repaint();
    }

    /**
     * Sobrescreve setHorizontalAlignment para redesenhar a linha.
     */
    @Override
    public void setHorizontalAlignment(int alignment) {
        super.setHorizontalAlignment(alignment);
        repaint();
    }

    /**
     * Sobrescreve setVerticalAlignment para redesenhar a linha.
     */
    @Override
    public void setVerticalAlignment(int alignment) {
        super.setVerticalAlignment(alignment);
        repaint();
    }

    // ============================================ MÉTODOS DE ACESSO ============================================
    public Color getLineColor() {return lineColor;}

    public float getLineAnimationProgress() {return lineAnimationProgress;}

    public boolean isMouseOver() {return mouseOver;}
}