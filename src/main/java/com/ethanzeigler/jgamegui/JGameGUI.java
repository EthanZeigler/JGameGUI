package com.ethanzeigler.jgamegui;


import com.ethanzeigler.jgamegui.animation.Animation;
import com.ethanzeigler.jgamegui.element.AbstractElement;
import com.ethanzeigler.jgamegui.element.ImageElement;
import com.ethanzeigler.jgamegui.sound.AudioClip;
import com.ethanzeigler.jgamegui.window.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * A Java Swing game API designed and created by Ethan Zeigler, class of '16.
 * How to use this API:
 * <p>Extend JGameGUI and implement its methods. Create a new main method and create a new object of your class with your desired screen size.
 * If you received a template from Mr. Ulmer, this has already been done for you and you will change the WIDTH and HEIGHT variables.</p>
 * <p>When the game starts, onStart will be invoked. Here, you initialize your variables, set the frame rate using {@link JGameGUI#setFPS(int)}, which can speed up or slow down your game. About 60, which is the default, is good. load image and sound files, and add your Elements to the window stack.</p>
 * <p>JGameGUI works by the developer adding {@link AbstractElement}s to the window. These elements represent anything that
 * can be drawn to the screen such as text, images, and buttons. All of these have pre-made objects for you to use.
 * <p><br>TextElement represents written text.
 * <br>ImageElement represents a plain ol' image.
 * <br>ButtonImageElement represents an image as a button. This also requires the image's size as an argument. The image can
 * also be set to null and set to the size of the screen to detect clicks.
 * <br>CollidableImageElement has a predefined method for checking to see if two CollidableElements are touching.
 * </p>
 * <br><br>Together, these represent your screen. In the onStart method, create new elements and add them to the screen
 * using . {@link Animation}s can be applied to these Elements as well using
 * the animation API, which is well documented and I will not explain here. Note that this is for late-year AP students only.
 * First years will not understand this.</p>
 * <p>What about sound? Use the sound API. Create a new AudioClip in the onStart method because depending on the size of the file,
 * it can cause lag spikes when loading. Using {@link AudioClip#playOnce()}, the sound file can be played. Be sure to check out the
 * other options including {@link AudioClip#playUntilStopped()}, which will play forever until told to stop.
 * </p>
 * <p>On each screen update, the onScreenUpdate method ({@link JGameGUI#onScreenUpdate(JGameGUI)} is invoked. Here you
 * can move your elements around using their set x and y methods, as well as set new animations and image files if necessary. This is the heart of your game.</p>
 * <p>When the window is closed or you want to end the game, call the {@link JGameGUI#stop()} method which will close the window and shut down the program.
 * As the program shuts down either by the stop method or the window being closed, the onStop method is called. This can be used to save files or other things you want to do.
 * It is a good thing to call {@link AudioClip#dispose()}</p>
 */
public abstract class JGameGUI extends JFrame implements MouseListener, MouseMotionListener, KeyListener {
    private Window activeWindow;
    private Window nextWindow;
    private Thread animator;
    private int width, height, frameDelay = 30;
    private boolean threadStop;
    private long tickCount = 1;
    private boolean hasProgrammicallyClosed = false;
    private boolean hasUserClosed = false;

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
                    // System.out.println("Thread stop status: " +threadStop);
                    // determine time before screen update math
                    long preMills = System.currentTimeMillis(); // time before update math
                    onScreenUpdate(this); // invoke user edits on a tick
                    onTick(); // invoke animations and other updates handled by API
                    repaint(); // tell JFrame to update screen and call paint w/ thread safety

                    if (hasProgrammicallyClosed) {
                        // System.out.println("Close programmatically requested");
                        setVisible(false);
                        threadStop = true;
                        break;
                    } else if (hasUserClosed) {
                        // System.out.println("User closed window");
                        threadStop = true;
                    }

                    long sleepMills = (frameDelay + preMills) - System.currentTimeMillis(); // time after math
                    if (sleepMills > 1)
                        Thread.sleep(sleepMills); // if additional frame time is necessary, sleep
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }

            // System.out.println("Animator is terminating");
            if (hasProgrammicallyClosed)
                JGameGUI.this.dispose();
        });
        animator.setDaemon(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBackground(Color.WHITE);
        setSize(width, height);

        //</editor-fold>

        // calls the implementing class's onStart method
        onStart(this);
        this.activeWindow = nextWindow;
        nextWindow = null;

        setSize(width, height);

        // add listener for window closing
        addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window has been closed.
             */
            @Override
            public void windowClosed(WindowEvent e) {
                // System.out.println("Window closed");
                if (!hasProgrammicallyClosed)
                    hasUserClosed = true;
                try {
                    // System.out.println("Thread lock on animator entered");
                    animator.join();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } finally {
                    // System.out.println("Animator thread lock ended. Deiniting");
                    onStop();
                }
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
    protected abstract void onStart(JGameGUI g);

    /**
     * Called when the VM is being disabled. This method can be used to dispose of any variables or save data.
     */
    protected void onStop() {

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
        super.paint(bufferGraphic);
        if (validateWindow())
            activeWindow.paint(bufferGraphic);
        g.drawImage(bufferedImage, 0, 0, null);
        ++tickCount;

        // see if new window has been assigned
        if(nextWindow != null) {
            activeWindow = nextWindow;
            nextWindow = null;
        }
    }

    /**
     * Backend method for delegating actions to be taken on a tick update.
     */
    public void onTick() {
        if (validateWindow())
        activeWindow.runTick(tickCount);
    }

    /**
     * <p>Called before the GUI is updated each frame and can be used to update ImageElement positions.
     * This is invoked <i><s>before</s></i>
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
     * Stops the JGameGUI and ends the program. This will subsequently call the onStop method.
     */
    public void stop() {
        hasProgrammicallyClosed = true;
    }

    /**
     * Gets the currently displayed window
     * @return the currently displayed window
     */
    public Window getWindow() {
        return activeWindow;
    }

    /**
     * Sets the next window to be displayed. Is thread safe and will replace the current window at the next screen refresh.
     * @param nextWindow the next window to display
     */
    public void setWindow(Window nextWindow) {
        this.nextWindow = nextWindow;
    }


    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the MouseEvent
     */
    @Override
    public final void mouseClicked(MouseEvent e) {
        if (validateWindow())
            activeWindow.runMouseClick(e);
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the MouseEvent
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Loads an image file from the given path. Use this instead of ImageIcon's constructor, as it is
     * temperamental with files.
     * @param filePath the path to the image file
     * @return the ImageIcon if found
     */
    public static ImageIcon loadImageFromFile(String filePath) {
        return new ImageIcon(JGameGUI.class.getResource("/" + filePath));
    }

    /**
     * Checks if the current window is valid
     * @return is the current window is valid
     */
    private boolean validateWindow() {
        return activeWindow != null;
    }
    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the MouseEvent
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the MouseEvent
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the MouseEvent
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
     * @param e the MouseEvent
     */
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e the MouseEvent
     */
    @Override
    public void mouseMoved(MouseEvent e) {

    }


    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the KeyEvent
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the KeyEvent
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
     * @param e the KeyEvent
     */
    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Release: " + e.getKeyChar());
    }
}
