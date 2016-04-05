package com.ethanzeigler.jgamegui.element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ethanzeigler on 2/24/16.
 */
public class ButtonElement extends Element implements SizedElement {
    private double width, height;
    private List<ButtonClickListener> clickListeners = new ArrayList<>();

    public ButtonElement(String resPath, double xOrig, double yOrig, int priority, double width, double height) {
        super(resPath, xOrig, yOrig, priority);
        this.width = width;
        this.height = height;
    }

    public boolean isClicked(double x, double y) {
        return (x >= xOrig && x <= (width + xOrig)) && (y >= yOrig && y <= (height + yOrig));
    }

    public final void onClick() {
        clickListeners.forEach(ButtonClickListener::onClick);
    }

    public void addButtonClickListener(ButtonClickListener listener) {
        clickListeners.add(listener);
    }

    public boolean removeButtonClickListener(ButtonClickListener listener) {
        return clickListeners.remove(listener);
    }


    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }
}
