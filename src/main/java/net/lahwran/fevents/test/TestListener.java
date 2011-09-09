/**
 * 
 */
package net.lahwran.fevents.test;

import net.lahwran.fevents.Listener;

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
