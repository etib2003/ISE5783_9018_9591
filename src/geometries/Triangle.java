package geometries;


import static primitives.Util.*;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;




/**
 * Represents a triangle in 3D space.
 */
public class Triangle extends Polygon {

	/**
	 * Constructs a new triangle with the specified vertices.
	 *
	 * @param p1 the first vertex of the triangle
	 * @param p2 the second vertex of the triangle
	 * @param p3 the third vertex of the triangle
	 */
	public Triangle(Point p1, Point p2, Point p3) {
		super(p1, p2, p3);
	}
	
	@Override
	public List<Point> findIntersections(Ray ray) {
		List<Point> rayPoints = plane.findIntersections(ray);
		if (rayPoints == null)
			return null;
		//check if the point in out or on the triangle:
		Point rayP0=ray.getP0();
		Vector v1 = vertices.get(0).subtract(rayP0);
		Vector v2 = vertices.get(1).subtract(rayP0);
		Vector v3 = vertices.get(2).subtract(rayP0);
		
		Vector n1 = v1.crossProduct(v2).normalize();
		Vector n2 = v2.crossProduct(v3).normalize();
		Vector n3 = v3.crossProduct(v1).normalize();

		
		//The point is inside if all  have the same sign (+/-)
		Vector rayDir=ray.getDir();

		if (alignZero(n1.dotProduct(rayDir)) > 0 && alignZero(n2.dotProduct(rayDir)) > 0 && alignZero(n3.dotProduct(rayDir)) > 0)
		{
			return rayPoints;
		}
		else if (alignZero(n1.dotProduct(rayDir)) < 0 && alignZero(n2.dotProduct(rayDir)) < 0 && alignZero(n3.dotProduct(rayDir)) < 0)
		{
			return rayPoints;
		}
		if (isZero(n1.dotProduct(rayDir)) || isZero(n2.dotProduct(rayDir)) || isZero(n3.dotProduct(rayDir)))
			return null; //there is no instruction point
		return null;
	}


}