package com.ethanzeigler.jgamegui.element;

import com.ethanzeigler.jgamegui.JGameGUI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a button on the screen with a clickable area defined in the constructor and it's image.
 * <p>To strictly create
 * a clickable area without an image attached to it, the image can be set to null and nothing will be drawn, but the
 * clickable area will still exist.</p>
 */
public class ButtonImage extends ImageElement implements Sized {
    private double width, height;
    private List<ButtonClickListener> clickListeners = new ArrayList<>();

    /**
     * An ImageElement that listens for clicks on it.
     * @param resPath The path to the image resource to display
     * @param xOrig the x origin
     * @param yOrig the y origin
     * @param priority the drawing priority. The higher the priority, the later it is drawn, and over others with lower priorities
     * @param width the clickable area's width
     * @param height the clickable area's height
     */
    public ButtonImage(String resPath, double xOrig, double yOrig, int priority, double width, double height) {
        this(JGameGUI.loadImageFromFile(resPath), xOrig, yOrig, priority, width, height);
    }

    /**
     * An ImageElement that listens for clicks on it.
     * @param icon The ImageIcon to display
     * @param xOrig the x origin
     * @param yOrig the y origin
     * @param priority An ImageElement that listens for clicks on it.
     * @param width the clickable area's width
     * @param height the clickable area's height
     */
    public ButtonImage(ImageIcon icon, double xOrig, double yOrig, int priority, double width, double height) {
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
        return 0;
    }

    /**
     * Gets the height of the element
     *
     * @ the element height
     */
    @Override
    public double getHeight() {
        return 0;
    }
}
