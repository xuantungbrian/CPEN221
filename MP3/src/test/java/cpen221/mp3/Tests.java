package cpen221.mp3;

import cpen221.mp3.fsftbuffer.Bufferable;
import cpen221.mp3.fsftbuffer.FSFTBuffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import static org.junit.Assert.fail;


public class Tests {

    private static FSFTBuffer test1;
    private static FSFTBuffer test2;

    @BeforeAll
    public static void setupTests() {
        test1 = new FSFTBuffer();
    }

    @Test
    /**
     * Test constructor, test object is removed after long time,
     * test method put() when capacity is not full, test exception of get() is thrown
     */
    public void testRemoveAfterLongTime() throws InterruptedException {
        test2 = new FSFTBuffer(3, 60);
        boolean result;
        Numbers testnumber1 = new Numbers(123);
        Numbers testnumber2 = new Numbers(321);
        Numbers testnumber3;
        result=test2.put(testnumber1);
        Assertions.assertEquals(0,Boolean.compare(result,true));
        Thread.sleep(10000);
        result=test2.put(testnumber2);
        Assertions.assertEquals(0,Boolean.compare(result,true));
        Thread.sleep(80000);

        try {
            test2.get(testnumber1.id());
            fail("No exception thrown");
        }
        catch (NoSuchElementException e){
            //OK
        }

        try {
            test2.get(testnumber2.id());
            fail("No exception thrown");
        }
        catch (NoSuchElementException e){
            //OK
        }
    }

    @Test
    /**
     * Test get()
     */
    public void testGet() throws InterruptedException {
        test2 = new FSFTBuffer(3, 60);
        Numbers testnumber1 = new Numbers(123);
        Bufferable testnumber2;
        test2.put(testnumber1);
        testnumber2=test2.get(testnumber1.id());
        Assertions.assertEquals(testnumber2.id(),testnumber1.id());
    }

    @Test
    /**
     * Test put() when capacity is full
     */
    public void testPutFullCapacity1() throws InterruptedException {
        test2 = new FSFTBuffer(3, 60);
        Numbers testnumber1 = new Numbers(123);
        Numbers testnumber2 = new Numbers(321);
        Numbers testnumber3 = new Numbers(1234);
        Numbers testnumber4 = new Numbers(4321);
        test2.put(testnumber1);
        Thread.sleep(10000);
        test2.put(testnumber2);
        Thread.sleep(10000);
        test2.put(testnumber3);
        Thread.sleep(10000);
        test2.put(testnumber4);
        test2.get(testnumber2.id());
        test2.get(testnumber3.id());
        test2.get(testnumber4.id());
    }

    @Test
    /**
     * Test put() when capacity is full, with get() changing last use time of the object
     */
    public void testPutFullCapacity2() throws InterruptedException {
        test2 = new FSFTBuffer(3, 60);
        Numbers testnumber1 = new Numbers(123);
        Numbers testnumber2 = new Numbers(321);
        Numbers testnumber3 = new Numbers(1234);
        Numbers testnumber4 = new Numbers(4321);
        test2.put(testnumber1);
        Thread.sleep(10000);
        test2.put(testnumber2);
        Thread.sleep(10000);
        test2.put(testnumber3);
        Thread.sleep(10000);
        test2.get(testnumber1.id());
        test2.put(testnumber4);
        test2.get(testnumber1.id());
        test2.get(testnumber3.id());
        test2.get(testnumber4.id());
    }

    @Test
    /**
     * Test touch()
     */
    public void testTouch() throws InterruptedException {
        test2 = new FSFTBuffer(3, 60);
        Numbers testnumber1 = new Numbers(123);
        test2.put(testnumber1);
        test2.get(testnumber1.id());
        Thread.sleep(20000);
        test2.touch(testnumber1.id());
        Thread.sleep(50000);
        test2.get(testnumber1.id());
    }

    @Test
    /**
     * Test update()
     */
    public void testupdate() throws InterruptedException {
        test2 = new FSFTBuffer(3, 60);
        Numbers testnumber1 = new Numbers(123);
        Numbers testnumber2 = new Numbers(123);
        test2.put(testnumber1);
        test2.update(testnumber2);
        Thread.sleep(55000);
        test2.get(testnumber2.id());
    }

}

