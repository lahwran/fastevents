/**
 * 
 */
package net.lahwran;

import net.lahwran.fevents.Listener;
import net.lahwran.fevents.SingletonTestEvent;

/**
 * Noop listener for speed testing of event caller
 * @author lahwran
 *
 */
public class SpeedListener implements Listener<SingletonTestEvent> {

    public void onEvent(SingletonTestEvent event) {
        //Does nothing because the purpose of this listener is to
        //allow the event-call system's speed to be tested
    }

}
