import com.ethanzeigler.jgamegui.JGameGUI;
import com.ethanzeigler.jgamegui.element.Element;

/**
 * Created by ethanzeigler on 3/3/16.
 */
public class Game extends JGameGUI {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;

    public Game() {
        super(WIDTH, HEIGHT);
    }

    /**
     * Called before the window is displayed
     */
    @Override
    public void init(JGameGUI g) {
        g.setFPS(60);
        System.out.println("Started");
        Element element = new Element("/oie_l4ipM7cChF7M.png", 30, 30, 1);
        g.addElement(element);
    }

    public void onUpdate(JGameGUI g) {

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
