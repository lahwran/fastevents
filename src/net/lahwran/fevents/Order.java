package net.lahwran.fevents;

/**
 * Order of event listener calls.
 * 
 * Odd-numbered slots are called even when
 * events are marked "not propogating". If an event stops propogating partway
 * through an even slot, that slot will not cease execution, but future even
 * slots will not be called.
 * 
 * @author lahwran
 */
public enum Order {
    /**
     * Called before all other handlers.
     * Should be used for high-priority event canceling.
     */
    Earlist(0),

    /**
     * Called after "Earliest" handlers and before "Early" handlers. Is called
     * even when event has been canceled. Should generally be used to uncancel
     * events canceled in Earliest.
     */
    Early_IgnoreCancelled(1),

    /**
     * Called after "Earliest" handlers. Should generally be used for low
     * priority event canceling.
     */
    Early(2),

    /**
     * Called after "Early" handlers and before "Default" handlers. Is called
     * even when event has been canceled. This is for general-purpose
     * always-run events.
     */
    Default_IgnoreCancelled(3),
    /**
     * Default call, for general purpose handlers
     */
    Default(4),

    /**
     * Called after "Default" handlers and before "Late" handlers. Is called
     * even when event has been canceled.
     */
    Late_IgnoreCancelled(5),

    /**
     * Called after "Default" handlers. 
     */
    Late(6),

    Latest_IgnoreCancelled(7),
    Latest(8),
    Monitor(9);
    private int index;
    Order(int index) {
        this.index = index;
    }
    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }
}