/**
 * 
 */
package net.lahwran.fevents;

/**
 * Event superclass. should be extended as: 
 * <pre>
 *     class MyEvent extends Event<MyEvent> {
 *         public static final HandlerList<MyEvent> handlers = new HandlerList<MyEvent>();
 *
 *         @Override
 *         HandlerList<MyEvent> getHandlers() {
 *             return handlers;
 *         }
 *         @Override
 *         void call(Listener<MyEvent> listener) {
 *             listener.onEvent(this);
 *         }
 *     }
 * </pre>
 * @author lahwran
 * @param <T> Event class
 *
 */
public abstract class Event<T extends Event<T>> {
    /**
     * Get the static handler list of this event subclass.
     * @return HandlerList to call event with
     */
    protected abstract HandlerList<T> getHandlers();

    /**
     * Call a listener for this event. Workaround for type erasure.
     * @param listener listener to call
     */
    protected abstract void call(Listener<T> listener);
}
