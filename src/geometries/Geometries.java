package geometries;

import java.util.LinkedList;
import java.util.List;
import primitives.Ray;

/**
 * The Geometries class represents a collection of intersectable geometries.
 * 
 * It extends the Intersectable class and stores a list of intersectable
 * objects. This class allows for adding intersectable objects to the list and
 * finding the intersections of a ray with the geometries in the list.
 * 
 * @author Eti and Chavi
 *
 */
public class Geometries extends Intersectable {

	private List<Intersectable> geometricBodies = new LinkedList<>();

	/**
	 * Default constructor that initializes an empty list of bodies.
	 */
	public Geometries() {
	}

	/**
	 * Constructor that accepts a list of geometries as its argument and initializes
	 * the list.
	 *
	 * @param geometries A variable number of intersectable objects to add to the
	 *                   list.
	 */
	public Geometries(Intersectable... geometries) {
		add(geometries);
	}

	/**
	 * Returns the list of intersectable geometries.
	 *
	 * @return A list of intersectable objects.
	 */
	public List<Intersectable> getBodies() {
		return geometricBodies;
	}

	/**
	 * Adds a variable number of intersectable objects to the list.
	 *
	 * @param geometries A variable number of intersectable objects to add to the
	 *                   list.
	 */
	public void add(Intersectable... geometries) {
		geometricBodies.addAll(List.of(geometries));
	}

	/**
	 * Finds all the intersections of the given ray with the geometries in the list.
	 *
	 * @param ray The ray to check for intersections.
	 * @return A list of intersection points, or null if there are no intersections.
	 */
	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		List<GeoPoint> res = null;
		for (Intersectable geometry : this.geometricBodies) {
			var resi = geometry.findGeoIntersections(ray);
			if (resi != null) {
				if (res == null) {
					res = new LinkedList<>();
				}
				res.addAll(resi);
			}
		}
		return res;
	}
}