package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Color;
import primitives.Material;

/**
 * The Geometry interface represents a geometric object in 3D space.
 * 
 * This interface defines methods for retrieving and setting the material and
 * emission properties of the geometry. It also includes an abstract method for
 * calculating the normal vector to the surface of the geometry at a specified
 * point.
 * 
 * Implementing classes should provide their own implementation of the getNormal
 * method.
 * 
 * The geometry can have a color emission and a material that determines its
 * visual and physical properties.
 * 
 * @author Eti and Chavi
 */
public abstract class Geometry extends Intersectable {
	protected Color emission = Color.BLACK;
	private Material material = new Material();

	/**
	 * Returns the material of the geometry.
	 *
	 * @return the material of the geometry
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * Sets the material of the geometry.
	 *
	 * @param material the material to set
	 * @return the geometry itself
	 */
	public Geometry setMaterial(Material material) {
		this.material = material;
		return this;
	}

	/**
	 * Returns the emission color of the geometry.
	 *
	 * @return the emission color of the geometry
	 */
	public Color getEmission() {
		return emission;
	}

	/**
	 * Sets the emission color of the geometry.
	 *
	 * @param emission the emission color to set
	 * @return the geometry itself
	 */
	public Geometry setEmission(Color emission) {
		this.emission = emission;
		return this;
	}

	/**
	 * Calculates the normal vector to the surface of the geometry at the specified
	 * point.
	 *
	 * @param p the point on the surface of the geometry
	 * @return the normal vector to the surface of the geometry at the specified
	 *         point
	 */
	public abstract Vector getNormal(Point p);
}
