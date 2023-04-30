package geometries;

import static primitives.Util.*;
import java.util.List;
import primitives.*;

/**
 * Represents a sphere in 3D space.
 */
public class Sphere extends RadialGeometry {

	/** The center point of the sphere */
	private final Point center;

	/**
	 * Constructs a new sphere with the specified center point and radius.
	 *
	 * @param p      the center point of the sphere
	 * @param radius the radius of the sphere
	 */
	public Sphere(Point p, double radius) {
		super(radius);
		center = p;
	}

	/**
	 * Returns the center point of the sphere.
	 *
	 * @return the center point of the sphere
	 */
	public Point getCenter() {
		return center;
	}

	@Override
	public Vector getNormal(Point p) {
		return (p.subtract(getCenter())).normalize();
	}

	/**
	 * Finds the intersections of a given ray with a sphere. 
	 * @param ray the ray to intersect with the sphere
	 * 
	 * @return a list of intersection points, or null if there are no intersections
	 */
	@Override
	public List<Point> findIntersections(Ray ray) {
		// If the beginning point of the ray is on the sphere center, return the point on the sphere's radius
		if (ray.getP0().equals(center))
			return List.of(ray.getPoint(radius));

		Vector u = center.subtract(ray.getP0());
		double tM = alignZero(ray.getDir().dotProduct(u));
		double d = alignZero(Math.sqrt(u.lengthSquared() - tM * tM));
		double tH = alignZero(Math.sqrt(radius * radius - d * d));
		double t1 = alignZero(tM + tH);
		double t2 = alignZero(tM - tH);

		// If there are no intersections, return null
		if (d > radius)
			return null;
		
		if (t1 <= 0 && t2 <= 0)
			return null;

		// If there are two intersections, return them as a list
		if (t1 > 0 && t2 > 0)
			return List.of(ray.getPoint(t1), ray.getPoint(t2));

		// If there is one intersection, return it as a list
		if (t1 > 0)
			return List.of(ray.getPoint(t1));
		else
			return List.of(ray.getPoint(t2));
	}

}
