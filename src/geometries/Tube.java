package geometries;

import primitives.*;
import static primitives.Util.isZero;

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
		Vector dir=axisRay.getDir();
		Point p0=axisRay.getP0();
		double t = dir.dotProduct(p.subtract(p0));
		if (isZero(t)) return p.subtract(p0).normalize();
		Point point0 = p0.add(dir.scale(t));
		return p.subtract(point0).normalize();
	}
	
	@Override
	public List<Point> findIntersections(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}
	

}