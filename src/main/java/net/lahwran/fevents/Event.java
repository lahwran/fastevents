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
 * @param <TEvent> Event class
 *
 */
public abstract class Event<TEvent extends Event<TEvent>> {
    /**
     * Get the static handler list of this event subclass.
     * 
     * @return HandlerList to call event with
     */
    protected abstract HandlerList<TEvent> getHandlers();

    /**
     * Call a listener for this event. Workaround for type erasure.
     * 
     * @param listener listener to call
     */
    protected abstract void call(Listener<TEvent> listener);

    /**
     * Get event type name.
     * 
     * @return event name
     */
    protected abstract String getEventName();

    public String toString() {
        return getEventName()+" ("+this.getClass().getName()+")";
    }

    /**
     * Returning false will prevent calling any even Order slots. For use in
     * cancelable events and such.
     * 
     * @see Order
     * @return true if the event is propogating; default implementation always
     *             returns true.
     */
    protected boolean isPropogating() {
        return true;
    }
}
