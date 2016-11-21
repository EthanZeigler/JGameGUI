import com.ethanzeigler.jgamegui.JGameGUI;
import com.ethanzeigler.jgamegui.element.ButtonImageElement;
import com.ethanzeigler.jgamegui.Window;
import com.ethanzeigler.jgamegui.Window;
import com.ethanzeigler.jgamegui.element.TextElement;

import java.awt.event.KeyEvent;

/**
 * Created by ethanzeigler on 3/3/16.
 */
public class Game extends JGameGUI {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 900;
    Window window;
    ButtonImageElement button;
    TextElement textElement;

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
        button=new ButtonImageElement("square.png",0,0, 600, 600, 0);
        textElement = new TextElement(100, 100, 10, "THIS IS A TEST");
        textElement.setFontSize(30);
        button.addButtonClickListener(() -> {
            System.out.println("Clicked");
            window.setDimensions(1000, 1000);
        });
        window.addElement(button);
        window.addElement(textElement);
        g.setWindow(window);
    }

    public static void main(String[] args) {
        Game game = new Game();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("Key Code: " + e.getKeyCode());
        System.out.println("Key Char: " + e.getKeyChar());
        super.keyTyped(e);

        if (e.getKeyCode() == KeyEvent.VK_Z) {
            System.out.println("Clicked z");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            // key was released
        }
    }
}
