package com.ethanzeigler.jgamegui.element;

import com.ethanzeigler.jgamegui.JGameGUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ethanzeigler on 2/24/16.
 */
public class ImageElement extends AbstractElement {
    protected Image img;

    /**
     * Initiates an image element with the given image resource path, x origin, y origin, and draw priority.
     * @param resPath the image resource path
     * @param xOrig the x origin
     * @param yOrig the y origin
     * @param priority the drawing priority. The higher the priority, the later it is drawn, and over others with lower priorities
     */
    public ImageElement(String resPath, double xOrig, double yOrig, int priority) {
        this(JGameGUI.loadImageFromFile(resPath), xOrig, yOrig, priority);
    }

    /**
     * Initiates an image element with the given ImageIcon, x origin, y origin, and draw priority.
     * @param resPath the ImageIcon to draw
     * @param xOrig the x origin
     * @param yOrig the y origin
     * @param priority the drawing priority. The higher the priority, the later it is drawn, and over others with lower priorities
     */
    public ImageElement(ImageIcon icon, double xOrig, double yOrig, int priority) {
        super(xOrig, yOrig, priority);
        setImg(icon);
    }

    /**
     * Gets the Image of the ImageElement
     *
     * @return Image displayed by the ImageElement
     */
    public Image getImg() {
        return img;
    }

    /**
     * Sets the Image of the ImageElement
     *
     * @param img Image to set
     */
    public void setImg(Image img) {
        this.img = img;
    }

    /**
     * Sets the Image of the ImageElement
     *
     * @param img Image to set
     */
    public void setImg(ImageIcon img) {
        this.img = img.getImage();
    }

    /**
     * Sets the Image to display from the given file
     *
     * @param filePath Path to file to display
     */
    public void setImg(String filePath) {
        System.out.println(this.getClass().getResource(filePath));
        this.img = new ImageIcon(this.getClass().getResource(filePath)).getImage();
    }


    /**
     * Draws the {@link ImageElement} to the {@link Graphics} object.
     *
     * @param g Graphics to draw to
     */
    public void paint(Graphics g) {
        g.drawImage(img, (int) xOrig, (int) yOrig, null);
    }
}
