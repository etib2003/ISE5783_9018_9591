package geometries;

import java.util.List;
import static primitives.Util.*;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Represents a plane in 3D space, which is a flat surface that extends
 * infinitely in all directions.
 *
 * @author Eti and Chavi
 */
public class Plane extends Geometry {

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

	/**
	 * Computes the intersection points of a given {@link Ray} with the plane. If
	 * the ray doesn't intersect the plane, the method returns null.
	 * 
	 * @param ray the ray to intersect with the plane
	 * @return a list of intersection points if the ray intersects the plane, null
	 *         otherwise
	 */
	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		// Calculate the dot product of the plane's normal vector with the ray's
		// direction vector
		double nv = normal.dotProduct(ray.getDir());
		if (isZero(nv)) { // if the dot product is zero, the ray is parallel to the plane and doesn't
							// intersect
			return null;
		}
		try {
			// Calculate the parameter t at which the ray intersects the plane
			Vector pSubtractP0 = p0.subtract(ray.getP0());
			double t = alignZero((normal.dotProduct(pSubtractP0)) / nv);
			return t <= 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t)));
		} catch (Exception ex) { // if an exception occurs during the calculation, return null
			return null;
		}
	}

}