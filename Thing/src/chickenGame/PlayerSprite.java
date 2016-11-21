package chickenGame;

import com.ethanzeigler.jgamegui.element.CollidableImageElement;

/**
 * Created by mihalzelenin on 5/26/16.
 */
public class PlayerSprite extends CollidableImageElement {

    public PlayerSprite(String resPath, double xOrig, double yOrig, double width, double height, int priority) {
        super(resPath, xOrig, yOrig, width, height, priority);
    }
}
