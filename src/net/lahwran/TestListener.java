/**
 * 
 */
package net.lahwran;

import net.lahwran.fevents.Listener;
import net.lahwran.fevents.SingletonTestEvent;

/**
 * TestEvent test listener
 * @author lahwran
 *
 */
public class TestListener implements Listener<SingletonTestEvent> {

    public void onEvent(SingletonTestEvent event) {
        System.out.println("TestEvent called! message: "+event.getMessage());
        event.setMessage("Hello thar!");
    }
}
