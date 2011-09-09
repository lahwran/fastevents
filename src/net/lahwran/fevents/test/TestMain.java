/**
 * 
 */
package net.lahwran.fevents.test;

import net.lahwran.fevents.EventManager;
import net.lahwran.fevents.Listener;
import net.lahwran.fevents.Order;

/**
 * @author lahwran
 *
 */
public class TestMain {

    static void log(String message) {
        System.out.println("["+System.currentTimeMillis()+"] "+message);
    }

    static void exAssert(boolean value, String assertion) throws TestException {
        log("Assert: "+assertion+(value ? "" : " - ASSERTION FAILED"));
        if (!value)
            throw new TestException("Assertion "+assertion+" FAILED");
    }
    
    public static void tests_simple(EventManager eventmanager) throws TestException {
        log("---- entering tests_simple");
        SingletonTestEvent testevent = SingletonTestEvent.update("Test event");
        exAssert(testevent.getMessage().equals("Test event"), "testevent.getMessage().equals(\"Test event\")");
        log("Calling event with no listeners");
        eventmanager.callEvent(testevent);
        log("Call complete");
        exAssert(testevent.getMessage().equals("Test event"), "testevent.getMessage().equals(\"Test event\")");
        log("Adding simple event listener");

        Listener<SingletonTestEvent> testlistener = new Listener<SingletonTestEvent>() {
            public void onEvent(SingletonTestEvent event) {
                log("Simple event listener called, event message: "+event.getMessage());
                try {
                    exAssert(event.getMessage().equals("Test event"), "event.getMessage().equals(\"Test event\")");
                } catch (TestException t) {
                    throw new RuntimeException(t);
                }
                event.setMessage("Event listener called");
            }
        };

        SingletonTestEvent.handlers.register(testlistener, Order.Default);

        try {
            log("calling event with simple event listener");
            eventmanager.callEvent(testevent);
            log("call complete");
            exAssert(testevent.getMessage().equals("Event listener called"), "testevent.getMessage().equals(\"Event listener called\")");
        } finally {
            log("unregistering listener");
            SingletonTestEvent.handlers.unregister(testlistener, Order.Default);
            log("---- exiting tests_simple");
        }
    }

    public static void test_speed(EventManager eventmanager) throws TestException {
        log("---- entering test_speed");
        SingletonTestEvent testevent = SingletonTestEvent.update("speed test");

        Listener<SingletonTestEvent> speedy = new Listener<SingletonTestEvent>() {
            public void onEvent(SingletonTestEvent event) {}
        };

        SingletonTestEvent.handlers.register(speedy, Order.Default);
        
        long starttime = System.currentTimeMillis();
        long itercount = 100000000L;
        double predicted = 5.7195E-5;
        log("Beginning speed test, "+itercount+" iterations");
        log("Predicted time per event: "+predicted+"ms - Predicted process time: "+((itercount * predicted) / 1000.0)+" seconds");
        for (long i=0; i<itercount; i++) {
            eventmanager.callEvent(testevent);
        }
        long endtime = System.currentTimeMillis();
        long length = endtime - starttime;
        double perevent = (double)length / (double)itercount;
        log("End "+itercount+" iterations in "+(length / 1000.0)+" seconds: "+perevent+"ms per event");
        log("Event calls per second: "+(itercount / (length / 1000.0)));
        log("---- exiting test_speed");
    }

    public static void tests_order(EventManager eventmanager) throws TestException {
        log("---- entering test_order");
        SimpleTestEvent testevent = new SimpleTestEvent(0);
        
        Listener<SimpleTestEvent> first = new Listener<SimpleTestEvent>() {
            public void onEvent(SimpleTestEvent event) {
                log("First listener called");
                try {
                    exAssert(event.getNumber() == 0, "event.getNumber() == 0");
                } catch (TestException e) {
                    throw new RuntimeException(e);
                }
                event.setNumber(1);
                log("First listener exiting");
            }
        };
        
        Listener<SimpleTestEvent> second = new Listener<SimpleTestEvent>() {
            public void onEvent(SimpleTestEvent event) {
                log("Second listener called");
                try {
                    exAssert(event.getNumber() == 1, "event.getNumber() == 1");
                } catch (TestException e) {
                    throw new RuntimeException(e);
                }
                event.setNumber(2);
                log("Second listener exiting");
            }
        };
        
        Listener<SimpleTestEvent> third = new Listener<SimpleTestEvent>() {
            public void onEvent(SimpleTestEvent event) {
                log("Third listener called");
                try {
                    exAssert(event.getNumber() == 2, "event.getNumber() == 2");
                } catch (TestException e) {
                    throw new RuntimeException(e);
                }
                event.setNumber(3);
                log("Third listener exiting, throwing error");
                throw new RuntimeException("Test exception");
            }
        };
        log("registering handlers");
        SimpleTestEvent.handlers.register(first, Order.Earlist);
        SimpleTestEvent.handlers.register(second, Order.Default);
        SimpleTestEvent.handlers.register(third, Order.Latest);
        try {
            log("calling event");
            eventmanager.callEvent(testevent);
            log("call complete");
            exAssert(testevent.getNumber() == 3, "testevent.getNumber() == 3");
        } finally {
            log("unregistering handlers");
            SimpleTestEvent.handlers.unregister(first, Order.Earlist);
            SimpleTestEvent.handlers.unregister(second, Order.Default);
            SimpleTestEvent.handlers.unregister(third, Order.Latest);
            log("---- exiting test_order");
        }
    }

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
        EventManager eventmanager = new EventManager();
        try {
            tests_simple(eventmanager);
        } catch (TestException e) {
            e.printStackTrace();
        }
        try {
            tests_order(eventmanager);
        } catch (TestException e) {
            e.printStackTrace();
        }
        try {
            test_speed(eventmanager);
        } catch (TestException e) {
            e.printStackTrace();
        }
        /*
        
        
        TestListener testlistener = new TestListener();
        TestListener2 testlistener2 = new TestListener2();
        SingletonTestEvent.handlers.register(testlistener, Order.Default);
        SimpleTestEvent.handlers.register(testlistener2, Order.Default);
        
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
        */
    }

}
