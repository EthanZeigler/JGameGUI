package com.ethanzeigler.jgamegui;


import com.ethanzeigler.jgamegui.element.Element;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ethanzeigler on 2/23/16.
 */
public abstract class JGameGUI extends JFrame implements KeyListener, MouseListener, MouseMotionListener {
    private Thread animator;
    private int tickCount;
    private int width, height, frameDelay = 30;
    private boolean threadStop;

    List<Element> elements = new ArrayList<>();

    /**
     * Creates a new <code>JPanel</code> with <code>FlowLayout</code>
     * and the specified buffering strategy.
     * If <code>isDoubleBuffered</code> is true, the <code>JPanel</code>
     * will use a double buffer.
     *
     * @param isDoubleBuffered a boolean, true for double-buffering, which
     *                         uses additional memory space to achieve fast, flicker-free
     *                         updates
     */
    public JGameGUI(int width, int height) {
        super();
        animator = new Thread(() -> {
            while(!threadStop) {
                try {
                    long preMills = System.currentTimeMillis();
                    repaint();
                    long sleepMills = (frameDelay + preMills) - System.currentTimeMillis();
                    // if additional frame time is necessary, sleep
                    if(sleepMills > 1) Thread.sleep(sleepMills);

                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
        animator.setDaemon(true);
        this.width = width;
        this.height = height;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        init(this);
        setSize(width, height);

        addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window has been closed.
             *
             * @param e
             */
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                threadStop = true;
                try {
                    animator.join();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                /*TODO debug print*/ System.out.println("Window closed");
                deinit();
            }
        });
        setVisible(true);
        animator.start();
        /*TODO debug print*/ System.out.println(animator + "Init");
    }

    /**
     * Called before the window is displayed
     */
    public abstract void init(JGameGUI g);

    /**
     * Called when the VM is being disabled. This method can be used to dispose of any variables or save data.
     */
    public void deinit() {

    }

    /**
     * Adds the Element into the GUI
     * @param ie Element to add
     */
    public void addElement(Element ie) {
        elements.add(ie);
    }

    /**
     * Removes the Element from the GUI.
     * @param ie Element to remove
     * @return true if Element was removed from the list of Elements
     */
    public boolean removeElement(Element ie) {
        return elements.remove(ie);
    }

    /**
     * Removes all Elements from the GUI
     */
    public void removeAllElements() {
        elements.clear();
    }

    /**
     * Updates the order Elements are called in. This is called when either add or remove element are called, but
     * if priorities are changed within an element this must be called to update the drawing order.
     */
    public void updateDrawPriorities() {
        Collections.sort(elements);
    }



    /**
     * Invoked by Swing to draw components.
     * Applications should not invoke <code>paint</code> directly,
     * but should instead use the <code>repaint</code> method to
     * schedule the component for redrawing.
     * <p>
     * This method actually delegates the work of painting to three
     * protected methods: <code>paintComponent</code>,
     * <code>paintBorder</code>,
     * and <code>paintChildren</code>.  They're called in the order
     * listed to ensure that children appear on top of component itself.
     * Generally speaking, the component and its children should not
     * paint in the insets area allocated to the border. Subclasses can
     * just override this method, as always.  A subclass that just
     * wants to specialize the UI (look and feel) delegate's
     * <code>paint</code> method should just override
     * <code>paintComponent</code>.
     *
     * @param g the <code>Graphics</code> context in which to paint
     * @see #repaint
     */
    @Override
    public void paint(Graphics g) {
        ++tickCount;
        elements.stream().forEachOrdered(e -> e.paint(g));
        if(tickCount % 60 == 0) {
            System.out.println(tickCount);
        }
    }

    /**
     * Sets the desired Frames Per Second. If the calculations needed to update a frame
     * @param frames The number of frames per second desired.
     */
    public void setFPS(int frames) {
        frameDelay = 1000 / frames;
    }


    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {

    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  <code>MOUSE_DRAGGED</code> events will continue to be
     * delivered to the component where the drag originated until the
     * mouse button is released (regardless of whether the mouse position
     * is within the bounds of the component).
     * <p>
     * Due to platform-dependent Drag&amp;Drop implementations,
     * <code>MOUSE_DRAGGED</code> events may not be delivered during a native
     * Drag&amp;Drop operation.
     *
     * @param e
     */
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e
     */
    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
