package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Point;

/**
 * Represents a tube in 3D space, which is defined by its central axis Ray and radius.
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
		double t=axisRay.getDir().dotProduct(p.subtract(axisRay.getP0()));
		Point point0 =axisRay.getP0().add(axisRay.getDir().scale(t));
		return p.subtract(point0).normalize();
	}
}