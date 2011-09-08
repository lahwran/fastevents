/**
 * 
 */
package net.lahwran;

import net.lahwran.fevents.Listener;
import net.lahwran.fevents.test.SimpleTestEvent;

/**
 * @author lahwran
 *
 */
public class TestListener2 implements Listener<SimpleTestEvent> {

    public void onEvent(SimpleTestEvent event) {
        System.out.println("TestEvent2 called! "+event.getNumber());
        event.setNumber(event.getNumber() + 1);
    }

}
