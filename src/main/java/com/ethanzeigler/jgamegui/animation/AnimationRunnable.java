package com.ethanzeigler.jgamegui.animation;

import com.ethanzeigler.jgamegui.element.Element;

/**
 * Created by ethanzeigler on 2/26/16.
 */
public interface AnimationRunnable {

    /**
     * Called when an animation needs to be updated.
     * @param numOfTicks
     * @param element
     * @return whether or not the animation should terminate. If true, {@link Element}'s animation field
     * will become null.
     */
    boolean onUpdate(int numOfTicks, Element element);
}
