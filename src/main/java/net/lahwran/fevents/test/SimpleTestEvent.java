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
public class SimpleTestEvent extends Event<SimpleTestEvent> {

    private int number;

    /**
     * Create a new SimpleTestEvent with the given number
     * @param number some number to pass to handlers
     */
    public SimpleTestEvent(int number) {
        this.number = number;
    }

    /**
     * Set number
     * @param newnumber new number to set
     */
    public void setNumber(int newnumber) {
        this.number = newnumber;
    }

    /**
     * @return event's number
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * Handler list
     */
    public static final HandlerList<SimpleTestEvent> handlers = new HandlerList<SimpleTestEvent>();

    @Override
    protected HandlerList<SimpleTestEvent> getHandlers() {
        return handlers;
    }

    @Override
    protected void call(Listener<SimpleTestEvent> listener) {
        listener.onEvent(this);
    }

    @Override
    protected String getEventName() {
        return "Simple Test Event";
    }
}
