/*
 * Copyright (c) 2016 under the Attribution-ShareAlike 4.0 International licence.
 * See JGameGUI-licence.txt for more information
 */

package com.ethanzeigler.jgamegui.element;

/**
 * Represents an elemet with size
 */
public interface Sized {
    /**
     * Gets the width of the implementing object
     * @return the element width
     */
    double getWidth();

    /**
     * Gets the height of the object
     * @return the object height
     */
    double getHeight();
}
