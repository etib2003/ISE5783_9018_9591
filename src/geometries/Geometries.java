package geometries;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import primitives.Point;
import primitives.Ray;

public class Geometries implements Intersectable{
	
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


	@Override
	public List<Point> findIntersections(Ray ray) {
		List<Point> temp = new ArrayList<Point>();
		for (Intersectable intersectable : geometricBodies) 
		{
			List<Point> intersection = intersectable.findIntersections(ray);
			if (intersection != null)
				temp.addAll(intersection); 
		}
		
		if (temp.isEmpty())
			return null;
		return temp;
	}

}
