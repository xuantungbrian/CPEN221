package cpen221.mp3;

import cpen221.mp3.fsftbuffer.Bufferable;

//Datatype for testing
//Numbers represents integer number of type int
public class Numbers implements Bufferable {
    private String id;
    
    //Rep invariant is
    //  RI(r)= r.id != null 
    //Abstraction function is
    //  AF(r)= an integer of type int that is represents by string variable id
    
    public Numbers(int i) {
        id=Integer.toString(i);
    }

    public String id() {
        return id;
    }
}
