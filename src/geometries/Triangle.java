package geometries;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import static primitives.Util.*;
import primitives.Point;
import primitives.Ray;



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
        int len = vertices.size();
		Point p0 = ray.getP0();
		primitives.Vector v = ray.getDir();
		List<Vector> vectors = new ArrayList<Vector>(len);

		//all the vectors
		for (Point vertex : vertices) {
			vectors.add(vertex.subtract(p0));
		}

		int sign = 0;
		for (int i = 0; i < len; i++) {
			// calculate the normal using the formula in the course slides
			Vector N = vectors.get(i).crossProduct(vectors.get((i+1)%len)).normalize();
			double dotProd = v.dotProduct(N);

			if (i == 0)
				sign = dotProd > 0 ? 1 : -1;

			if (!checkSign(sign,dotProd) || isZero(dotProd))
				return null;
		}
		return plane.findIntersections(ray);
    }


}