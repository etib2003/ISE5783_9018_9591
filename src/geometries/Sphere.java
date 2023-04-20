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

	@Override
	public List<Point> findIntersections(Ray ray) {

		if (ray.getP0().equals(center)) // if the begin of the ray in the center, the point, is on the radius
			return List.of(ray.getPoint(radius));
		Vector u = center.subtract(ray.getP0());
		double tM = alignZero(ray.getDir().dotProduct(u));
		double d = alignZero(Math.sqrt(u.lengthSquared() - tM * tM));
		double tH = alignZero(Math.sqrt(radius * radius - d * d));
		double t1 = alignZero(tM + tH);
		double t2 = alignZero(tM - tH);

		if (d > radius)
			return null; // there are no instructions

		if (t1 <= 0 && t2 <= 0)
			return null;

		if (t1 > 0 && t2 > 0)
			return List.of(ray.getPoint(t1), ray.getPoint(t2));
		if (t1 > 0) {
			return List.of(ray.getPoint(t1));
		}

		else
			return List.of(ray.getPoint(t2));

	}

}
