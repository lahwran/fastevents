/**
 * 
 */
package net.lahwran.fevents.test;

import net.lahwran.fevents.Event;
import net.lahwran.fevents.HandlerList;
import net.lahwran.fevents.Listener;

/**
 * @author lahwran
 *
 */
public class SingletonTestEvent extends Event<SingletonTestEvent> {

    private static final SingletonTestEvent instance = new SingletonTestEvent();

    /**
     * Update singleton and return it
     * @param message message to "initialize" singleton with
     * @return SingletonTestEvent singleton
     */
    public static SingletonTestEvent update(String message) {
        instance.message = message;
        return instance;
    }

    private String message;

    private SingletonTestEvent() {
        message = null;
    }

    /**
     * Set message
     * @param newmessage message to set
     */
    public void setMessage(String newmessage) {
        this.message = newmessage;
    }

    /**
     * Get test message
     * @return this.message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * handler list.
     */
    public static final HandlerList<SingletonTestEvent> handlers = new HandlerList<SingletonTestEvent>();

    @Override
    protected HandlerList<SingletonTestEvent> getHandlers() {
        return handlers;
    }

    @Override
    protected void call(Listener<SingletonTestEvent> listener) {
        listener.onEvent(this);
    }

    @Override
    protected String getEventName() {
        return "Singleton Test Event";
    }
}
