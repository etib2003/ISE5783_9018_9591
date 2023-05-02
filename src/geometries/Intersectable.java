/**
 * 
 */
package geometries;

import java.util.List;
import primitives.Point;
import primitives.Ray;

/**
 * @author user
 *
 */
/**
 * 
 * The Intersectable interface represents any object in the scene that can be
 * intersected by a ray.
 * 
 * This interface provides a method to find intersections between a given ray
 * and the object.
 */
public interface Intersectable {

	/**
	 * 
	 * Returns a list of intersection points between a given ray and the object. If
	 * no intersections are found, an empty list is returned.
	 * 
	 * @param ray The ray to intersect with the object
	 * @return A list of intersection points between the ray and the object, or an
	 *         empty list if no intersections are found
	 */
	List<Point> findIntersections(Ray ray);

}
