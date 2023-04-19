package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.Point;
import primitives.Ray;

public class Geometries extends Intersectable{
	
	private List<Intersectable> geometricBodies;

	/**
	 * Default Constructor build empty list of bodies
	 */
	public Geometries() {
		this.geometricBodies = new LinkedList<Intersectable>();
  	}

	/**
	 * Constructor with arguments
	 * 
	 * @param geometries list of bodies
	 */
	public Geometries(Intersectable... geometries) {
		this(); // first we initialize the list
	    add(geometries);
	}

	/**
	 * get all the bodies
	 */
	public List<Intersectable> getBudies() {
		return geometricBodies;
	}

	/**
	 * add bodies to the list of bodies
	 * 
	 * @param geometries list of bodies to add
	 */
	public void add(Intersectable... geometries) {
		for (var intersectable : geometries) {
			geometricBodies.add(intersectable);
	 
		}
	}

	/**
	 * This function returns only the relevant point of the intersection using the
	 * help of regular grid structure if the box is null that means we call the
	 * regular find intersection (without acceleration)
	 * 
	 * @param ray            - Ray that intersect
	 * @param box            - box of the scene
	 * @param shadowRaysCase - if its shadow ray we traverse always all the way .
	 * @param dis            - distance for find intersection
	 * @return the relevant point
	 */
	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
		List<GeoPoint> points = null;
		if (geometricBodies != null) {
			for (var body : geometricBodies) {
				var result = body.findGeoIntersections(ray, maxDistance);
				if (result != null)
					if (points == null)
						points = new LinkedList<GeoPoint>(result);
					else
						points.addAll(result);
			}
		}
		return points;
	}
}
