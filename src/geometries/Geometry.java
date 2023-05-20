package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Color;
import primitives.Material;

/**
 * The Geometry interface represents a geometric object in 3D space.
 * 
 * @author Eti and Chavi
 */
public abstract class Geometry extends Intersectable {
	protected Color emission = Color.BLACK;
	private Material material = new Material();

	/**
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * @param material the material to set
	 */
	public Geometry setMaterial(Material material) {
		this.material = material;
		return this;
	}

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