package com.ethanzeigler.jgamegui.element;

import javax.swing.*;

/**
 * Created by ethanzeigler on 4/5/16.
 */
public class CollidableElement extends Element implements SizedElement {
    private double height, width;

    public CollidableElement(String resPath, double xOrig, double yOrig, int priority, double height, double width) {
        super(resPath, xOrig, yOrig, priority);
        this.height = height;
        this.width = width;
    }

    public CollidableElement(ImageIcon icon, double xOrig, double yOrig, int priority, double width, double height) {
        super(icon, xOrig, yOrig, priority);
        this.width = width;
        this.height = height;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }
}
