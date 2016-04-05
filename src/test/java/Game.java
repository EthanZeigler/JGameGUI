import com.ethanzeigler.jgamegui.JGameGUI;
import com.ethanzeigler.jgamegui.animation.Animation;
import com.ethanzeigler.jgamegui.animation.Vector;
import com.ethanzeigler.jgamegui.element.ButtonElement;
import com.ethanzeigler.jgamegui.element.Element;

/**
 * Created by ethanzeigler on 3/3/16.
 */
public class Game extends JGameGUI {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;
    Element defaultElement;

    public Game() {
        super(WIDTH, HEIGHT);
    }

    /**
     * Called before the window is displayed
     * This can be used to set the FPS using g.setFPS and add Elements
     */
    @Override
    public void init(JGameGUI g) {
        g.setFPS(60);
        defaultElement = new Element("/piq_311577_400x400.png", 0, 0, 2);
        g.addElement(defaultElement);
    }

    /**
     * <p>Called before the GUI is updated each frame and can be used to update Element positions.
     * This is invoked <b><i>before</i></b> before
     * any animations defined in {@link Element#setAnimation(Animation)}</p>
     * <p>The more preferable alternative to overriding this is using the animating API
     * included with {@link Element#setAnimation}, however, this creates a more simplistic approach
     * for learners and backwards compatibility with previous teaching games.</p>
     *
     * @param gui The JGameGUI instance that is updating
     */
    @Override
    protected void onScreenUpdate(JGameGUI gui) {
        defaultElement.setxOrig(defaultElement.getxOrig() + 1);
        defaultElement.setyOrig(defaultElement.getyOrig() + 1);
    }

    /**
     * Invoked when the game stops (The window closes)
     */
    @Override
    public void deinit() {
        System.out.println("Stopped");
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}
