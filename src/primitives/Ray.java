package primitives;
import static primitives.Util.*;

/**
 * Represents a ray in 3D space, defined by a starting point and a direction.
 */
public class Ray {

	/**
	 * The starting point of the ray.
	 */
	private final Point p0;

	/**
	 * The normalized direction vector of the ray.
	 */
	private final Vector dir;

	/**
	 * Constructs a new ray with the given starting point and direction.
	 * 
	 * @param p the starting point of the ray
	 * @param v the direction vector of the ray
	 */
	public Ray(Point p, Vector v) {
		p0 = p;
		dir = v.normalize();
	}

	/**
	 * Returns the starting point of the ray.
	 * 
	 * @return the starting point of the ray
	 */
	public Point getP0() {
		return p0;
	}

	/**
	 * Returns the direction vector of the ray.
	 * 
	 * @return the direction vector of the ray
	 */
	public Vector getDir() {
		return dir;
	}

	/**
	 * Checks whether this ray is equal to the specified object. Two rays are equal
	 * if their starting points and direction vectors are equal.
	 * 
	 * @param obj the object to compare this ray with
	 * @return true if the specified object is equal to this ray, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return (obj instanceof Ray other) && (this.p0.equals(other.p0) && this.dir.equals(other.dir));
	}

	/**
	 * Returns a string representation of this ray in the format "Ray : p0={x,y,z},
	 * dir={x,y,z}".
	 * 
	 * @return a string representation of this ray
	 */
	@Override
	public String toString() {
		return "Ray : p0=" + p0 + ", dir=" + dir;
	}
	
	public Point getPoint(double t) {
		try {
			if (isZero(t))
				return p0;			 			
			return p0.add(dir.scale(t));
		} catch (Exception e) {
			return p0;
		}
	}
}