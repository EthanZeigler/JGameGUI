package com.ethanzeigler.jgamegui.element;

import java.awt.*;

/**
 * An {@link AbstractElement} that represents text drawn on the screen with the lower-right corner of the Element as the drawing origin.
 */
public class TextElement extends AbstractElement {
    protected String text;
    protected Font font = new Font("Arial", Font.PLAIN, 30);
    protected double fontSize;
    protected Color color = Color.BLACK;

    /**
     * Represents written text on the screen. <b><u>This Element's origin is on the <i>bottom</i> right,
     * not the top right like other Elements. This is due to the behavior of the Swing API.</u></b>
     * @param xOrig x origin, the leftmost point
     * @param yOrig y origin, the bottommost point
     * @param priority the draw priority
     * @param text the text to be drawn
     */
    public TextElement(double xOrig, double yOrig, int priority, String text) {
        super(xOrig, yOrig, priority);
        this.text = text;
    }

    /**
     * Gets the text that is currently displayed
     * @return the text currently displayed
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text to display
     * @param text the text to display
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the currently used font
     * @return the current font
     */
    public Font getFont() {
        return font;
    }

    /**
     * Sets the text's font
     * @param font the new font
     */
    public void setFont(Font font) {
        if(font == null) throw new RuntimeException(new IllegalArgumentException("Font cannot be null"));
        this.font = font;
        fontSize = font.getSize();
    }

    /**
     * Gets the text size
     * @return the text size
     */
    public double getFontSize() {
        return fontSize;
    }

    /**
     * Sets the text size
     * @param fontSize the text size
     */
    public void setFontSize(double fontSize) {
        this.fontSize = fontSize;
        font = font.deriveFont((float) fontSize);
    }

    /**
     * Gets the color of the text
     * @return color of the text
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of the text
     * @param color color of the text
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Draws the {@link ImageElement} to the {@link Graphics} object.
     *
     * @param g Graphics to draw to
     */
    @Override
    public void paint(Graphics g) {
        g.setFont(font);
        g.setColor(color);

        g.drawString(text, (int) getxOrigin(), (int) getyOrigin());
    }
}
