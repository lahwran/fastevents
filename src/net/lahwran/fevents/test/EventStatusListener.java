/**
 * 
 */
package net.lahwran.fevents.test;

import net.lahwran.fevents.CancellableEvent;
import net.lahwran.fevents.Listener;

/**
 * @author lahwran
 *
 */
public class EventStatusListener implements Listener<TestCancellableEvent> {

    String message;
    EventStatusListener(String message){
        this.message = message;
    }
    @Override
    public void onEvent(TestCancellableEvent event) {
        TestMain.log(message+" - event is "+(event.isCancelled() ? "canceled" : "not canceled"));
    }
    
}
