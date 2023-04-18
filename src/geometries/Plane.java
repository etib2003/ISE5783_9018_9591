package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Represents a plane in 3D space, which is a flat surface that extends
 * infinitely in all directions.
 */
public class Plane implements Geometry {

	/** A point on the plane */
	private final Point p0;

	/** The normal vector to the plane */
	private final Vector normal;

	/**
	 * Constructs a new plane from three non-collinear points on the plane.
	 *
	 * @param p1 the first point on the plane
	 * @param p2 the second point on the plane
	 * @param p3 the third point on the plane
	 */
	public Plane(Point p1, Point p2, Point p3) {
		p0 = p1;
		Vector u = p2.subtract(p0);
		Vector v = p3.subtract(p0);
		Vector n = u.crossProduct(v);
		normal = n.normalize();
	}

	/**
	 * Constructs a new plane from a point on the plane and a normal vector to the
	 * plane.
	 *
	 * @param p the point on the plane
	 * @param n the normal vector to the plane
	 */
	Plane(Point p, Vector n) {
		p0 = p;
		normal = n.normalize();
	}

	/**
	 * Returns the normal vector to the plane.
	 *
	 * @return the normal vector to the plane
	 */
	public Vector getNormal() {
		return normal;
	}

	/**
	 * Returns the normal vector to the surface of the plane at the specified point.
	 *
	 * @param p the point on the surface of the plane
	 * @return the normal vector to the surface of the plane at the specified point
	 */
	@Override
	public Vector getNormal(Point p) {
		return getNormal();
	}
}
