package geometries;

import primitives.*;

/**
 * Represents a cylinder in 3D space, which is a solid geometric shape with a
 * circular base and straight sides.
 */
public class Cylinder extends Tube {

	/** The height of the cylinder */
	private final double height;

	/**
	 * Constructs a new cylinder with the specified height, axis, and radius.
	 *
	 * @param h      the height of the cylinder
	 * @param r      the axis of the cylinder
	 * @param radius the radius of the cylinder
	 */
	public Cylinder(double h, Ray r, double radius) {
		super(r, radius);
		height = h;
	}

	/**
	 * Returns the height of the cylinder.
	 *
	 * @return the height of the cylinder
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * Returns the normal vector to the surface of the cylinder at the specified
	 * point.
	 * 
	 * @param p the point on the surface of the cylinder
	 * @return the normal vector to the surface of the cylinder at the specified
	 *         point
	 */
	@Override
	public Vector getNormal(Point p) {
		return null;
	}
}