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
        window=new Window();
        button=new ButtonImageElement("square.png",0,0,0,600,600);
        button.addButtonClickListener(() -> System.out.println("Button"));
        window.addElement(button);
        g.setWindow(window);
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}
