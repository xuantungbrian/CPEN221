package cpen221.mp3.fsftbuffer;

import java.lang.reflect.Array;
import java.util.*;

// This class represents a finite space, finite time buffer
// Abstract invariant:
//      This buffer will be defined by a collection with finite space containing objects put in the buffer,
//      a map to map the object to its time first put in, second map to map the object to the latest time
//      it was used.
public class FSFTBuffer<T extends Bufferable> {

    /* the default buffer size is 32 objects */
    public static final int DSIZE = 32;

    /* the default timeout value is 3600s */
    public static final int DTIMEOUT = 3600;

    /* TODO: Implement this datatype */
    private int capacity;
    private int timeout;
    private List<T> object;
    private Map<T,Long> time;
    private Map<T,Long> time_used;

    //Rep invariant is
    //  RI(r)= r.capacity !=null && r.timeout !=null
    //         && r.object.size()<= r.capacity && time exist of an object in r<=timeout
    //
    //Abstraction function is:
    //          AF(r)= a buffer, B, such that
    //          objects in B = r.object
    //          time for objects in B to be removed = r.time + r.timeout
    //          latest time an object in B is used = r.time_used
    /**
     * Create a buffer with a fixed capacity and a timeout value.
     * Objects in the buffer that have not been refreshed within the
     * timeout period are removed from the cache.
     *
     * @param capacity the number of objects the buffer can hold
     * @param timeout  the duration, in seconds, an object should
     *                 be in the buffer before it times out
     */
    public FSFTBuffer(int capacity, int timeout) {
        // TODO: implement this constructor
        this.capacity=capacity;
        this.timeout=timeout;
        object=new ArrayList<T>();
        time=new HashMap<>();
        time_used=new HashMap<>();
    }

    /**
     * Create a buffer with default capacity and timeout values.
     */
    public FSFTBuffer() {
        this(DSIZE, DTIMEOUT);
    }

    /**
     * Add a value to the buffer.
     * If the buffer is full then remove the least recently accessed
     * object to make room for the new object.
     */
    public boolean put(T t) {
        // TODO: implement this method
        boolean result=true;
        Long min=0L;
        for (int i=0; i<object.size(); i++) {
            if ((System.currentTimeMillis()-time.get(object.get(i)))>=timeout*1000) {
                time.remove(object.get(i));
                time_used.remove(object.get(i));
                object.remove(i);
            }
        }

        if (object.size()<capacity) {
            result = object.add(t);
            if (result) {
                time.put(t, System.currentTimeMillis());
                time_used.put(t, System.currentTimeMillis());
            }
        }
        else {
            min = Collections.min(time_used.values());
            for (int i=0; i<time.size(); i++) {
                if (time_used.get(object.get(i))==min) {
                    time.remove(object.get(i));
                    time_used.remove(object.get(i));
                    object.remove(i);
                    break;
                }
            }
            result = object.add(t);
            if (result) {
                time.put(t, System.currentTimeMillis());
                time_used.put(t, System.currentTimeMillis());
            }
        }
        return result;
    }


    /**
     * @param id the identifier of the object to be retrieved
     * @return the object that matches the identifier from the
     * buffer
     */
    public T get(String id) {
        /* TODO: throw exception */
        /* Do not return null. Throw a suitable checked exception when an object
            is not in the cache. You can add the checked exception to the method
            signature. */
        for (int i=0; i<object.size(); i++) {
            if ((System.currentTimeMillis()-time.get(object.get(i)))>=timeout*1000) {
                time.remove(object.get(i));
                time_used.remove(object.get(i));
                object.remove(i);
            }
        }

        for (T object : object) {
            if (object.id().equals(id)) {
                time_used.put(object, System.currentTimeMillis());
                return object;
            }
        }

        throw new NoSuchElementException();
    }

    /**
     * Update the last refresh time for the object with the provided id.
     * This method is used to mark an object as "not stale" so that its
     * timeout is delayed.
     *
     * @param id the identifier of the object to "touch"
     * @return true if successful and false otherwise
     */
    public boolean touch(String id) {
        /* TODO: Implement this method */
        for (int i=0; i<object.size(); i++) {
            if ((System.currentTimeMillis()-time.get(object.get(i)))>=timeout*1000) {
                time.remove(object.get(i));
                time_used.remove(object.get(i));
                object.remove(i);
            }
        }

        for (T object : object) {
            if (object.id().equals(id)) {
                time.put(object, System.currentTimeMillis());
                return true;
            }
        }

        return false;
    }

    /**
     * Update an object in the buffer.
     * This method updates an object and acts like a "touch" to
     * renew the object in the cache.
     *
     * @param t the object to update
     * @return true if successful and false otherwise
     */
    public boolean update(T t) {
        /* TODO: implement this method */
        for (int i=0; i<object.size(); i++) {
            if ((System.currentTimeMillis()-time.get(object.get(i)))>=timeout*1000) {
                time.remove(object.get(i));
                time_used.remove(object.get(i));
                object.remove(i);
            }
        }

        for (int i=0; i<time.size(); i++) {
            if (object.get(i).id().equals(t.id())) {
                time.remove(object.get(i));
                time_used.remove(object.get(i));
                object.remove(i);
                object.add(t);
                time.put(t, System.currentTimeMillis());
                time_used.put(t, System.currentTimeMillis());
                return true;
            }
        }

        return false;
    }
}
