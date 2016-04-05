package com.ethanzeigler.jgamegui.element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ethanzeigler on 2/24/16.
 */
public class ButtonElement extends Element {
    private double xLenght, yLenght;
    private List<ButtonClickListener> clickListeners = new ArrayList<>();

    public ButtonElement(String resPath, double xOrig, double yOrig, int priority, double xLenght, double yLenght) {
        super(resPath, xOrig, yOrig, priority);
        this.xLenght = xLenght;
        this.yLenght = yLenght;
    }

    public boolean isClicked(double x, double y) {
        if((x >= xOrig && x <= (xLenght + xOrig)) && (y >= yOrig && y <= (yLenght + yOrig))) return true;
        return false;
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

}
