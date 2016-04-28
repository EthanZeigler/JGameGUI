package com.ethanzeigler.jgamegui;


import com.ethanzeigler.jgamegui.animation.Animation;
import com.ethanzeigler.jgamegui.element.AbstractElement;
import com.ethanzeigler.jgamegui.element.ButtonImageElement;
import com.ethanzeigler.jgamegui.element.ImageElement;
import com.ethanzeigler.jgamegui.sound.AudioClip;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A Java Swing game API designed and created by Ethan Zeigler, class of '16.
 * How to use this API:
 * <p>Extend JGameGUI and implement its methods. Create a new main method and create a new object of your class with your desired screen size.
 * If you received a template from Mr. Ulmer, this has already been done for you and you will change the WIDTH and HEIGHT variables.</p>
 * <p>When the game starts, init will be invoked. Here, you initialize your variables, set the frame rate using {@link JGameGUI#setFPS(int)}, which can speed up or slow down your game. About 60, which is the default, is good. load image and sound files, and add your Elements to the window stack.</p>
 * <p>JGameGUI works by the developer adding {@link AbstractElement}s to the window. These elements represent anything that
 * can be drawn to the screen such as text, images, and buttons. All of these have pre-made objects for you to use.
 * <p><br>TextElement represents written text.
 * <br>ImageElement represents a plain ol' image.
 * <br>ButtonImageElement represents an image as a button. This also requires the image's size as an argument. The image can
 * also be set to null and set to the size of the screen to detect clicks.
 * <br>CollidableImageElement has a predefined method for checking to see if two CollidableElements are touching.
 * </p>
 * <br><br>Together, these represent your screen. In the init method, create new elements and add them to the screen
 * using {@link JGameGUI#addElement(AbstractElement)}. {@link Animation}s can be applied to these Elements as well using
 * the animation API, which is well documented and I will not explain here. Note that this is for late-year AP students only.
 * First years will not understand this.</p>
 * <p>What about sound? Use the sound API. Create a new AudioClip in the init method because depending on the size of the file,
 * it can cause lag spikes when loading. Using {@link AudioClip#playOnce()}, the sound file can be played. Be sure to check out the
 * other options including {@link AudioClip#playUntilStopped()}, which will play forever until told to stop.
 * </p>
 * <p>On each screen update, the onScreenUpdate method ({@link JGameGUI#onScreenUpdate(JGameGUI)} is invoked. Here you
 * can move your elements around using their set x and y methods, as well as set new animations and image files if necessary. This is the heart of your game.</p>
 * <p>When the window is closed or you want to end the game, call the {@link JGameGUI#stop()} method which will close the window and shut down the program.
 * As the program shuts down either by the stop method or the window being closed, the deinit method is called. This can be used to save files or other things you want to do.
 * It is a good thing to call {@link AudioClip#dispose()}</p>
 */
public abstract class JGameGUI extends JFrame implements MouseListener, MouseMotionListener, KeyListener {
    private List<AbstractElement> elements = new ArrayList<>();
    private Thread animator;
    private int width, height, frameDelay = 30;
    private boolean threadStop;
    private long tickCount = 1;
    private boolean isCloseRequested = false;

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
                    if(isCloseRequested) {
                        setVisible(false);
                        deinit();
                        break;
                    }
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
     * Adds the ImageElement into the GUI
     *
     * @param e ImageElement to add
     */
    public void addElement(AbstractElement e) {
        elements.add(e);
        updateDrawPriorities();
    }

    /**
     * Removes the ImageElement from the GUI.
     *
     * @param e ImageElement to remove
     * @return true if ImageElement was removed from the list of Elements
     */
    public boolean removeElement(AbstractElement e) {
        boolean returnBool = elements.remove(e);
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
        for (AbstractElement element: elements) element.paint(bufferGraphic);
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
     * <p>Called before the GUI is updated each frame and can be used to update ImageElement positions.
     * This is invoked <i><s>before</s></i> {@link JGameGUI#onScreenUpdate(JGameGUI)} and before
     * any animations defined in {@link ImageElement#setAnimation(Animation)}</p>
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
     * Stops the JGameGUI and ends the program. This will subsequently call the deinit method.
     */
    public void stop() {
        isCloseRequested = true;
    }


    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e
     */
    @Override
    public final void mouseClicked(MouseEvent e) {
        elements.stream().filter(element -> element instanceof ButtonImageElement)
                .filter(element1 -> ((ButtonImageElement) element1).isClicked(e.getX(), e.getY()))
                .sequential().limit(1).forEach(element2 -> ((ButtonImageElement)element2).onClick());
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
     * Loads an image file from the given path. Use this instead of ImageIcon's constructor, as it is
     * temperamental with files.
     * @param filePath
     * @return
     */
    public static ImageIcon loadImageFromFile(String filePath) {
        return new ImageIcon(JGameGUI.class.getResource("/" + filePath));
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
