import com.ethanzeigler.jgamegui.JGameGUI;
import com.ethanzeigler.jgamegui.animation.Animation;
import com.ethanzeigler.jgamegui.animation.AnimationRunnable;
import com.ethanzeigler.jgamegui.element.*;
import com.ethanzeigler.jgamegui.Window;

import java.awt.*;


/**
 * Created by mihalzelenin on 5/10/16.
 */
public class GameBitsTest extends JGameGUI
{

    private TextElement text;
    private Window window, window2;
    private ButtonImageElement button;
    private CollidableImageElement floor;
    private CollidableImageElement bouncy;

    public GameBitsTest(int width, int height) {
        super(width, height);
    }

    @Override
    protected void onStart(JGameGUI jGameGUI) {
        window = new Window();
        window2= new Window();
        text = new TextElement(0, 100, 1, "Yay for APIs!");
        text.setColor(Color.CYAN);
        text.setAnimation(new Animation(new AnimationRunnable() {
            @Override
            public void onUpdate(long l, AbstractElement abstractElement, Animation animation) {
                text.moveHorizontally(2);
                text.moveVertically(l/4);
            }
        }));
        button = new ButtonImageElement("500block.png",0,50, 500, 500, 0);
        button.addButtonClickListener(new ButtonClickListener() {
            @Override
            public void onClick() {

                System.out.println("Worked");
            }
        });
        floor = new CollidableImageElement("border1.png",0,780,0,20,800);
        bouncy = new CollidableImageElement("spiky.png", 100,0,0,500,500);
        bouncy.setAnimation(new Animation(new AnimationRunnable() {
            private int vert=2;
            @Override
            public void onUpdate(long l, AbstractElement abstractElement, Animation animation) {
                bouncy.moveVertically(vert);
                if(bouncy.isCollidingWith(floor)) {
                    vert=-vert;
                }
            }
        }));
        window.addElement(button);
        window2.addElement(text);
        window2.addElement(bouncy);
        window2.addElement(floor);
        jGameGUI.setWindow(window);
    }

    public static void main(String[] args) {
        new GameBitsTest(800,800);
    }

}
