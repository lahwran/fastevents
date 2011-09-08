/**
 * 
 */
package net.lahwran.fevents;

/**
 * @author lahwran
 *
 */
public class EventManager {
    /**
     * @param <EventType> Event subclass
     * @param event Event to handle
     */
    public <EventType extends Event<EventType>> void callEvent(EventType event) {
        HandlerList<EventType> handlerlist = event.getHandlers();
        handlerlist.bake();
        Listener<EventType>[][] handlers = handlerlist.handlers;
        for (int orderidx=0; orderidx<handlers.length; orderidx++) {
            for (int handler = 0; handler < handlers[orderidx].length; handler++) {
                event.call(handlers[orderidx][handler]);
            }
        }
    }
}
