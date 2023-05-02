package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * The Geometry interface represents a geometric object in 3D space.
 */
public interface Geometry extends Intersectable {

	/**
	 * Returns the normal vector to the surface of the geometry at the specified
	 * point.
	 *
	 * @param p the point on the surface of the geometry
	 * @return the normal vector to the surface of the geometry at the specified
	 *         point
	 */
	public Vector getNormal(Point p);
}