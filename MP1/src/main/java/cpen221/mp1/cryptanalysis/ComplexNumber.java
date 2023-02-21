package cpen221.mp1.cryptanalysis;

public class ComplexNumber {

    // Real and imaginary parts of the complex number
    private double real;
    private double imaginary;

    /**
     * Create a complex number from given inputs
     * @param real is the real part of the complex number
     * @param imaginary is the imaginary (i) part of the complex number
     */
    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    /**
     * Create a complex number with real and imaginary values as 0 (0+0*i)
     */
    public ComplexNumber() {
        real = 0;
        imaginary = 0;
    }

    /**
     * Create a complex number by copying another complex number
     * @param seed is another complex number (a+b*i) that we're copying
     */
    public ComplexNumber(ComplexNumber seed) {

        real = seed.re();
        imaginary = seed.im();
    }

    /**
     * Add a complex number another complex number
     * @param other is the complex number that we're adding to the first one
     */
    public void add(ComplexNumber other) {
        double otherReal = other.re();
        double otherImaginary = other.im();

        // Result = (real + imaginary) + (otherReal + otherImaginary) * i

        real = real + otherReal;
        imaginary = imaginary + otherImaginary;
    }

    /**
     * Multiply a complex number with another complex number
     * @param other is the complex number that we're multiplying the first one with
     */
    public void multiply(ComplexNumber other) {
        double otherReal = other.re();
        double otherImaginary = other.im();

        // Result = (real * otherReal - imag * otherImag) + (real * otherImag + imag * otherReal ) * i

        double newReal;
        double newImaginary;

        newReal = real * otherReal - imaginary * otherImaginary;
        newImaginary = real * otherImaginary + imaginary * otherReal;

        real = newReal;
        imaginary = newImaginary;
    }

    /**
     * Turn a complex number into string with the format "a+bi" or "a-bi"
     */
    public String toString() {

        String realString = String.valueOf(real);
        String imaginaryString = String.valueOf(imaginary);
        String complexString = " ";

        if (imaginary >= 0) {
            complexString = complexString.concat(realString);
            complexString = complexString.concat("+");
            complexString = complexString.concat(imaginaryString);
            complexString = complexString.concat("*i");
            complexString = complexString.replace(" ", "");
            return complexString;
        }

        else
        {
            complexString = complexString.concat(realString);
            complexString = complexString.concat(imaginaryString);
            complexString = complexString.concat("*i");
            complexString = complexString.replace(" ", "");
            return complexString;
        }
    }

    /**
     * Return the real part of the complex number
     */
    public double re() { return real; }

    /**
     * Return the imaginary part of the complex number
     */
    public double im() { return imaginary; }

    /**
     * Check if 2 complex numbers are equal to each other
     * @param other is the complex number that we're comparing the first one to
     */
    @Override
    public boolean equals(Object other) {

        if (!(other instanceof ComplexNumber)) {
            return false;
        }

        ComplexNumber otherComplex = (ComplexNumber) other;

        if (otherComplex.re() == real && otherComplex.im() == imaginary) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Check if 2 complex numbers are equal to each other by comparing their hashcodes
     * @return the rounded version of the sum of their sum and products of their real and imaginary parts.
     */
    @Override
    public int hashCode() {
        return (int) Math.round(real*imaginary+real+imaginary);
    }
}
