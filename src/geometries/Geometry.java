package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Color;

/**
 * The Geometry interface represents a geometric object in 3D space.
 * 
 * @author Eti and Chavi
 */
public abstract class Geometry extends Intersectable {
	protected Color emission = Color.BLACK;

	/**
	 * @return the emission
	 */
	public Color getEmission() {
		return emission;
	}

	/**
	 * @param emission the emission to set
	 */
	public Geometry setEmission(Color emission) {
		this.emission = emission;
		return this;
	}

	/**
	 * Returns the normal vector to the surface of the geometry at the specified
	 * point.
	 *
	 * @param p the point on the surface of the geometry
	 * @return the normal vector to the surface of the geometry at the specified
	 *         point
	 */
	public abstract Vector getNormal(Point p);
}