package geometries;

/**
 * Represents a geometric object that has a radial dimension, such as a sphere
 * or a cylinder.
 */
public abstract class RadialGeometry implements Geometry {

	/** The radius of the object */
	protected final double radius;

	/**
	 * Constructs a new radial geometry object with the specified radius.
	 *
	 * @param r the radius of the object
	 */
	RadialGeometry(double r) {
		radius = r;
	}
}