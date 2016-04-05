package com.ethanzeigler.jgamegui.animation;

import com.ethanzeigler.jgamegui.element.Element;

/**
 * Created by ethanzeigler on 2/26/16.
 */
public class Animation {
    private Vector vector;
    private AnimationRunnable runnable;
    private int totalTicks;
    private int currentTicks;

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

    public Animation(AnimationRunnable runnable) {
        this.runnable = runnable;
    }

    /**
     * Invoked when the next frame of the animation runs
     * @param element Element that the animation belongs to
     * @return Whether the animation should be terminated
     */
    public boolean update(Element element) {
        currentTicks++;
        if(vector == null) {
            return runnable.onUpdate(currentTicks, element);
        } else {
            element.setxOrig(element.getxOrig() + vector.getHorizontalMagnitude());
            element.setyOrig(element.getyOrig() + vector.getVerticalMagnitude());
            return totalTicks <= currentTicks;
        }
    }
}
