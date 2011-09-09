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

    /**
     * Dynamic handler lists. These are changed using register() and
     * unregister() and are automatically baked to the handlers array any
     * time they have changed.
     */
    private final EnumMap<Order, ArrayList<Listener<EventType>>> handlerslots;

    /**
     * Whether the current handlerslist has been fully baked. When this is set
     * to false, bakedslots will be searched for unbaked order slots next time
     * the event is called.
     * @see EventManager.callEvent
     */
    private boolean baked = false;

    /**
     * Tracks Order slots which have been baked. If a false is set in this map,
     * that order slot will be rebaked.
     */
    private final EnumMap<Order, Boolean> bakedslots;

    /**
     * List of all handlerlists which have been created, for use in bakeall()
     */
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
        handlerslots = new EnumMap<Order, ArrayList<Listener<EventType>>>(Order.class);
        bakedslots = new EnumMap<Order, Boolean>(Order.class);
        for (Order o : Order.values()) {
            handlerslots.put(o, new ArrayList<Listener<EventType>>());
            bakedslots.put(o, false);
        }
        alllists.add(this);
    }

    /**
     * Register a new listener in this handler list
     * @param listener listener to register
     * @param order order location at which to call provided listener
     */
    public void register(Listener<EventType> listener, Order order) {
        if (handlerslots.get(order).contains(listener))
            throw new IllegalStateException("This listener is already registered to order "+order.toString());
        baked = false;
        bakedslots.put(order, false);
        handlerslots.get(order).add(listener);
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
        if (handlerslots.get(order).contains(listener)) {
            baked = false;
            bakedslots.put(order, false);
            handlerslots.get(order).remove(listener);
        }
    }

    /**
     * Bake HashMap and ArrayLists to 2d array - does nothing if not necessary
     */
    void bake() {
        if (baked)
            return; // don't re-bake when still valid

        for (Entry<Order, ArrayList<Listener<EventType>>> entry : handlerslots.entrySet()) {
            Order orderslot = entry.getKey();
            if (bakedslots.get(orderslot))
                continue; // don't re-bake individual order slots, either

            ArrayList<Listener<EventType>> list = entry.getValue();

            int ord = orderslot.getIndex();
            handlers[ord] = list.toArray(new Listener[list.size()]);

            bakedslots.put(orderslot, true); // mark this order slot as baked
        }
        baked = true;
    }
}
