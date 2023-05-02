package geometries;

import java.util.LinkedList;
import java.util.List;
import primitives.Point;
import primitives.Ray;

/**
 * The Geometries class represents a collection of intersectable geometries.
 * 
 * @author אתוש
 *
 */
public class Geometries implements Intersectable {

	private List<Intersectable> geometricBodies;

	/**
	 * Default constructor that initializes an empty list of bodies.
	 */
	public Geometries() {
		this.geometricBodies = new LinkedList<Intersectable>();
	}

	/**
	 * Constructor that accepts a list of geometries as its argument and initializes
	 * the list.
	 *
	 * @param geometries A variable number of intersectable objects to add to the
	 *                   list.
	 */
	public Geometries(Intersectable... geometries) {
		this();
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
		for (var intersectable : geometries) {
			geometricBodies.add(intersectable);
		}
	}

	/**
	 * Finds all the intersections of the given ray with the geometries in the list.
	 *
	 * @param ray The ray to check for intersections.
	 * @return A list of intersection points, or null if there are no intersections.
	 */
	@Override
	public List<Point> findIntersections(Ray ray) {
		List<Point> res = null;
		for (Intersectable geometry : this.geometricBodies) {
			List<Point> resi = geometry.findIntersections(ray);
			if (resi != null) {
				if (res == null) {
					res = new LinkedList<Point>();
				}
				res.addAll(resi);
			}
		}
		return res;
	}
}
