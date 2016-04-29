package com.ethanzeigler.jgamegui.element;

/**
 * Represents a box that has the ability to collide with other Collidables
 */
public interface Collidable {
    // to be implemented at a later time
    /*enum CollidableSide {
        LEFT,
        RIGHT,
        BOTTOM,
        TOP
    }*/

    /**
     * Gets whether the given Collidable is colliding with <i>this</i>
     * @param collidable the collidable to inspect for collision with
     * @return whether or not the collidables are colliding
     */
    default boolean isCollidingWith(Collidable collidable) {
        return  this.getxOrigin() + this.getWidth() <= collidable.getxOrigin() ||       // a is left of b
                this.getxOrigin() >= collidable.getxOrigin() + collidable.getWidth() || // a is right of b
                this.getyOrigin() + this.getHeight() <= collidable.getHeight() ||       // a is above b
                this.getyOrigin() >= collidable.getyOrigin() + collidable.getHeight();  // a is below b

    }

    /**
     * Gets the origin x
     * @return the x origin
     */
    double getxOrigin();

    /**
     * Gets the width
     * @return the largest x value
     */
    double getWidth();

    /**
     * Gets the y origin
     * @return the y origin
     */
    double getyOrigin();

    /**
     * Gets the height
     * @return the height
     */
    double getHeight();
}
