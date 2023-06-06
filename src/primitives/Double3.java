package primitives;

import static primitives.Util.isZero;

/**
 * Represents a triple of double values. This class serves as a foundation for
 * other primitive classes based on three numbers.
 * 
 * @author Eti and Chavi
 */
public class Double3 {
	/** First number */
	final double d1;
	/** Second number */
	final double d2;
	/** Third number */
	final double d3;

	/** Zero triad (0,0,0) */
	public static final Double3 ZERO = new Double3(0, 0, 0);

	/** One's triad (1,1,1) */
	public static final Double3 ONE = new Double3(1, 1, 1);

	/**
	 * Constructor to initialize Double3 based object with its three number values
	 * 
	 * @param d1 the value of the first number
	 * @param d2 the value of the second number
	 * @param d3 the value of the third number
	 */
	public Double3(double d1, double d2, double d3) {
		this.d1 = d1;
		this.d2 = d2;
		this.d3 = d3;
	}

	/**
	 * Constructor to initialize Double3 based object the same number values
	 * 
	 * @param value number value for all 3 numbers
	 */
	public Double3(double value) {
		this.d1 = value;
		this.d2 = value;
		this.d3 = value;
	}

	/**
	 * Adds another Double3 to this Double3 and returns the result as a new Double3
	 * object. Each corresponding pair of numbers is added together.
	 * 
	 * @param rhs the Double3 to be added
	 * @return the result of the addition as a new Double3 object
	 */
	public Double3 add(Double3 rhs) {
		return new Double3(d1 + rhs.d1, d2 + rhs.d2, d3 + rhs.d3);
	}

	/**
	 * Subtracts another Double3 from this Double3 and returns the result as a new
	 * Double3 object. Each corresponding pair of numbers is subtracted.
	 * 
	 * @param rhs the Double3 to be subtracted
	 * @return the result of the subtraction as a new Double3 object
	 */
	public Double3 subtract(Double3 rhs) {
		return new Double3(d1 - rhs.d1, d2 - rhs.d2, d3 - rhs.d3);
	}

	/**
	 * Scales (multiplies) this Double3 by a scalar value and returns the result as
	 * a new Double3 object. Each number is multiplied by the scalar value.
	 * 
	 * @param rhs the scalar value to scale by
	 * @return the result of the scaling as a new Double3 object
	 */
	public Double3 scale(double rhs) {
		return new Double3(d1 * rhs, d2 * rhs, d3 * rhs);
	}

	/**
	 * Reduces (divides) this Double3 by a divisor value and returns the result as a
	 * new Double3 object. Each number is divided by the divisor value.
	 * 
	 * @param rhs the divisor value to reduce by
	 * @return the result of the reduction as a new Double3 object
	 */
	public Double3 reduce(double rhs) {
		return new Double3(d1 / rhs, d2 / rhs, d3 / rhs);
	}

	/**
	 * Multiplies another Double3 with this Double3 and returns the result as a new
	 * Double3 object. Each corresponding pair of numbers is multiplied.
	 * 
	 * @param rhs the Double3 to be multiplied
	 * @return the result of the multiplication as a new Double3 object
	 */
	public Double3 product(Double3 rhs) {
		return new Double3(d1 * rhs.d1, d2 * rhs.d2, d3 * rhs.d3);
	}

	/**
	 * Checks if all the numbers in this Double3 are lower than a given test number.
	 * 
	 * @param k the test number
	 * @return {@code true} if all the numbers are less than k, {@code false}
	 *         otherwise
	 */
	public boolean lowerThan(double k) {
		return d1 < k && d2 < k && d3 < k;
	}

	/**
	 * Checks if all the numbers in this Double3 are lower than the corresponding
	 * numbers in another Double3.
	 * 
	 * @param other the other Double3 to compare with
	 * @return {@code true} if all the numbers in this Double3 are less than the
	 *         appropriate numbers in the other Double3, {@code false} otherwise
	 */
	public boolean lowerThan(Double3 other) {
		return d1 < other.d1 && d2 < other.d2 && d3 < other.d3;
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 * 
	 * @param obj the reference object with which to compare
	 * @return {@code true} if this Double3 is the same as the obj argument;
	 *         {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Double3 other)
			return isZero(d1 - other.d1) && isZero(d2 - other.d2) && isZero(d3 - other.d3);
		return false;
	}

	/**
	 * Returns a hash code value for this Double3.
	 * 
	 * @return a hash code value for this Double3
	 */
	@Override
	public int hashCode() {
		return (int) Math.round(d1 + d2 + d3);
	}

	/**
	 * Returns a string representation of this Double3. The string representation
	 * consists of the three numbers enclosed in parentheses, separated by commas.
	 * 
	 * @return a string representation of this Double3
	 */
	@Override
	public String toString() {
		return "(" + d1 + "," + d2 + "," + d3 + ")";
	}
}
