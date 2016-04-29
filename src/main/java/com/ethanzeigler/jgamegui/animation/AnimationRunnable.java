package com.ethanzeigler.jgamegui.animation;

import com.ethanzeigler.jgamegui.element.AbstractElement;

/**
 * A listening runnable invoked when a custom Animation ({@link Animation#Animation(AnimationRunnable)}) needs to be updated.
 */
public interface AnimationRunnable {

    /**
     * Called when an animation needs to be updated.
     * <p>To cancel an animation, invoke {@link Animation#}</p>
     * @param numOfTicks the number of iterations that the animation has done so far
     * @param element The element that this animation applies to
     * @param animation The animation that invoked the update. Can be used to stop updates.
     */
    void onUpdate(long numOfTicks, AbstractElement element, Animation animation);
}
