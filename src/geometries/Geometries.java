package geometries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import primitives.Point;
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

	static List<Function<Intersectable, Double>> axes = new ArrayList<>(
			Arrays.asList((x) -> x.bbox.center.getX(), (x) -> x.bbox.center.getY(), (x) -> x.bbox.center.getZ()));

	/**
	 * Default constructor that initializes an empty list of bodies.
	 */
	public Geometries() {
		if (CBR)
			bbox = new AABB(new Point(Double.POSITIVE_INFINITY), new Point(Double.NEGATIVE_INFINITY));
	}

	/**
	 * Constructor that accepts a list of geometries as its argument and initializes
	 * the list.
	 *
	 * @param geometries A variable number of intersectable objects to add to the
	 *                   list.
	 */
	public Geometries(Intersectable... geometries) {
		if (CBR)
			bbox = new AABB(new Point(Double.POSITIVE_INFINITY), new Point(Double.NEGATIVE_INFINITY));
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

	/***
	 * add geometries to the list
	 * 
	 * @param geometries to add
	 */
	public void add(Intersectable... geometries) {
		add(Arrays.stream(geometries).toList());
	}

	/***
	 * add geometries to te list
	 * 
	 * @param geometries to add
	 */
	public Geometries add(List<Intersectable> geometries) {
		if (geometries.size() == 0)
			return this;
		if (CBR) {
			Point maxBbox = bbox.getMax(), minBbox = bbox.getMin();
			boolean inf = bbox.isInfinite();
			for (Intersectable geometry : geometries) {
				geometricBodies.add(geometry);
				if (geometry.bbox.isInfinite())
					inf = true;
				maxBbox = Point.createMaxPoint(maxBbox, geometry.bbox.getMax());
				minBbox = Point.createMinPoint(minBbox, geometry.bbox.getMin());
			}
			bbox = new AABB(minBbox, maxBbox).setInfinity(inf);
		} else
			this.geometricBodies.addAll(geometries);
		return this;
	}

	/**
	 * Finds all the intersections of the given ray with the geometries in the list.
	 *
	 * @param ray The ray to check for intersections.
	 * @return A list of intersection points, or null if there are no intersections.
	 */
	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		LinkedList<GeoPoint> list = null;
		for (Intersectable body : geometricBodies) {
			var temp = body.findGeoIntersections(ray);
			if (temp != null) {
				if (list == null)
					list = new LinkedList<>();
				list.addAll(temp);
			}
		}
		return list;
	}

	/**
	 * Builds a Bounding Volume Hierarchy (BVH) for the geometries.
	 * 
	 * @return The root node of the BVH.
	 */
	public Geometries BuildBVH() {
		if (geometricBodies.size() <= 2)
			return this;
		double[] arr = decideHowToSplitBySAH();
		List<List<Intersectable>> split = splitByAxis(axes.get((int) arr[0]), arr[1]);
		geometricBodies = new LinkedList<>();
		add(new Geometries().add(split.get(0)).BuildBVH(), new Geometries().add(split.get(1)).BuildBVH());
		if (split.size() == 3)
			add(split.get(2));

		return this;
	}

	/**
	 * Evaluates the Surface Area Heuristic (SAH) cost for a given splitting
	 * position.
	 * 
	 * @param func The function to evaluate for each intersectable.
	 * @param pos  The splitting position.
	 * @return The SAH cost for the given splitting position.
	 */
	double evaluateSAH(Function<Intersectable, Double> func, double pos) {
		Geometries left = new Geometries(), right = new Geometries();
		for (Intersectable i : geometricBodies) {
			if (i.bbox.isInfinite()) {
				continue;
			}
			if (func.apply(i) < pos) {
				left.add(i);
			} else {
				right.add(i);
			}
		}
		double cost = left.geometricBodies.size() * left.bbox.AABBArea()
				+ right.geometricBodies.size() * right.bbox.AABBArea();
		return cost > 0 ? cost : Double.POSITIVE_INFINITY;
	}

	/**
	 * Decides the best axis and position to split the geometries using the Surface
	 * Area Heuristic (SAH).
	 * 
	 * @return An array containing the best axis and position to split.
	 */
	double[] decideHowToSplitBySAH() {
		int bestAxis = -1;
		double bestPos = 0, bestCost = Double.POSITIVE_INFINITY;
		for (int axis = 0; axis < 3; axis++) {
			for (Intersectable i : geometricBodies) {
				double candidatePos = axes.get(axis).apply(i);
				double cost = evaluateSAH(axes.get(axis), candidatePos);
				if (cost < bestCost) {
					bestPos = candidatePos;
					bestAxis = axis;
					bestCost = cost;
				}
			}
		}
		return new double[] { bestAxis, bestPos };
	}

	/**
	 * Splits the geometries into three lists based on a given splitting function
	 * and position.
	 * 
	 * @param func The function to determine the splitting position for each
	 *             intersectable.
	 * @param pos  The splitting position.
	 * @return A list containing three lists of intersectables: left, right, and
	 *         infinite.
	 */
	List<List<Intersectable>> splitByAxis(Function<Intersectable, Double> func, double pos) {
		List<List<Intersectable>> list = new ArrayList<>(Arrays.asList(new ArrayList<>(), new ArrayList<>()));

		for (Intersectable i : geometricBodies) {
			if (i.bbox.isInfinite()) {
				if (list.size() == 2)
					list.add(new ArrayList<>());
				list.get(2).add(i);
			} else if (func.apply(i) < pos) {
				list.get(0).add(i);
			} else {
				list.get(1).add(i);
			}
		}
		return list;
	}
}