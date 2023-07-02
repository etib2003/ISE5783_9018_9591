package primitives;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static primitives.Util.isZero;

/**
 * 
 * The Point class represents a point in 3-dimensional space.
 * 
 * @author Eti and Chavi
 */
public class Point {

	/** The origin point with coordinates (0, 0, 0). */
	public static final Point ZERO = new Point(0, 0, 0);

	/** The 3-dimensional coordinates of this point. */
	protected final Double3 xyz;

	/**
	 * 
	 * Constructs a new Point object with the given x, y, and z coordinates.
	 * 
	 * @param x the x-coordinate of the point
	 * @param y the y-coordinate of the point
	 * @param z the z-coordinate of the point
	 */
	public Point(double x, double y, double z) {
		xyz = new Double3(x, y, z);
	}

	/**
	 * 
	 * Constructs a new Point object with the given Double3 object.
	 * 
	 * @param point the Double3 object containing the coordinates of the point
	 */
	Point(Double3 point) {
		xyz = point;
	}

	public Point(double xyz) {
		this(xyz, xyz, xyz);
	}

	/**
	 * 
	 * Determines whether this point is equal to another object.
	 * 
	 * @param obj the object to compare this point to
	 * @return true if the objects are equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return (obj instanceof Point other) && (this.xyz.equals(other.xyz));
	}

	/**
	 * 
	 * Returns a string representation of this point.
	 * 
	 * @return a string representation of this point in the form (x, y, z)
	 */
	@Override
	public String toString() {
		return xyz.toString();
	}

	/**
	 * 
	 * Returns a new Vector object representing the vector from the given point to
	 * this point.
	 * 
	 * @param p the point to subtract from this point
	 * @return a new Vector object representing the vector from the given point to
	 *         this point
	 */
	public Vector subtract(Point p) {
		return new Vector(this.xyz.subtract(p.xyz));
	}

	/**
	 * 
	 * Returns a new Point object representing the result of adding the given vector
	 * to this point.
	 * 
	 * @param v the vector to add to this point
	 * @return a new Point object representing the result of adding the given vector
	 *         to this point
	 */
	public Point add(Vector v) {
		return new Point(this.xyz.add(v.xyz));
	}

	/**
	 * 
	 * Returns the squared distance between this point and the given point.
	 * 
	 * @param p the point to calculate the distance to
	 * @return the squared distance between this point and the given point
	 */
	public double distanceSquared(Point p) {
		double d1 = this.xyz.d1 - p.xyz.d1;
		double d2 = this.xyz.d2 - p.xyz.d2;
		double d3 = this.xyz.d3 - p.xyz.d3;

		return d1 * d1 + d2 * d2 + d3 * d3;
	}

	/**
	 * Returns the distance between this point and the given point.
	 * 
	 * @param p the point to calculate the distance to
	 * @return the distance between this point and the given point
	 */
	public double distance(Point p) {
		return Math.sqrt(distanceSquared(p));
	}

	/**
	 * Returns the x-coordinate of this point.
	 * 
	 * @return the x-coordinate of this point
	 */
	public double getX() {
		return this.xyz.d1;
	}

	/**
	 * Returns the y-coordinate of this point.
	 * 
	 * @return the y-coordinate of this point
	 */
	public double getY() {
		return this.xyz.d2;
	}

	/**
	 * Returns the z-coordinate of this point.
	 * 
	 * @return the z-coordinate of this point
	 */
	public double getZ() {
		return this.xyz.d3;
	}

	/**
	 * 
	 * Generates points on a circle given the center, up vector, right vector,
	 * radius, and grid density.
	 * 
	 * @param center      the center point of the circle
	 * @param vUp         the up vector defining the orientation of the circle
	 * @param vRight      the right vector defining the orientation of the circle
	 * @param radius      the radius of the circle
	 * @param gridDensity the density of the grid used for generating points
	 * @return a list of points generated on the circle
	 */
	public static List<Point> generatePointsOnCircle(Point center, Vector vUp, Vector vRight, double radius,
			double gridDensity) {
		Random random = new Random();
		var points = new ArrayList<Point>();
		for (double i = -radius; i < radius; i += radius / gridDensity) {
			if (isZero(i))
				continue;
			double jitterOffset = random.nextDouble(-0.1, 0.1);
			for (double j = -radius; j < radius; j += radius / gridDensity) {
				if (isZero(j))
					continue;
				var p = center.add(vUp.scale(i).add(vRight.scale(j + jitterOffset)));
				if (center.distance(p) <= radius)
					points.add(p);
			}
		}
		return points;
	}

	/**
	 * Creates a new Point object with the maximum coordinates from two given
	 * points.
	 *
	 * @param p1 the first Point object
	 * @param p2 the second Point object
	 * @return a new Point object with the maximum coordinates
	 */
	public static Point createMaxPoint(Point p1, Point p2) {
		return new Point(Math.max(p1.getX(), p2.getX()), Math.max(p1.getY(), p2.getY()),
				Math.max(p1.getZ(), p2.getZ()));
	}

	/**
	 * Creates a new Point object with the minimum coordinates from two given
	 * points.
	 *
	 * @param p1 the first Point object
	 * @param p2 the second Point object
	 * @return a new Point object with the minimum coordinates
	 */
	public static Point createMinPoint(Point p1, Point p2) {
		return new Point(Math.min(p1.getX(), p2.getX()), Math.min(p1.getY(), p2.getY()),
				Math.min(p1.getZ(), p2.getZ()));
	}

}