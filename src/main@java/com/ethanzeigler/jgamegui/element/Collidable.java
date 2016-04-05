package com.ethanzeigler.jgamegui.element;

import java.awt.geom.Rectangle2D;

/**
 * Represents a box that has the ability to collide with other Collidables
 * public boolean intersects(double x, double y, double w, double h) {



 }
 return (getMinumumX() >= collidable.getMaximumX()
 */
public interface Collidable {
    public enum CollidableSide {
        LEFT,
        RIGHT,
        BOTTOM,
        TOP
    }

    default boolean isCollidingWith(Collidable collidable) {
        /*
        if (a.max.x < b.min.x) return false;
    if (a.min.x > b.max.x) return false;
    if (a.max.y < b.min.y) return false;
    if (a.min.y > b.max.y) return false;
    return true; // boxes overlap
         */
        return  this.getxOrigin() + this.getWidth() <= collidable.getxOrigin() ||       // a is left of b
                this.getxOrigin() >= collidable.getxOrigin() + collidable.getWidth() || // a is right of b
                this.getyOrigin() + this.getHeight() <= collidable.getHeight() ||       // a is above b
                this.getyOrigin() >= collidable.getyOrigin() + collidable.getHeight();  // a is below b

    }

    /**
     * Gets the orgin x
     * @return the x origin
     */
    public double getxOrigin();

    /**
     * Gets the width
     * @return the largest x value
     */
    public double getWidth();

    /**
     * Gets the y origin
     * @return the y origin
     */
    public double getyOrigin();

    /**
     * Gets the height
     * @return the height
     */
    public double getHeight();
}
