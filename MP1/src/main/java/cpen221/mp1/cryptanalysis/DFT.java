package cpen221.mp1.cryptanalysis;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.lang.Math.*;

public abstract class DFT {

    /**
     * Computes the DFT of a sequence of complex numbers. A sequence of complex numbers is
     * transformed into another sequence of complex numbers.
     * @param inSignal is an array of complex numbers
     * @return the DFT of given inputs
     */
    public static ComplexNumber[] dft(ComplexNumber[] inSignal) {

        int i, j;
        double pi = Math.PI;
        double N = (double) inSignal.length;

        ComplexNumber[] X = new ComplexNumber[inSignal.length];

        ComplexNumber[] previousInSignal = new ComplexNumber[inSignal.length];

        for (i = 0 ; i < X.length ; i++) {
            X[i] = new ComplexNumber();
        }

        for (i = 0; i < inSignal.length; i++) {
            ComplexNumber a = new ComplexNumber(inSignal[i]);
            previousInSignal[i] = a;
        }

        for (i = 0; i < X.length; i++) {

            for (int k = 0; k < inSignal.length; k++) {
                ComplexNumber a = new ComplexNumber(previousInSignal[k]);
                inSignal[k] = a;
            }

            for (j = 0; j < inSignal.length; j++) {
                ComplexNumber cossin = new ComplexNumber( 1.0*cos(2 * pi / N * i * j ), -1.0*sin(2.0 * pi / N * i *j));
                inSignal[j].multiply(cossin);
                X[i].add(inSignal[j]);
            }
        }

        return X;
    }

    /**
     * Computes the DFT of a sequence of integers. A sequence of integers is transformed into
     * another sequence of complex numbers.
     * @param inSignal is an array of integers
     * @return the DFT of given inputs
     */
    public static ComplexNumber[] dft(int[] inSignal) {
        int i, j;
        double pi = Math.PI;
        double N = (double) inSignal.length;

        ComplexNumber[] X = new ComplexNumber[inSignal.length];
        ComplexNumber[] inSignalToComplex = new ComplexNumber[inSignal.length];

        for (i = 0 ; i < X.length ; i++) {
            X[i] = new ComplexNumber();
            inSignalToComplex[i] = new ComplexNumber(inSignal[i], 0);
        }


        ComplexNumber[] previousInSignalToComplex = new ComplexNumber[inSignal.length];

        for (i = 0; i < inSignal.length; i++) {
            ComplexNumber a = new ComplexNumber(inSignalToComplex[i]);
            previousInSignalToComplex[i] = a;
        }

        for (i = 0; i < X.length; i++) {

            for (int k = 0; k < inSignal.length; k++) {
                ComplexNumber a = new ComplexNumber(previousInSignalToComplex[k]);
                inSignalToComplex[k] = a;
            }
            for (j = 0; j < inSignal.length; j++) {
                ComplexNumber cossin = new ComplexNumber( 1.0*cos(2 * pi / N * i * j ), -1.0*sin(2.0 * pi / N * i *j));
                inSignalToComplex[j].multiply(cossin);
                X[i].add(inSignalToComplex[j]);
            }
        }
        return X;
    }
}
