/*
 * Copyright (c) 2016 under the Attribution-ShareAlike 4.0 International licence.
 * See JGameGUI-licence.txt for more information
 */

package com.ethanzeigler.jgamegui;

import com.ethanzeigler.jgamegui.element.AbstractElement;
import com.ethanzeigler.jgamegui.element.ButtonImageElement;
import com.sun.istack.internal.Nullable;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A window to draw to. Elements can be added to the window.
 */
public class Window {
    protected List<AbstractElement> elements = new ArrayList<>();
    protected long tickCount = 1;
    protected int width, height;
    protected ActionListener sizeChangeListener;

    public Window() {

    }

    /**
     * Gives the Window it's own width and height that overrides the default size of {@link com.ethanzeigler.jgamegui.JGameGUI}
     * @param width the width to set
     * @param height the height to set
     */
    public Window(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Removes the ImageElement from the window
     * Adds the ImageElement into the window
     *
     * @param e ImageElement to add
     */
    public void addElement(AbstractElement e) {
        elements.add(e);
        updateDrawPriorities();
    }

    /**
     * Removes the ImageElement from the window.
     *
     * @param e ImageElement to remove
     * @return true if ImageElement was removed from the list of Elements
     */
    public boolean removeElement(AbstractElement e) {
        return elements.remove(e);
    }

    /**
     * Removes all Elements from the window
     */
    public void removeAllElements() {
        elements.clear();
    }

    /**
     * Returns the elements inside the window
     * @return the elements inside the window
     */
    public List<AbstractElement> getElements() {
        return elements;
    }

    /**
     * Updates the order Elements are called in. This is called when either add or remove element are called, but
     * if priorities are changed within an element this must be called to update the drawing order.
     */
    public void updateDrawPriorities() {
        //noinspection unchecked
        Collections.sort(elements);
    }

    /**
     * Paints the window to the given graphics object
     * @param g the graphics to paint to
     */
    public void paint(Graphics g) {
        for (AbstractElement element: elements) element.paint(g);
        tickCount++;
    }

    /**
     * Backend method for delegating actions to be taken on a tick update.
     */
    public void runTick() {
        List<AbstractElement> tempElements = new ArrayList<AbstractElement>(elements);
        tempElements.stream().forEach(element -> element.runTick(tickCount));
    }

    /**
     * Runs the appropriate actions when the mouse is clicked on the window
     * @param e the MouseEvent
     */
    public void runMouseClick(MouseEvent e) {
        elements.stream().filter(element -> element instanceof ButtonImageElement)
                .filter(element1 -> ((ButtonImageElement) element1).isClicked(e.getX(), e.getY()))
                .sequential().limit(1).forEach(element2 -> ((ButtonImageElement)element2).onClick());
    }

    /**
     * Sets the width and the height of the Window. It will override the size specified by {@link com.ethanzeigler.jgamegui.JGameGUI}
     * @param width the width to display
     * @param height the height to display
     */
    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;

        if (sizeChangeListener != null)
            sizeChangeListener.actionPerformed(new ActionEvent(this, 0, "size change"));

    }

    /**
     * Gets the width of the Window when it is being displayed on screen.
     * @return the window width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * Gets the height of the Window when it is being displayed on screen.
     * @return the window height. Null if not set.
     */
    public Integer getHeight() {
        return height;
    }

    void setSizeChangeListener(ActionListener listener) {
        this.sizeChangeListener = listener;
    }

    void removeSizeChangeListener() {
        this.sizeChangeListener = null;
    }
}
