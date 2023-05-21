package geometries;

import java.util.List;
import primitives.Ray;
import primitives.Point;
import primitives.Vector;

//תועד
/**
 * Represents a cylinder in 3D space, which is a solid geometric shape with a
 * circular base and straight sides.
 * 
 * This class extends the Tube class and adds a height parameter to define the
 * height of the cylinder.
 * 
 * @author Eti and Chavi
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
	 * Calculates the normal vector to the cylinder at the specified point.
	 * 
	 * @param point the point on the cylinder surface
	 * @return the normal vector at the specified point
	 */
	public Vector getNormal(Point point) {
		// Define the center of the cylinder's sides
		Vector cylinderCenterVector = axisRay.getDir();
		Point centerOfOneSide = axisRay.getP0();
		Point centerOfSecondSide = axisRay.getP0().add(axisRay.getDir().scale(height));

		// The normal at a base will be simply equal to the central ray's direction
		// vector v or opposite to it (-v), so we check it
		if (point.equals(centerOfOneSide)) {
			return cylinderCenterVector.scale(-1);
		} else if (point.equals(centerOfSecondSide)) {
			return cylinderCenterVector;
		}

		// If the point is on one of the cylinder's bases, but it's not the center point
		double projection = cylinderCenterVector.dotProduct(point.subtract(centerOfOneSide));
		if (projection == 0) {
			Vector v1 = point.subtract(centerOfOneSide);
			return v1.normalize();
		}

		// If the point is on the side of the cylinder
		Point center = centerOfOneSide.add(cylinderCenterVector.scale(projection));
		Vector v = point.subtract(center);

		return v.normalize();
	}

	/**
	 * Helper method for finding geometric intersections of a ray with the cylinder.
	 * 
	 * @param ray the ray to intersect with the cylinder
	 * @return a list of GeoPoint objects representing the intersection points, or
	 *         null if there are no intersections
	 */
	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		// TODO: Implement the intersection calculation for a cylinder
		return null;
	}
}
