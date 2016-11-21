package chickenGame;

import com.ethanzeigler.jgamegui.JGameGUI;
import com.ethanzeigler.jgamegui.animation.Animation;
import com.ethanzeigler.jgamegui.animation.AnimationRunnable;
import com.ethanzeigler.jgamegui.element.AbstractElement;
import com.ethanzeigler.jgamegui.element.ButtonClickListener;
import com.ethanzeigler.jgamegui.element.ButtonImageElement;
import com.ethanzeigler.jgamegui.element.CollidableImageElement;
import com.ethanzeigler.jgamegui.Window;

/**
 * Created by mihalzelenin on 5/20/16.
 */
public class ChickenLittle extends JGameGUI
{
    private Window startMenu, tutorial, level1;
    private ButtonImageElement startButton;
    private CollidableImageElement playerSprite, floor;

    public ChickenLittle(int width, int height) {super(width, height);}

    @Override
    protected void onStart(JGameGUI jGameGUI) {
        startMenu = new Window();
        tutorial = new Window();
        level1 = new Window();
        startButton = new ButtonImageElement("resources/protostart.png", 200,150, 100, 50, 0);
        playerSprite = new CollidableImageElement("resources/protochick.png",100,100,60,30,0);
        floor = new CollidableImageElement("border1.png",0,780,20,800,0);

        startButton.addButtonClickListener(() -> jGameGUI.setWindow(tutorial));
        

        playerSprite.setAnimation(new Animation((l, abstractElement, animation) -> {
            if (!playerSprite.isCollidingWith(floor))
              {
                  playerSprite.moveVertically(l/4);
              }
        }));

        startMenu.addElement(startButton);
        tutorial.addElement(playerSprite);
        tutorial.addElement(floor);

        jGameGUI.setWindow(startMenu);
    }

    public static void main(String[] args) {new ChickenLittle(800,800);}
}
