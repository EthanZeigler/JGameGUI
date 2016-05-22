package com.ethanzeigler.jgamegui.window;

import com.ethanzeigler.jgamegui.element.AbstractElement;
import com.ethanzeigler.jgamegui.element.ButtonImageElement;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A window to draw to. Elements can be added to the window.
 */
public class Window {
    private List<AbstractElement> elements = new ArrayList<>();
    private long tickCount = 1;

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
        elements.stream().forEach(element -> element.runTick(tickCount));
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
}
