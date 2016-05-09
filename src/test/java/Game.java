import com.ethanzeigler.jgamegui.JGameGUI;
import com.ethanzeigler.jgamegui.animation.Animation;
import com.ethanzeigler.jgamegui.animation.Vector;
import com.ethanzeigler.jgamegui.element.ButtonImageElement;
import com.ethanzeigler.jgamegui.element.ImageElement;
import com.ethanzeigler.jgamegui.element.TextElement;
import com.ethanzeigler.jgamegui.window.Window;

import java.awt.*;

/**
 * Created by ethanzeigler on 3/3/16.
 */
public class Game extends JGameGUI {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;
    Window window;
    TextElement e;
    ButtonImageElement button;

    public Game() {
        super(WIDTH, HEIGHT);
    }

    /**
     * Called before the window is displayed
     * This can be used to set the FPS using g.setFPS and add Elements
     */
    @Override
    public void onStart(JGameGUI g) {
        button = new ButtonImageElement("",0,0,0,100,100);
        button.addButtonClickListener(() -> e.setColor(Color.BLACK));
        window = new Window();
        g.setFPS(50);
        e = new TextElement(0, 100, 1, "This is text");
        e.setAnimation(new Animation(new Vector(1,1), 500));
        e.setFontSize(50);
        e.setColor(Color.RED);
        window.addElement(e);
        window.addElement(button);
        g.setWindow(window);
        g.setWindow(window);
    }

    /**
     * <p>Called before the GUI is updated each frame and can be used to update ImageElement positions.
     * This is invoked <b><i>before</i></b> before
     * any animations defined in {@link ImageElement#setAnimation(Animation)}</p>
     * <p>The more preferable alternative to overriding this is using the animating API
     * included with {@link ImageElement#setAnimation}, however, this creates a more simplistic approach
     * for learners and backwards compatibility with previous teaching games.</p>
     *
     * @param gui The JGameGUI instance that is updating
     */
    @Override
    protected void onScreenUpdate(JGameGUI gui) {
        // gui.stop();
    }

    /**
     * Invoked when the game stops (The window closes)
     */
    @Override
    public void onStop() {
        System.out.println("onStop");
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}
