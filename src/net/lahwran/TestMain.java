/**
 * 
 */
package net.lahwran;

import net.lahwran.fevents.EventManager;
import net.lahwran.fevents.Order;
import net.lahwran.fevents.SingletonTestEvent;
import net.lahwran.fevents.test.SimpleTestEvent;

/**
 * @author lahwran
 *
 */
public class TestMain {

    /**
     * Test main - call to run tests (including 10-billion-call speed test)<br>
     * <br>
     * Test results I had:<br>
     * <pre>
        TestEvent called! message: Test event
        Back in main, te message: Hello thar!
        TestEvent2 called! 5
        Back in main, te2 number: 6
        Beginning speed test, 10000000000 iterations: system clock 1315504976876ms
        Predicted time per event: 5.7195E-5ms - Predicted process time: 571.95 seconds
        End 10000000000 iterations in 584.069 seconds: 5.84069E-5ms per event
     * </pre>
     * This was on a pentium 4 w/hyperthreading 3ghz.
     * @param args cli arguments
     */
    public static void main(String[] args) {
        TestListener testlistener = new TestListener();
        TestListener2 testlistener2 = new TestListener2();
        SingletonTestEvent.handlers.register(testlistener, Order.Default);
        SimpleTestEvent.handlers.register(testlistener2, Order.Default);
        
        EventManager eventmanager = new EventManager();
        SingletonTestEvent te = SingletonTestEvent.update("Test event");
        eventmanager.callEvent(te);
        System.out.println("Back in main, te message: "+te.getMessage());

        SimpleTestEvent te2 = new SimpleTestEvent(5);
        eventmanager.callEvent(te2);
        System.out.println("Back in main, te2 number: "+te2.getNumber());
        
        SpeedListener speedy = new SpeedListener();
        SingletonTestEvent.handlers.register(speedy, Order.Default);
        SingletonTestEvent.handlers.unregister(testlistener);
        
        long starttime = System.currentTimeMillis();
        long itercount = 10000000000L;
        double predicted = 5.7195E-5;
        System.out.println("Beginning speed test, "+itercount+" iterations: system clock "+starttime+"ms");
        System.out.println("Predicted time per event: "+predicted+"ms - Predicted process time: "+((itercount * predicted) / 1000.0)+" seconds");
        for (long i=0; i<itercount; i++) {
            eventmanager.callEvent(te);
        }
        long endtime = System.currentTimeMillis();
        long length = endtime - starttime;
        double perevent = (double)length / (double)itercount;
        System.out.println("End "+itercount+" iterations in "+(length / 1000.0)+" seconds: "+perevent+"ms per event");
        
    }

}
