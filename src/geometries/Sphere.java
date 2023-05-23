package geometries;

import static primitives.Util.*;
import java.util.List;

import primitives.*;

/**
 * Represents a sphere in 3D space.
 * 
 * The Sphere class extends the RadialGeometry class and represents a sphere
 * with a center point and a radius. It provides methods for retrieving the
 * center point, calculating the normal vector at a given point on the sphere,
 * and finding intersections of a ray with the sphere.
 * 
 * @author Eti and Chavi
 */
public class Sphere extends RadialGeometry {

	/** The center point of the sphere */
	private final Point center;

	/**
	 * Constructs a new sphere with the specified center point and radius.
	 *
	 * @param center the center point of the sphere
	 * @param radius the radius of the sphere
	 */
	public Sphere(Point center, double radius) {
		super(radius);
		this.center = center;
	}

	/**
	 * Returns the center point of the sphere.
	 *
	 * @return the center point of the sphere
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * Returns the normal vector at a given point on the sphere's surface.
	 * 
	 * @param point the point on the sphere's surface
	 * @return the normalized normal vector at the given point
	 */
	@Override
	public Vector getNormal(Point point) {
		return (point.subtract(center)).normalize();
	}

	/**
	 * Finds the intersections of a given ray with the sphere.
	 * 
	 * @param ray the ray to intersect with the sphere
	 * @return a list of intersection points, or null if there are no intersections
	 */
	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		Point p0 = ray.getP0();

		// If the beginning point of the ray is on the sphere center, return the point
		// on the sphere's radius
		if (p0.equals(center))
			return List.of(new GeoPoint(this, ray.getPoint(radius)));

		Vector u = center.subtract(p0);
		double tM = alignZero(ray.getDir().dotProduct(u));
		double d2 = u.lengthSquared() - tM * tM; // squared d
		double delta2 = alignZero(radius2 - d2);

		// If there are no intersections, return null
		if (delta2 <= 0)
			return null;

		double tH = Math.sqrt(delta2);

		double t2 = alignZero(tM + tH);
		if (t2 <= 0)
			return null;

		double t1 = alignZero(tM - tH);
		return t1 <= 0 ? List.of(new GeoPoint(this, ray.getPoint(t2))) // P2 only
				: List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2))); // P1 & P2
	}
}
