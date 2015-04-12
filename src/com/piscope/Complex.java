package com.piscope;

public class Complex {
	private final double re;   // the real part
	private final double im;   // the imaginary part

	// create a new object with the given real and imaginary parts
	public Complex(double real, double imag) {
		re = real;
		im = imag;
	}
	
	// return a string representation of the invoking Complex object
    public String toString() {
        if (im == 0) return re + "";
        if (re == 0) return im + "i";
        if (im <  0) return re + " - " + (-im) + "i";
        return re + " + " + im + "i";
    }
    
    // return abs/modulus/magnitude and angle/phase/argument
    public double abs()   { return Math.hypot(re, im); }  // Math.sqrt(re*re + im*im)
    public double phase() { return Math.atan2(im, re); }  // between -pi and pi
}