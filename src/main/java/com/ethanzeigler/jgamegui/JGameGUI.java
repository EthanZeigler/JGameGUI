package com.ethanzeigler.jgamegui;


import com.ethanzeigler.jgamegui.animation.Animation;
import com.ethanzeigler.jgamegui.element.ButtonElement;
import com.ethanzeigler.jgamegui.element.Element;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ethanzeigler on 2/23/16.
 */
public abstract class JGameGUI extends JFrame implements MouseListener, MouseMotionListener, KeyListener {
    private List<Element> elements = new ArrayList<>();
    private Thread animator;
    private int width, height, frameDelay = 30;
    private boolean threadStop;
    private long tickCount = 1;

    /**
     * Creates a new JGameGUI and immediately begins to start the frame.
     **/
    public JGameGUI(int width, int height) {

        //<editor-fold desc="Window setup">
        super();
        this.width = width;
        this.height = height;
        setResizable(false);
        animator = new Thread(() -> {
            while (!threadStop) {
                try {
                    // determine time before screen update math
                    long preMills = System.currentTimeMillis(); // time before update math
                    onScreenUpdate(this); // invoke user edits on a tick
                    onTick(); // invoke animations and other updates handled by API
                    repaint(); // tell JFrame to update screen and call paint w/ thread safety
                    long sleepMills = (frameDelay + preMills) - System.currentTimeMillis(); // time after math
                    if (sleepMills > 1) Thread.sleep(sleepMills); // if additional frame time is necessary, sleep

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        animator.setDaemon(true);
        this.width = width;
        this.height = height;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBackground(Color.WHITE);
        setSize(width, height);

        //</editor-fold>

        // calls the implementing class's init method
        init(this);

        setSize(width, height);

        // add listener for window closing
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
                /*TODO debug print*/
                System.out.println("Window closed");
                deinit();
            }
        });

        // focus to receive key strokes
        setFocusable(true);
        requestFocus();

        // register listeners
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);

        // begin animation and make visible
        setVisible(true);
        animator.start();
    }

    /**
     * Called before the window is displayed
     */
    protected abstract void init(JGameGUI g);

    /**
     * Called when the VM is being disabled. This method can be used to dispose of any variables or save data.
     */
    protected void deinit() {

    }

    /**
     * Adds the Element into the GUI
     *
     * @param ie Element to add
     */
    public void addElement(Element ie) {
        elements.add(ie);
        updateDrawPriorities();
    }

    /**
     * Removes the Element from the GUI.
     *
     * @param ie Element to remove
     * @return true if Element was removed from the list of Elements
     */
    public boolean removeElement(Element ie) {
        boolean returnBool = elements.remove(ie);
        updateDrawPriorities();
        return returnBool;
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
        // create buffer to prevent issues with windows
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D bufferGraphic = bufferedImage.createGraphics();
        Graphics2D g2dComponent = (Graphics2D) g;
        super.paint(bufferGraphic);
        elements.stream().forEachOrdered(e -> e.paint(bufferGraphic));
        ++tickCount;
        g.drawImage(bufferedImage, 0, 0, null);
    }

    /**
     * Backend method for delegating actions to be taken on a tick update.
     */
    private void onTick() {
        elements.stream().forEach(element -> element.runTick(tickCount));
    }

    /**
     * <p>Called before the GUI is updated each frame and can be used to update Element positions.
     * This is invoked <i><s>before</s></i> {@link JGameGUI#onScreenUpdate(JGameGUI)} and before
     * any animations defined in {@link Element#setAnimation(Animation)}</p>
     * <p>The more preferable alternative to overriding this is using the animating API
     * included with {@link Element#setAnimation}, however, this creates a more simplistic approach
     * for learners and backwards compatibility with previous teaching games.</p>
     *
     * @param gui The JGameGUI instance that is updating
     */
    protected void onScreenUpdate(JGameGUI gui) {

    }

    /**
     * Sets the desired Frames Per Second. If the calculations needed to update a frame
     *
     * @param frames The number of frames per second desired.
     */
    public void setFPS(int frames) {
        frameDelay = 1000 / frames;
    }



    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e
     */
    @Override
    public final void mouseClicked(MouseEvent e) {
        elements.stream().filter(element -> element instanceof ButtonElement)
                .filter(element1 -> ((ButtonElement) element1).isClicked(e.getX(), e.getY()))
                .sequential().limit(1).forEach(element2 -> ((ButtonElement)element2).onClick());
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
        System.out.println("Press: " + e.getKeyChar());
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
        System.out.println("Release: " + e.getKeyChar());
    }
}
