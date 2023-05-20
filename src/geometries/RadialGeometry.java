package geometries;

/**
 * Represents a geometric object that has a radial dimension, such as a sphere
 * or a cylinder.
 * 
 * @author Eti and Chavi
 */
public abstract class RadialGeometry extends Geometry {

	/** The radius of the object */
	protected final double radius;

	/** The squared radius of the object */
	protected final double radius2;

	/**
	 * Constructs a new radial geometry object with the specified radius.
	 *
	 * @param r the radius of the object
	 */
	RadialGeometry(double r) {
		radius = r;
		radius2 = r * r;
	}
}