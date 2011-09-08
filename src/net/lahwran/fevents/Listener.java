/**
 * 
 */
package net.lahwran.fevents;

/**
 * @author lahwran
 * @param <EventType> Event type
 */
public interface Listener<EventType extends Event<EventType>> {
    /**
     * Handle an event
     * @param event Event to handle
     */
    public void onEvent(EventType event);
}
