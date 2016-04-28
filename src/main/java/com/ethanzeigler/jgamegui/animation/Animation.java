package com.ethanzeigler.jgamegui.animation;

import com.ethanzeigler.jgamegui.element.AbstractElement;

/**
 * Created by ethanzeigler on 2/26/16.
 */
public class Animation {
    /**
     * Represents an infinite number of tick iterations in an animation
     */
    public static final long INFINITE_TICKS = -1;
    private Vector vector;
    private AnimationRunnable runnable;
    private long totalTicks;
    private long currentTicks;
    private boolean doCancel;

    /**
     * Creates a new animation that will follow the Vector path set. The Vector is mutable at runtime.
     *
     * @param vector     The vector that will represent the {@Link ImageElement}'s movement for the given
     *                   amount of ticks
     * @param totalTicks The total number of ticks (Iterations) the ImageElement ill be moved by the Vector's magnitude
     */
    public Animation(Vector vector, long totalTicks) {
        this.vector = vector;
        this.totalTicks = totalTicks;
    }

    /**
     * Creates a new animation moving hm pixels on the x axis and vm pixels on the y axis each screen update for totalTicks iterations
     * @param hm pixels moved on the x axis each iteration
     * @param vm pixels moved on the y axis each iteration
     * @param totalTicks the total amount of iterations before the animation self-cancels
     */
    public Animation(double hm, double vm, long totalTicks) {
        this.vector = new Vector(hm, vm);
        this.totalTicks = totalTicks;
    }

    /**
     * Creates a new animation that invokes the runnable AnimationRunnable on each iteration.
     * @param runnable runnable to be invoked on each iteration
     */
    public Animation(AnimationRunnable runnable) {
        this.runnable = runnable;
    }

    /**
     * Invoked when the next frame of the animation runs
     *
     * @param element ImageElement that the animation belongs to
     * @return Whether the animation should be terminated
     */
    public final boolean update(AbstractElement element) {
        // check if the animation has been requested to terminate
        if(this.doCancel) return true;

        currentTicks++;
        if (vector == null) {
            runnable.onUpdate(currentTicks, element, this);
            return this.doCancel;
        } else {
            element.setxOrig(element.getxOrig() + vector.getHorizontalMagnitude());
            element.setyOrig(element.getyOrig() + vector.getVerticalMagnitude());
            return totalTicks <= currentTicks;
        }
    }

    /**
     * Stops the animation. This is thread-safe.
     */
    public void cancel() {
        this.doCancel = true;
    }
}
