package cpen221.mp1;

import cpen221.mp1.cryptanalysis.DFT;
import cpen221.mp1.cryptanalysis.ComplexNumber;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Task6DFTTests {
    @Test
    public void complexTest1 () {
        ComplexNumber testNumberOne = new ComplexNumber(4, 5);
        ComplexNumber [] inputArray = new ComplexNumber [1];
        inputArray[0] = testNumberOne;

        ComplexNumber [] expectedOutput = new ComplexNumber[1];
        ComplexNumber expectedOne = new ComplexNumber(4,5);
        expectedOutput[0] = expectedOne;

        ComplexNumber [] Output = new ComplexNumber[1];
        Output = DFT.dft(inputArray);

        for (int i = 0; i < Output.length; i++) {
            Assertions.assertEquals(expectedOutput[i].re(), Output[i].re(), 0.0005);
            Assertions.assertEquals(expectedOutput[i].im(), Output[i].im(), 0.0005);
        }
    }

    @Test
    public void complexTest2 () {
        ComplexNumber testNumberOne = new ComplexNumber(2, 2);
        ComplexNumber testNumberTwo = new ComplexNumber(3, -1);
        ComplexNumber [] inputArray = new ComplexNumber [2];
        inputArray[0] = testNumberOne;
        inputArray[1] = testNumberTwo;

        ComplexNumber [] expectedOutput = new ComplexNumber[2];
        ComplexNumber expectedOne = new ComplexNumber(5,1);
        ComplexNumber expectedTwo = new ComplexNumber(-1,3);
        expectedOutput[0] = expectedOne;
        expectedOutput[1] = expectedTwo;

        ComplexNumber [] Output = new ComplexNumber[2];
        Output = DFT.dft(inputArray);

        for (int i = 0; i < Output.length; i++) {
            Assertions.assertEquals(expectedOutput[i].re(), Output[i].re(), 0.0005);
            Assertions.assertEquals(expectedOutput[i].im(), Output[i].im(), 0.0005);
        }

    }

    @Test
    public void complexTest3 () {
        ComplexNumber testNumberOne = new ComplexNumber(2, -1);
        ComplexNumber testNumberTwo = new ComplexNumber(1, 1);
        ComplexNumber testNumberThree = new ComplexNumber(4, 2);
        ComplexNumber [] inputArray = new ComplexNumber [3];
        inputArray[0] = testNumberOne;
        inputArray[1] = testNumberTwo;
        inputArray[2] = testNumberThree;

        ComplexNumber [] expectedOutput = new ComplexNumber[3];
        ComplexNumber expectedOne = new ComplexNumber(7,2);
        ComplexNumber expectedTwo = new ComplexNumber(-1.36602541,0.09807622);
        ComplexNumber expectedThree = new ComplexNumber(0.366025408,-5.09807622);
        expectedOutput[0] = expectedOne;
        expectedOutput[1] = expectedTwo;
        expectedOutput[2] = expectedThree;

        ComplexNumber [] Output = new ComplexNumber[3];
        Output = DFT.dft(inputArray);

        for (int i = 0; i < Output.length; i++) {
            Assertions.assertEquals(expectedOutput[i].re(), Output[i].re(), 0.0005);
            Assertions.assertEquals(expectedOutput[i].im(), Output[i].im(), 0.0005);
        }
    }

    @Test
    public void realTest1 () {
        int [] inputArray = new int[3];
        inputArray[0] = 2;
        inputArray[1] = 4;
        inputArray[2] = 1;

        ComplexNumber [] expectedOutput = new ComplexNumber[3];
        ComplexNumber expectedOne = new ComplexNumber(7, 0);
        ComplexNumber expectedTwo = new ComplexNumber(-0.5,-2.59807622);
        ComplexNumber expectedThree = new ComplexNumber(-0.5,2.59808122);
        expectedOutput[0] = expectedOne;
        expectedOutput[1] = expectedTwo;
        expectedOutput[2] = expectedThree;

        ComplexNumber [] Output = new ComplexNumber[3];
        Output = DFT.dft(inputArray);

        for (int i = 0; i < Output.length; i++) {
            Assertions.assertEquals(expectedOutput[i].re(), Output[i].re(), 0.0005);
            Assertions.assertEquals(expectedOutput[i].im(), Output[i].im(), 0.0005);
        }
    }
}
