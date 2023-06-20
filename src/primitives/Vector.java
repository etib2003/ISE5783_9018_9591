package primitives;

/**
 * 
 * The Vector class represents a vector in 3-dimensional space.
 * 
 * @author Eti and Chavi
 */
public class Vector extends Point {

	/**
	 * Constructs a new vector from the given x, y, and z components. Throws an
	 * IllegalArgumentException if the vector is the zero vector.
	 *
	 * @param x the x component of the vector
	 * @param y the y component of the vector
	 * @param z the z component of the vector
	 * @throws IllegalArgumentException if the vector is the zero vector
	 */
	public Vector(double x, double y, double z) {
		super(x, y, z);
		if (xyz.equals(Double3.ZERO))
			throw new IllegalArgumentException("Zero vector");
	}

	/**
	 * Constructs a new vector from the given Double3 point. Throws an
	 * IllegalArgumentException if the vector is the zero vector.
	 *
	 * @param point the Double3 point representing the vector
	 * @throws IllegalArgumentException if the vector is the zero vector
	 */
	Vector(Double3 point) {
		super(point);
		if (xyz.equals(Double3.ZERO))
			throw new IllegalArgumentException("Zero vector");
	}

	/**
	 * Adds this vector to the given vector and returns the result as a new vector.
	 *
	 * @param v the vector to add to this vector
	 * @return the sum of this vector and the given vector as a new vector
	 */
	public Vector add(Vector v) {
		return new Vector(this.xyz.add(v.xyz));
	}

	/**
	 * Subtracts this vector to the given vector and returns the result as a new
	 * vector.
	 *
	 * @param v the vector to subtract to this vector
	 * @return the difference of this vector and the given vector as a new vector
	 */
	public Vector subtract(Vector v) {
		return new Vector(this.xyz.subtract(v.xyz));
	}

	/**
	 * 
	 * Scales the vector by the given scalar and returns a new Vector object with
	 * the result.
	 * 
	 * @param rhs the scalar to scale the vector by.
	 * @return a new Vector object representing the scaled vector.
	 */
	public Vector scale(double rhs) {
		return new Vector(this.xyz.scale(rhs));
	}

	/**
	 * 
	 * Computes the dot product of this vector with another vector.
	 * 
	 * @param v the other vector to compute the dot product with.
	 * @return the dot product of this vector with v.
	 */
	public double dotProduct(Vector v) {
		return ((xyz.d1 * v.xyz.d1) + (xyz.d2 * v.xyz.d2) + (xyz.d3 * v.xyz.d3));
	}

	/**
	 * 
	 * Computes the cross product of this vector with another vector.
	 * 
	 * @param v the other vector to compute the cross product with.
	 * @return a new Vector object representing the cross product of this vector
	 *         with v.
	 */
	public Vector crossProduct(Vector v) {
		return new Vector((xyz.d2 * v.xyz.d3) - (xyz.d3 * v.xyz.d2), (xyz.d3 * v.xyz.d1) - (xyz.d1 * v.xyz.d3),
				(xyz.d1 * v.xyz.d2) - (xyz.d2 * v.xyz.d1));
	}

	/**
	 * 
	 * Computes the squared length of the vector.
	 * 
	 * @return the squared length of the vector.
	 */
	public double lengthSquared() {
		return dotProduct(this);
	}

	/**
	 * 
	 * Computes the length of the vector.
	 * 
	 * @return the length of the vector.
	 */
	public double length() {
		return Math.sqrt(lengthSquared());
	}

	/**
	 * 
	 * Normalizes the vector to have unit length and returns a new Vector object
	 * with the result.
	 * 
	 * @return a new Vector object representing the normalized vector.
	 */
	public Vector normalize() {
		return new Vector(xyz.reduce(length()));
	}
	
	  
}
