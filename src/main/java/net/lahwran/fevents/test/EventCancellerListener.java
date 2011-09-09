/**
 * 
 */
package net.lahwran.fevents.test;

import net.lahwran.fevents.Listener;

/**
 * @author lahwran
 *
 */
public class EventCancellerListener implements Listener<TestCancellableEvent> {

    /* (non-Javadoc)
     * @see net.lahwran.fevents.Listener#onEvent(net.lahwran.fevents.Event)
     */
    @Override
    public void onEvent(TestCancellableEvent event) {
        event.setCancelled(true);
    }

}
