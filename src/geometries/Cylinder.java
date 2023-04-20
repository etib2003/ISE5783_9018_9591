package geometries;
import static primitives.Util.isZero;

import java.util.List;

import primitives.Ray;
import primitives.Point;
import primitives.Vector;


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

    public Vector getNormal(Point point) {

        // define the center of cylinder's sides
        Vector cylinderCenterVector = axisRay.getDir();

        Point centerOfOneSide = axisRay.getP0();
        Point centerOfSecondSide = axisRay.getP0().add(axisRay.getDir().scale(height));

        //The normal at a base will be simply equal to central ray's
        //direction vector 𝑣 or opposite to it (−𝑣) so we check it
        if (point.equals(centerOfOneSide)) {
            return cylinderCenterVector.scale(-1);
        }
        else if (point.equals(centerOfSecondSide)){
            return cylinderCenterVector;
        }

        //If the point on one of the cylinder's bases, but it's not the center point
        double projection = cylinderCenterVector.dotProduct(point.subtract(centerOfOneSide));
        if (projection == 0) {
            Vector v1 = point.subtract(centerOfOneSide);
            return v1.normalize();
        }

        //If the point on the side of the cylinder.
        Point center = centerOfOneSide.add(cylinderCenterVector.scale(projection));
        Vector v = point.subtract(center);

        return v.normalize();
    }
    
    @Override
	public List<Point> findIntersections(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}