package com.ethanzeigler.jgamegui.animation;

/**
 * Stores vertical and horizontal magnitude, as well as manipulating them.
 * Gravity can be set and applied to these vectors easily.
 *
 * @author Ethan Zeigler, class of '16
 */
public class Vector {
    private double horizontalMagnitude = 0, verticalMagnitude = 0;


    /**
     * Initiates vector with magnitudes
     *
     * @param hm horizontal magnitude
     * @param vm vertical magnitude
     */
    public Vector(double hm, double vm) {
        this.horizontalMagnitude = hm;
        this.verticalMagnitude = vm;
    }

    /**
     * @return the current vertical magnitude
     */
    public double getVerticalMagnitude() {
        return this.verticalMagnitude;
    }

    /**
     * @return the current horizontal magnitude
     */
    public double getHorizontalMagnitude() {
        return this.horizontalMagnitude;
    }

    /**
     * Adds value to the current horizontal magnitude
     *
     * @param acc the magnitude to add to the current horizontal magnitude. May be negative.
     */
    public void addHorizontalMagnitude(double acc) {
        this.horizontalMagnitude += acc;
    }

    /**
     * Adds value to the current vertical magnitude
     *
     * @param acc the magnitude to add to the current vertical magnitude. May be negative.
     */
    public void addVerticalMagnitude(double acc) {
        this.verticalMagnitude -= acc;
    }

    /**
     * Sets the horizontal magnitude to 0
     */
    public void clearHorizontalMagnitude() {
        this.horizontalMagnitude = 0;
    }

    /**
     * Sets the vertical magnitude to 0
     */
    public void clearVerticalMagnitude() {
        this.verticalMagnitude = 0;
    }

    /**
     * Clears all magnitudes
     */
    public void clearAllMagnitudes() {
        clearVerticalMagnitude();
        clearHorizontalMagnitude();
    }
}