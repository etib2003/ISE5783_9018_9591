package primitives;

import static primitives.Util.*;
import geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * Represents a ray in 3D space, defined by a starting point and a direction.
 * 
 * @author Eti and Chavi
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
	 * 
	 * Returns the 3D-point on the ray that is at a distance of {@code t} from the
	 * starting point of the ray. The point is calculated using the formula: P = p0
	 * + t * v, where p0 is the starting point of the ray and v is the direction
	 * vector of the ray.
	 * 
	 * @param t the distance from the starting point of the ray
	 * @return the 3D-point on the ray at the specified distance
	 */
	public Point getPoint(double t) {
		return isZero(t) ? p0 : p0.add(dir.scale(t));
	}

	/**
	 * Finds the closest intersection point with a list of points.
	 * 
	 * @param points the list of points to find the closest intersection with
	 * @return the closest intersection point, or {@code null} if the list is null
	 *         or empty
	 * 
	 */
	public Point findClosestPoint(List<Point> points) {
		return points == null || points.isEmpty() ? null
				: findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
	}

	/**
	 * 
	 * Finds the closest intersection GeoPoint with a list of GeoPoints.
	 * 
	 * @param points the list of GeoPoints to find the closest intersection with
	 * @return the closest intersection GeoPoint, or {@code null} if the list is
	 *         null or empty
	 */
	public GeoPoint findClosestGeoPoint(List<GeoPoint> points) {
		if (points == null || points.isEmpty())
			return null;
		GeoPoint closest = null;
		double minDistance = Double.MAX_VALUE;
		for (GeoPoint p : points) {
			double distance = p.point.distance(p0);
			if (distance < minDistance) {
				closest = p;
				minDistance = distance;
			}
		}
		return closest;
	}

}