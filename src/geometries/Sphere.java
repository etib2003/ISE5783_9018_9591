package geometries;
import primitives.*;

/**
 * Represents a sphere in 3D space.
 */
public class Sphere extends RadialGeometry {
	
	/** The center point of the sphere */
	private final Point center;

	/**
	 * Constructs a new sphere with the specified center point and radius.
	 *
	 * @param p the center point of the sphere
	 * @param radius the radius of the sphere
	 */
	public Sphere(Point p, double radius) {
		super(radius);
		center = p;
	}

	/**
	 * Returns the center point of the sphere.
	 *
	 * @return the center point of the sphere
	 */
	public Point getCenter() {
		return center;
	}

	@Override
	public Vector getNormal(Point p) {
		return (p.subtract(getCenter())).normalize();
	}
}
