/**
 * 
 */
package net.lahwran.fevents;

/**
 * @author lahwran
 * @param <TEvent> Event type
 */
public abstract class CancellableEvent<TEvent extends CancellableEvent<TEvent>> extends Event<TEvent> {

    /**
     * Whether this event has been cancelled.
     */
    protected boolean cancelled = false;

    /**
     * If an event stops propogating (ie, is cancelled) partway through an even
     * slot, that slot will not cease execution, but future even slots will not
     * be called.
     * 
     * @param cancelled True to set event canceled, False to uncancel event.
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Get event canceled state.
     * 
     * @return whether event is cancelled
     */
    public boolean isCancelled() {
        return cancelled;
    }

    protected boolean isPropogating() {
        return !cancelled;
    }
}
