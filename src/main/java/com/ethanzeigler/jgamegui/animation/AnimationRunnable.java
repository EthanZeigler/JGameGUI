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
     * @return wether to retain the animation. True continues to run. False stops running.
     */
    boolean onUpdate(int numOfTicks, Element element);
}
