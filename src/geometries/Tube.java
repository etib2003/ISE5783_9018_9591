package geometries;

import primitives.*;
import static primitives.Util.isZero;
import static primitives.Util.*;

import java.util.ArrayList;
import java.util.List;

//תועד
/**
 * Represents a tube in 3D space, which is defined by its central axis Ray and
 * radius.
 * 
 * @author Eti and Chavi
 */
public class Tube extends RadialGeometry {

	/**
	 * The central axis Ray of the tube.
	 */
	protected final Ray axisRay;

	/**
	 * Constructs a Tube object with the given axis Ray and radius.
	 *
	 * @param r      The central axis Ray of the tube.
	 * @param radius The radius of the tube.
	 */
	public Tube(Ray r, double radius) {
		super(radius);
		axisRay = r;
	}

	/**
	 * Returns the central axis Ray of the tube.
	 *
	 * @return The central axis Ray of the tube.
	 */
	public Ray getAxisRay() {
		return axisRay;
	}

	/**
	 * Returns the normal to the tube at the given point (which is null, since it
	 * has no defined normal).
	 *
	 * @param p The point at which to find the normal.
	 * @return The normal to the tube at the given point (which is null).
	 */
	@Override
	public Vector getNormal(Point p) {
		Vector dir = axisRay.getDir();
		Point p0 = axisRay.getP0();
		double t = dir.dotProduct(p.subtract(p0));
		if (isZero(t))
			return p.subtract(p0).normalize();
		Point point0 = p0.add(dir.scale(t));
		return p.subtract(point0).normalize();
	}

	// BONUS
	/**
	 * 
	 * This method finds the intersections of the given ray with this cylinder.
	 * 
	 * @param ray the ray to intersect with this cylinder.
	 * @return A list of intersection points, or null if there are no intersections.
	 */
	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		Vector dir = ray.getDir();
		Vector v = axisRay.getDir();
		double dirV = dir.dotProduct(v);

		if (ray.getP0().equals(axisRay.getP0())) { // In case the ray starts on the p0.
			if (isZero(dirV))
				return List.of(new GeoPoint(this, ray.getPoint(radius)));

			if (dir.equals(v.scale(dir.dotProduct(v))))
				return null;

			return List.of(new GeoPoint(this, ray
					.getPoint(Math.sqrt(radius * radius / dir.subtract(v.scale(dir.dotProduct(v))).lengthSquared()))));
		}

		Vector deltaP = ray.getP0().subtract(axisRay.getP0());
		double dpV = deltaP.dotProduct(v);

		double a = 1 - dirV * dirV;
		double b = 2 * (dir.dotProduct(deltaP) - dirV * dpV);
		double c = deltaP.lengthSquared() - dpV * dpV - radius * radius;

		if (isZero(a)) {
			if (isZero(b)) { // If a constant equation.
				return null;
			}
			return List.of(new GeoPoint(this, ray.getPoint(-c / b))); // if it's linear, there's a solution.
		}

		double discriminant = alignZero(b * b - 4 * a * c);

		if (discriminant < 0) // No real solutions.
			return null;

		double t1 = alignZero(-(b + Math.sqrt(discriminant)) / (2 * a)); // Positive solution.
		double t2 = alignZero(-(b - Math.sqrt(discriminant)) / (2 * a)); // Negative solution.

		if (discriminant <= 0) // No real solutions.
			return null;

		if (t1 > 0 && t2 > 0) {
			List<GeoPoint> _points = new ArrayList<>(2);
			_points.add(new GeoPoint(this, ray.getPoint(t1)));
			_points.add(new GeoPoint(this, ray.getPoint(t2)));
			return _points;
		} else if (t1 > 0) {
			List<GeoPoint> _points = new ArrayList<>(1);
			_points.add(new GeoPoint(this, ray.getPoint(t1)));
			return _points;
		} else if (t2 > 0) {
			List<GeoPoint> _points = new ArrayList<>(1);
			_points.add(new GeoPoint(this, ray.getPoint(t2)));
			return _points;
		}
		return null;
	}
}
