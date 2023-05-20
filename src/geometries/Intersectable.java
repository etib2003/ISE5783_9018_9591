/**
 * 
 */
package geometries;

import java.util.List;
import java.util.Objects;

import primitives.Point;
import primitives.Ray;

/**
 * 
 * The Intersectable interface represents any object in the scene that can be
 * intersected by a ray.
 * 
 * This interface provides a method to find intersections between a given ray
 * and the object.
 * 
 * @author Eti and Chavi
 */
public abstract class Intersectable {

	/**
	 * Represents a geographic point with associated geometry information.
	 */
	public static class GeoPoint {
		/**
		 * The geometry information of the point.
		 */
		public Geometry geometry;

		/**
		 * The actual point coordinates.
		 */
		public Point point;

		/**
		 * Constructs a GeoPoint object with the specified geometry and point
		 * coordinates.
		 *
		 * @param geometry The geometry information of the point.
		 * @param point    The actual point coordinates.
		 */
		public GeoPoint(Geometry geometry, Point point) {
			this.geometry = geometry;
			this.point = point;
		}

		/**
		 * Checks if this GeoPoint is equal to another object.
		 *
		 * @param o The object to compare.
		 * @return {@code true} if the objects are equal, {@code false} otherwise.
		 */
		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			GeoPoint geoPoint = (GeoPoint) o;
			return Objects.equals(geometry, geoPoint.geometry) && point.equals(geoPoint.point);
		}

		/**
		 * Returns a string representation of the GeoPoint object.
		 *
		 * @return A string representation of the object.
		 */
		@Override
		public String toString() {
			return "GeoPoint{" + "geometry=" + geometry + ", point=" + point + '}';
		}

	}

	 /**
     * Returns all the intersections of ray with geometry shape
     *
     * @param ray {@link Ray} pointing toward the object
     * @return List of intersection {@link Point}s
     */
    public final List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream()
                .map(gp -> gp.point)
                .toList();
    }
    
    /**
     * This function returns a list of all the intersections of the ray with the geometry of the scene
     *
     * @param ray The ray to find intersections with.
     * @return A list of GeoPoints.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray){
        return findGeoIntersectionsHelper(ray);
    }
	
    /**
     * Finds the intersection points of the ray with the surface of the object
     *
     * @param ray The ray to intersect with the GeoPoint.
     * @return A list of GeoPoints that are the intersections of the ray with the object.
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
}
