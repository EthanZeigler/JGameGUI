package com.ethanzeigler.jgamegui.animation;

import com.ethanzeigler.jgamegui.element.Element;

/**
 * Created by ethanzeigler on 2/26/16.
 */
public class Animation {

    /**
     * Creates a new animation that will follow the Vector path set. The Vector is mutable at runtime.
     * @param vector The vector that will represent the {@Link Element}'s movement for the given
     *               amount of ticks
     * @param totalTicks The total number of ticks (Iterations) the Element ill be moved by the Vector's magnitude
     */
    public Animation(Vector vector, int totalTicks) {
        this.vector = vector;
        this.totalTicks = totalTicks;
    }

    public Animation(AnimationRunnable runnable, int totalTicks) {
        this.runnable = runnable;
    }

    Vector vector;
    AnimationRunnable runnable;
    int totalTicks;
    int currentTicks;

    void update(Element element) {
        if(vector == null) {
            runnable.onUpdate(currentTicks, totalTicks, element);
        } else {
            element.setxOrig(element.getxOrig() + vector.getHorizontalMagnitude());
            element.setyOrig(element.getyOrig() + vector.getVerticalMagnitude());
        }
        currentTicks++;
    }
}
