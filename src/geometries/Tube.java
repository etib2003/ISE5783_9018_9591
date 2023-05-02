package geometries;

import primitives.*;
import static primitives.Util.isZero;
import static primitives.Util.*;

import java.util.List;

/**
 * Represents a tube in 3D space, which is defined by its central axis Ray and
 * radius.
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
	 * @return a list of intersection points, or null if there are no intersections.
	 */
	@Override
	public List<Point> findIntersections(Ray ray) {
		Point d, e, rayP0 = ray.getP0();
		Vector rayDir = ray.getDir();
		double dis, ab, bc, ac;

		// initialize the points and vectors of the axis of the cylinder
		Point pointA = rayP0, pointB = axisRay.getP0();
		Vector vecA = rayDir, vecB = axisRay.getDir();

		// calculate dot product between ray direction vector and axis vector
		ab = vecA.dotProduct(vecB);

		// check if the vectors are parallel, if so return null
		try {
			vecA.crossProduct(vecB);
		} catch (IllegalArgumentException ex) {
			return null;
		}

		// calculate values for quadratic equation
		double bb = 1;
		double aa = 1;
		try {
			Vector c = pointB.subtract(pointA);
			bc = vecB.dotProduct(c);
			ac = vecA.dotProduct(c);

			double t1 = (-ab * bc + ac * bb) / (aa * bb - ab * ab);
			try {
				d = pointA.add(vecA.scale(t1));
			} catch (IllegalArgumentException ex) {
				d = pointA;
			}

			double t2 = (ab * ac - bc * aa) / (1 - ab * ab);

			try {
				e = pointB.add(vecB.scale(t2));
			} catch (IllegalArgumentException ex) {
				e = pointB;
			}
			dis = d.distance(e);

		} catch (IllegalArgumentException ex) {
			d = rayP0;
			dis = 0;
		}

		// check if the ray misses the cylinder
		double diff = alignZero(dis - radius);
		if (diff > 0.0)
			return null;

		// check if the ray is tangent to the cylinder
		if (diff == 0.0) {
			return null;
		}

		// calculate the width of the intersection at the intersection point
		double width;
		try {
			double sinA = vecA.crossProduct(vecB).length();
			width = radius / sinA;
		} catch (IllegalArgumentException ex) { // it is orthogonal
			width = radius;
		}
		double k = width / radius;

		// calculate the intersection points
		double th = Math.sqrt(radius2 - dis * dis) * k;

		Point p1 = d.subtract(vecA.scale(th));
		Point p2 = d.add(vecA.scale(th));

		// check which intersection point is in front of the camera
		try {
			if (!(p1.subtract(pointA).dotProduct(vecA) < 0.0) && !(p2.subtract(pointA).dotProduct(vecA) < 0.0)) {
				return List.of(p1, p2);
			}
		} catch (IllegalArgumentException ex) {
		}

		try {
			if (!(p1.subtract(pointA).dotProduct(vecA) < 0.0)) {
				return List.of(p1);
			}
		} catch (IllegalArgumentException ex) {
		}

		try {
			if (!(p2.subtract(pointA).dotProduct(vecA) < 0.0)) {
				return List.of(p2);
			}
		} catch (IllegalArgumentException ex) {
		}

		return null;
	}
}
