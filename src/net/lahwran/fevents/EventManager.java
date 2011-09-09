/**
 * 
 */
package net.lahwran.fevents;

/**
 * This class doesn't actually need to exist, but it feels wrong to have this
 * part of the event call logic inside Event
 * @author lahwran
 */
public class EventManager {
    /**
     * Call an event.
     * 
     * @param <EventType> Event subclass
     * @param event Event to handle
     */
    public <EventType extends Event<EventType>> void callEvent(EventType event) {
        HandlerList<EventType> handlerlist = event.getHandlers();
        handlerlist.bake();

        Listener<EventType>[][] handlers = handlerlist.handlers;

        for (int orderidx=0; orderidx<handlers.length; orderidx++) {

            // if the order slot is even and the event has stopped propogating
            if ((orderidx & 1) == 0 && !event.isPropogating())
                continue; // then don't call this order slot

            for (int handler = 0; handler < handlers[orderidx].length; handler++) {
                Listener<EventType> listener = handlers[orderidx][handler];
                try {
                    event.call(listener);
                } catch (Throwable t) {
                    System.err.println("Error while passing event "+event);
                    t.printStackTrace();
                }
            }
        }
    }
}
