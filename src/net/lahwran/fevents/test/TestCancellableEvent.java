/**
 * 
 */
package net.lahwran.fevents.test;

import net.lahwran.fevents.CancellableEvent;
import net.lahwran.fevents.HandlerList;
import net.lahwran.fevents.Listener;

/**
 * @author lahwran
 *
 */
public class TestCancellableEvent extends CancellableEvent<TestCancellableEvent> {

    /* (non-Javadoc)
     * @see net.lahwran.fevents.Event#call(net.lahwran.fevents.Listener)
     */
    @Override
    protected void call(Listener<TestCancellableEvent> listener) {
        // TODO Auto-generated method stub
        listener.onEvent(this);
    }

    /* (non-Javadoc)
     * @see net.lahwran.fevents.Event#getEventName()
     */
    @Override
    protected String getEventName() {
        return "Test Cancellable Event";
    }

    /**
     * Handler list
     */
    public static final HandlerList<TestCancellableEvent> handlers = new HandlerList<TestCancellableEvent>();
    /* (non-Javadoc)
     * @see net.lahwran.fevents.Event#getHandlers()
     */
    @Override
    protected HandlerList<TestCancellableEvent> getHandlers() {
        // TODO Auto-generated method stub
        return handlers;
    }

}
