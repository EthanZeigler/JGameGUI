package com.ethanzeigler.jgamegui.element;

import com.ethanzeigler.jgamegui.animation.Animation;

import java.awt.*;

/**
 * Created by ethanzeigler on 4/14/16.
 */
public abstract class AbstractElement implements Comparable {
    protected double xOrig, yOrig;
    private int priority;
    protected Animation animation;
    private boolean isVisible = true;

    /**
     * Initiates a AbstractElement with the given x origin, y origin, and draw priority
     * @param xOrig the x origin
     * @param yOrig the y origin
     * @param priority the drawing priority. The higher the priority, the later it is drawn, and over others with lower priorities
     */
    public AbstractElement(double xOrig, double yOrig, int priority) {
        this.xOrig = xOrig;
        this.yOrig = yOrig;
        this.priority = priority;
    }

    /**
     * Gets the x origin of the ImageElement. The origin is the top left corner.
     *
     * @return The x origin of the ImageElement
     */
    public double getxOrig() {
        return xOrig;
    }

    /**
     * Sets the x origin of the ImageElement. The origin is in the top left corner.
     *
     * @param xOrig The origin to set
     */
    public void setxOrig(double xOrig) {
        this.xOrig = xOrig;
    }

    /**
     * Gets the y origin of the ImageElement. The origin is the top left corner.
     *
     * @return The y origin of the ImageElement
     */
    public double getyOrig() {
        return yOrig;
    }

    /**
     * Sets the y origin of the ImageElement. The origin is in the top left corner.
     *
     * @param yOrig The y origin to set
     */
    public void setyOrig(double yOrig) {
        this.yOrig = yOrig;
    }

    /**
     * Gets the drawing priority of the ImageElement. The smaller the number, the later the ImageElement will be drawn.
     *
     * @return The drawing priority of the ImageElement
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Gets the drawing priority of the element. The smaller the number, the later the ImageElement will be drawn.
     *
     * @param priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof ImageElement) return priority - ((AbstractElement) o).priority;
        else throw new ClassCastException(
                "Param Object cannot be cast to com.ethanzeigler.jgamegui.ImageElement");

    }

    /**
     * Draws the {@link ImageElement} to the {@link Graphics} object.
     *
     * @param g Graphics to draw to
     */
    public abstract void paint(Graphics g);

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
     * Gets whether or not the ImageElement is currently visible
     * @return whether the ImageElement is visible
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Sets whether or not the ImageElement is visible
     * @param isVisible whether or not the ImageElement is visible
     */
    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
}
