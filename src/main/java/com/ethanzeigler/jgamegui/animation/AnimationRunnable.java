package com.ethanzeigler.jgamegui.animation;

import com.ethanzeigler.jgamegui.element.Element;

/**
 * Created by ethanzeigler on 2/26/16.
 */
public interface AnimationRunnable {

    /**
     * Called when an animation needs to be updated.
     * @param numOfTicks
     * @param numOfTicksRemaining
     * @param vector
     * @param element
     */
    void onUpdate(int numOfTicks, int numOfTicksRemaining, Element element);
}
