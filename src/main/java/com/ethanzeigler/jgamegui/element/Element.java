package com.ethanzeigler.jgamegui.element;

import com.ethanzeigler.jgamegui.animation.Animation;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ethanzeigler on 2/24/16.
 */
public class Element implements Comparable {
    protected Image img;
    protected double xOrig, yOrig;
    private int priority;
    protected Animation animation;
    private boolean isVisible = true;

    public Element(String resPath, double xOrig, double yOrig, int priority) {
        setImg(resPath);
        this.xOrig = xOrig;
        this.yOrig = yOrig;
        this.priority = priority;
    }

    public Element(ImageIcon icon, double xOrig, double yOrig, int priority) {
        setImg(icon);
        this.xOrig = xOrig;
        this.yOrig = yOrig;
        this.priority = priority;
    }

    /**
     * Gets the Image of the Element
     *
     * @return Image displayed by the Element
     */
    public Image getImg() {
        return img;
    }

    /**
     * Sets the Image of the Element
     *
     * @param img Image to set
     */
    public void setImg(Image img) {
        this.img = img;
    }

    /**
     * Sets the Image of the Element
     *
     * @param img Image to set
     */
    public void setImg(ImageIcon img) {
        this.img = img.getImage();
    }

    /**
     * Sets the Image to display from the given file
     *
     * @param filePath Path to file to display
     */
    public void setImg(String filePath) {
        this.img = new ImageIcon(this.getClass().getResource(filePath)).getImage();
    }


    /**
     * Gets the x origin of the Element. The origin is the top left corner.
     *
     * @return The x origin of the Element
     */
    public double getxOrig() {
        return xOrig;
    }

    /**
     * Sets the x origin of the Element. The origin is in the top left corner.
     *
     * @param xOrig The origin to set
     */
    public void setxOrig(double xOrig) {
        this.xOrig = xOrig;
    }

    /**
     * Gets the y origin of the Element. The origin is the top left corner.
     *
     * @return The y origin of the Element
     */
    public double getyOrig() {
        return yOrig;
    }

    /**
     * Sets the y origin of the Element. The origin is in the top left corner.
     *
     * @param yOrig The y origin to set
     */
    public void setyOrig(double yOrig) {
        this.yOrig = yOrig;
    }

    /**
     * Gets the drawing priority of the element. The smaller the number, the later the Element will be drawn.
     *
     * @return The drawing priority of the Element
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Gets the drawing priority of the element. The smaller the number, the later the Element will be drawn.
     *
     * @param priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Element) return priority - ((Element) o).priority;
        else throw new ClassCastException(
                "Param Object cannot be cast to com.ethanzeigler.jgamegui.Element");

    }

    /**
     * Draws the {@link Element} to the {@link Graphics} object.
     *
     * @param g Graphics to draw to
     */
    public void paint(Graphics g) {
        g.drawImage(img, (int) xOrig, (int) yOrig, null);
    }

    public void runTick(long totalTicks) {
        if (animation != null) {
            boolean doCancel = animation.update(this);
            if (doCancel) this.animation = null;
        }
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    /**
     * Gets whether or not the Element is currently visible
     * @return whether the Element is visible
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Sets whether or not the Element is visible
     * @param isVisible whether or not the Element is visible
     */
    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
}
