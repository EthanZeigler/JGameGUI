package com.ethanzeigler.jgamegui.element;

import javax.swing.*;

/**
 * An {@link ImageElement} that is extended to allow for a manual check for collisions with other CollidableImageElements using
 * {@link Collidable#isCollidingWith(Collidable)}
 */
public class CollidableImage extends ImageElement implements Sized {
    private double height, width;

    /**
     * An ImageElement with the ability to check for collisions with other CollidableImageElements.
     * @param resPath the path to the image resource to display
     * @param xOrig the x origin
     * @param yOrig the y origin
     * @param priority the drawing priority. The higher the priority, the later it is drawn, and over others with lower priorities
     * @param height the collision area's height
     * @param width the collision area's width
     */
    public CollidableImage(String resPath, double xOrig, double yOrig, int priority, double height, double width) {
        super(resPath, xOrig, yOrig, priority);
        this.height = height;
        this.width = width;
    }

    /**
     * An ImageElement with the ability to check for collisions with other CollidableImageElements.
     * @param icon the image icon to display
     * @param xOrig the x origin
     * @param yOrig the y origin
     * @param priority the drawing priority. The higher the priority, the later it is drawn, and over others with lower priorities
     * @param height the collision area's height
     * @param width the collision area's width
     */
    public CollidableImage(ImageIcon icon, double xOrig, double yOrig, int priority, double width, double height) {
        super(icon, xOrig, yOrig, priority);
        this.width = width;
        this.height = height;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    /**
     * Sets the height of the collision area
     * @param height the height of the collision area
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Sets the width of the collison area
     * @param width the width of the collision area
     */
    public void setWidth(double width) {
        this.width = width;
    }
}
