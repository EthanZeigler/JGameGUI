package com.ethanzeigler.jgamegui.element;

/**
 * Created by ethanzeigler on 2/24/16.
 */
public class ButtonElement extends Element {
    double xLenght, yLenght;

    public ButtonElement(String resPath, double xOrig, double yOrig, int priority, double xLenght, double yLenght) {
        super(resPath, xOrig, yOrig, priority);
        this.xLenght = xLenght;
        this.yLenght = yLenght;
    }
}
