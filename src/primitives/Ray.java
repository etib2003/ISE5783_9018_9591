package primitives;

import static primitives.Util.*;

import java.util.List;

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

	/**
	 * Refactoring must be performed for the calculation code of a point on a ray: P
	 * = p0 + t∙v. Used wherever required in the implementations of
	 * findIntersections function.
	 *
	 * @param t The distance to be calculated for the ray from its head
	 *
	 * @return The 3D-point on the ray that is at a distance of t from the head of
	 *         the ray
	 */
	public Point getPoint(double t) {
		return isZero(t) ? p0 : p0.add(dir.scale(t));
	}

	/**
	 * Returns the point closest to the start of the ray.
	 *
	 * @param points a collection of points to search from
	 * @return the point closest to the start of the ray
	 */
	public Point findClosestPoint(List<Point> points) {
		if (points.size() == 0)
			return null;
		double close = p0.distance(points.get(0));
		int index = 0;
		int size=points.size();
		for (int i = 1; i < size; i++) {
			if (p0.distance(points.get(i)) < close) {
				close = p0.distance(points.get(i));
				index = i;
			}
		}
		return points.get(index);
	}
}
