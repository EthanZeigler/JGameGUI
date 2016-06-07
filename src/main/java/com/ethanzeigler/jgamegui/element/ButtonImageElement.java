/*
 * Copyright (c) 2016 under the Attribution-ShareAlike 4.0 International licence.
 * See JGameGUI-licence.txt for more information
 */

package com.ethanzeigler.jgamegui.element;

import com.ethanzeigler.jgamegui.JGameGUI;
import com.sun.istack.internal.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a button on the screen with a clickable area defined in the constructor and it's image.
 * <p>To strictly create
 * a clickable area without an image attached to it, the image can be set to null and nothing will be drawn, but the
 * clickable area will still exist.</p>
 */
public class ButtonImageElement extends ImageElement implements Sized {
    protected double width, height;
    protected List<ButtonClickListener> clickListeners = new ArrayList<>();

    /**
     * An ImageElement that listens for clicks on it.
     * @param resPath The path to the image resource to display
     * @param xOrig the x origin
     * @param yOrig the y origin
     * @param width the clickable area's width
     * @param height the clickable area's height
     * @param priority the drawing priority. The higher the priority, the later it is drawn, and over others with lower priorities
     */
    public ButtonImageElement(@Nullable String resPath, double xOrig, double yOrig, double width, double height, int priority) {
        this(JGameGUI.loadImageFromFile(resPath), xOrig, yOrig, width, height, priority);
    }

    /**
     * An ImageElement that listens for clicks on it.
     * @param icon The ImageIcon to display
     * @param xOrig the x origin
     * @param yOrig the y origin
     * @param width the clickable area's width
     * @param height the clickable area's height
     * @param priority An ImageElement that listens for clicks on it.
     */
    public ButtonImageElement(@Nullable ImageIcon icon, double xOrig, double yOrig, double width, double height, int priority) {
        super(icon, xOrig, yOrig, priority);
        this.width = width;
        this.height = height;
    }

    /**
     * Gets if the given coordinates land within the click area
     * @param x the x coordinate
     * @param y the y coordinate
     * @return whether or not the button was clicked
     */
    public boolean isClicked(double x, double y) {
        return (x >= xOrig && x <= (width + xOrig)) && (y >= yOrig && y <= (height + yOrig));
    }

    /**
     * Invoked when the button has been clicked
     */
    public final void onClick() {
        clickListeners.forEach(ButtonClickListener::onClick);
    }

    /**
     * Adds a new ButtonClickListener to the button. {@link ButtonClickListener#onClick()} will be invoked when the button is clicked
     * @param listener the listener
     */
    public void addButtonClickListener(ButtonClickListener listener) {
        clickListeners.add(listener);
    }

    /**
     * Removed a ButtonClickListener from the button.
     * @param listener the listener to remove
     * @return true if a listener was removed
     */
    public boolean removeButtonClickListener(ButtonClickListener listener) {
        return clickListeners.remove(listener);
    }

    /**
     * Gets the width of the element
     *
     * @return the element width
     */
    @Override
    public double getWidth() {
        return width;
    }

    /**
     * Gets the height of the element
     *
     * @ the element height
     */
    @Override
    public double getHeight() {
        return height;
    }
}
