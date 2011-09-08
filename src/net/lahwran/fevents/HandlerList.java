/**
 * 
 */
package net.lahwran.fevents;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map.Entry;

/**
 * @author lahwran
 * @param <EventType> Event type
 *
 */
@SuppressWarnings("unchecked")
public class HandlerList<EventType extends Event<EventType>> {
    /**
     * handler array. this field being an array is the key to this system's speed
     */
    final Listener<EventType>[][] handlers = new Listener[Order.values().length][];
    private final EnumMap<Order, ArrayList<Listener<EventType>>> handlerslist; 
    private boolean baked = false;
    private final EnumMap<Order, Boolean> bakedlist;

    private static ArrayList<HandlerList> alllists = new ArrayList<HandlerList>();

    /**
     * Bake all handler lists. Best used just after all normal event
     * registration is complete, ie just after all plugins are loaded if
     * you're using fevents in a plugin system.
     */
    public static void bakeall() {
        for (HandlerList h : alllists) {
            h.bake();
        }
    }

    /**
     * Create a new handler list and initialize using EventManager.Order
     * handlerlist is then added to meta-list for use in bakeall()
     */
    public HandlerList() {
        handlerslist = new EnumMap<Order, ArrayList<Listener<EventType>>>(Order.class);
        bakedlist = new EnumMap<Order, Boolean>(Order.class);
        for (Order o : Order.values()) {
            handlerslist.put(o, new ArrayList<Listener<EventType>>());
            bakedlist.put(o, false);
        }
        alllists.add(this);
    }

    /**
     * Register a new listener in this handler list
     * @param listener listener to register
     * @param order order location at which to call provided listener
     */
    public void register(Listener<EventType> listener, Order order) {
        if (handlerslist.get(order).contains(listener))
            throw new IllegalStateException("This listener is already registered to order "+order.toString());
        baked = false;
        bakedlist.put(order, false);
        handlerslist.get(order).add(listener);
    }

    /**
     * Remove a listener from all order slots
     * @param listener listener to purge
     */
    public void unregister(Listener<EventType> listener) {
        for (Order o : Order.values()) {
            unregister(listener, o);
        }
    }

    /**
     * Remove a listener from a specific order slot
     * @param listener listener to remove
     * @param order order from which to remove listener
     */
    public void unregister(Listener<EventType> listener, Order order) {
        if (handlerslist.get(order).contains(listener)) {
            baked = false;
            bakedlist.put(order, false);
            handlerslist.get(order).remove(listener);
        }
    }

    /**
     * Bake HashMap and ArrayLists to 2d array - does nothing if not necessary
     */
    void bake() {
        if (baked)
            return; // don't re-bake when still valid
        for (Entry<Order, ArrayList<Listener<EventType>>> entry : handlerslist.entrySet()) {
            if (bakedlist.get(entry.getKey()))
                continue; // don't re-bake individual order slots, either
            int ord = entry.getKey().getIndex();
            handlers[ord] = entry.getValue().toArray(new Listener[entry.getValue().size()]);
            bakedlist.put(entry.getKey(), true);
        }
        baked = true;
    }
}
