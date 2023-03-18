package primitives;

/**

*The Point class represents a point in 3-dimensional space.
*/
public class Point {

/**

*The 3-dimensional coordinates of this point.
*/
protected final Double3 xyz;
/**

*Constructs a new Point object with the given x, y, and z coordinates.
*@param x the x-coordinate of the point
*@param y the y-coordinate of the point
*@param z the z-coordinate of the point
*/
public Point(double x, double y, double z) {
xyz = new Double3(x, y, z);
}
/**

*Constructs a new Point object with the given Double3 object.
*@param point the Double3 object containing the coordinates of the point
*/
Point(Double3 point) {
xyz = point;
}
/**

*Determines whether this point is equal to another object.
*@param obj the object to compare this point to
*@return true if the objects are equal, false otherwise
*/
@Override
public boolean equals(Object obj) {
if (this == obj)
return true;
if (obj instanceof Point other)
return this.xyz.equals(other.xyz);
return false;
}
/**

*Returns a string representation of this point.
*@return a string representation of this point in the form (x, y, z)
*/
@Override
public String toString() {
return xyz.toString();
}
/**

*Returns a new Vector object representing the vector from the given point to this point.
*@param p the point to subtract from this point
*@return a new Vector object representing the vector from the given point to this point
*/
public Vector subtract(Point p) {
return new Vector(this.xyz.subtract(p.xyz));
}
/**

*Returns a new Point object representing the result of adding the given vector to this point.
*@param v the vector to add to this point
*@return a new Point object representing the result of adding the given vector to this point
*/
public Point add(Vector v) {
return new Point(this.xyz.add(v.xyz));
}
/**

*Returns the squared distance between this point and the given point.
*@param p the point to calculate the distance to
*@return the squared distance between this point and the given point
*/
 	public double distanceSquared(Point p) {
		return (this.xyz.d1 - p.xyz.d1) * (this.xyz.d1 - p.xyz.d1) + (this.xyz.d2 - p.xyz.d2) * (this.xyz.d2 - p.xyz.d2)
				+ (this.xyz.d3 - p.xyz.d3) * (this.xyz.d3 - p.xyz.d3);
	}

	public double distance(Point p) {
		return Math.sqrt(distanceSquared(p));
	}
}