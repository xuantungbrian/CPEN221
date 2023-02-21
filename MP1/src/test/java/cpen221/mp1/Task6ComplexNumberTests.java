package cpen221.mp1;

import cpen221.mp1.cryptanalysis.DFT;
import cpen221.mp1.cryptanalysis.ComplexNumber;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Task6ComplexNumberTests {
    @Test
    public void testRealImaginaryReturns () {
        ComplexNumber z = new ComplexNumber(3, 2);
        Assertions.assertEquals(z.re(), 3);
        Assertions.assertEquals(z.im(), 2);

        Assertions.assertEquals(z, new ComplexNumber(3,2));
    }

    @Test
    public void testZero () {
        ComplexNumber zero = new ComplexNumber();
        Assertions.assertEquals(zero.re(), 0);
        Assertions.assertEquals(zero.im(), 0);

        Assertions.assertEquals(zero, new ComplexNumber(0,0));
    }

    @Test
    public void testSeed () {
        ComplexNumber seed = new ComplexNumber(2, 3);
        ComplexNumber newNumber = new ComplexNumber(seed);
        Assertions.assertEquals(newNumber.re(), 2);
        Assertions.assertEquals(newNumber.im(), 3);

        Assertions.assertEquals(newNumber, new ComplexNumber(2,3));
    }

    @Test
    public void testAdd () {
        ComplexNumber y = new ComplexNumber(2, 3);
        ComplexNumber z = new ComplexNumber(4, -1);

        ComplexNumber addedResult = new ComplexNumber(6, 2);

        y.add(z);
        Assertions.assertEquals(y, addedResult);
    }

    @Test
    public void testMultiply () {
        ComplexNumber y = new ComplexNumber(2, 3);
        ComplexNumber z = new ComplexNumber(4, -1);

        ComplexNumber multipliedResult = new ComplexNumber(11, 10);

        y.multiply(z);

        Assertions.assertEquals(y, multipliedResult);
    }

    @Test
    public void testString () {
        ComplexNumber a = new ComplexNumber(2.2, 3);
        ComplexNumber b = new ComplexNumber(2, -3.5);
        ComplexNumber c = new ComplexNumber(-2.5, 3.1);
        ComplexNumber d = new ComplexNumber(-2.1, -3);


        Assertions.assertEquals(a.toString(), "2.2+3.0*i");
        Assertions.assertEquals(b.toString(), "2.0-3.5*i");
        Assertions.assertEquals(c.toString(), "-2.5+3.1*i");
        Assertions.assertEquals(d.toString(), "-2.1-3.0*i");
    }

    @Test
    public void testEquals () {
        ComplexNumber a = new ComplexNumber (2,2);
        Object b = new ComplexNumber (2, 2);
        Object c = new ComplexNumber (2, 0);
        ComplexNumber d = new ComplexNumber (1,1);
        Object e = new Object();

        Assertions.assertEquals(a.equals(b), true);
        Assertions.assertEquals(a.equals(c), false);
        Assertions.assertEquals(a.equals(d), false);
        Assertions.assertEquals(a.equals(e), false);
    }

    @Test
    public void testHashcode () {
        ComplexNumber a = new ComplexNumber (3,3);
        Object b = new ComplexNumber (3, 3);
        Object c = new ComplexNumber (7, -2);
        ComplexNumber d = new ComplexNumber (3,3);
        ComplexNumber e = new ComplexNumber (1,1);

        Assertions.assertEquals(a.hashCode(), b.hashCode());
        Assertions.assertNotEquals(a.hashCode(), c.hashCode());
        Assertions.assertEquals(a.hashCode(), d.hashCode());
        Assertions.assertNotEquals(a.hashCode(), e.hashCode());
    }
}
