package geometries;

import java.util.List;
import java.util.Objects;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
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
	 * Returns all the intersections of ray with the geometry shape.
	 *
	 * @param ray {@link Ray} pointing toward the object.
	 * @return List of intersection {@link Point}s.
	 */
	public List<Point> findIntersections(Ray ray) {
		var geoList = findGeoIntersections(ray);
		return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
	}

	/**
	 * Finds the intersection points of the ray with the surface of the object.
	 *
	 * @param ray The ray to intersect with the GeoPoint.
	 * @return A list of GeoPoints that are the intersections of the ray with the
	 *         object.
	 */
	public final List<GeoPoint> findGeoIntersections(Ray ray) {
		return findGeoIntersectionsHelper(ray);
	}

	/**
	 * Finds the intersection points of the ray with the surface of the object. This
	 * method should be implemented by the subclasses to provide their own
	 * intersection logic.
	 *
	 * @param ray The ray to intersect with the object.
	 * @return A list of GeoPoints that are the intersections of the ray with the
	 *         object.
	 */
	protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

	
	

    /**
     * The AABB class represents an Axis-Aligned Bounding Box.
     * It is used to enclose objects and determine intersection in computer graphics and collision detection algorithms.
     */
    public static class AABB {
        Point min, max, center;

        /**
         * Flag indicating if the AABB is infinite.
         */
        boolean isInfinite = false;

        /**
         * Constant value used for expanding the AABB slightly.
         */
        private static final double DELTA = 0.1;

        /**
         * Checks if the AABB is infinite.
         *
         * @return true if the AABB is infinite, false otherwise.
         */
        public boolean isInfinite() {
            return isInfinite;
        }

        /**
         * Constructs an AABB with the given minimum point, maximum point, and center point.
         * Expands the AABB slightly using the DELTA constant.
         *
         * @param min    The minimum point of the AABB.
         * @param max    The maximum point of the AABB.
         * @param center The center point of the AABB.
         */
        public AABB(Point min, Point max, Point center) {
            min = min.add(new Vector(-DELTA, -DELTA, -DELTA));
            max = max.add(new Vector(DELTA, DELTA, DELTA));
            this.min = min;
            this.max = max;
            this.center = center;
        }

        /**
         * Constructs an AABB with the given minimum and maximum points.
         * Calculates the center point and expands the AABB using the DELTA constant.
         *
         * @param min The minimum point of the AABB.
         * @param max The maximum point of the AABB.
         */
        public AABB(Point min, Point max) {
            min = min.add(new Vector(-DELTA, -DELTA, -DELTA));
            max = max.add(new Vector(DELTA, DELTA, DELTA));
            this.min = min;
            this.max = max;
            center = new Point(
                    (min.getX() + max.getX()) / 2,
                    (min.getY() + max.getY()) / 2,
                    (min.getZ() + max.getZ()) / 2
            );
        }

        /**
         * Checks if the AABB intersects with the given Ray within the specified maximum distance.
         *
         * @param ray    The Ray to check for intersection.
         * @param maxDis The maximum distance at which intersection can occur.
         * @return true if the AABB intersects with the Ray, false otherwise.
         */
        public boolean intersect(Ray ray) {
            if (isInfinite) return true;
            var dir = ray.getDir();
            var vP0 = ray.getP0();
            var invdir = new Vector(1 / dir.getX(), 1 / dir.getY(), 1 / dir.getZ());
            int[] sign = {invdir.getX() < 0 ? 1 : 0, invdir.getY() < 0 ? 1 : 0, invdir.getZ() < 0 ? 1 : 0};
            Point[] bounds = {min, max};
            double px = vP0.getX(),py = vP0.getY(),pz = vP0.getZ();
            double inx = invdir.getX(), iny = invdir.getY(),inz = invdir.getZ();
            double tmin, tmax, tymin, tymax, tzmin, tzmax;
            tmin = (bounds[sign[0]].getX() - px) * inx;
            tmax = (bounds[1 - sign[0]].getX() - px) * inx;
            tymin = (bounds[sign[1]].getY() - py) * iny;
            tymax = (bounds[1 - sign[1]].getY() - py) * iny;
            if ((tmin > tymax) || (tymin > tmax))
                return false;

            if (tymin > tmin)
                tmin = tymin;
            if (tymax < tmax)
                tmax = tymax;
            tzmin = (bounds[sign[2]].getZ() - pz) * inz;
            tzmax = (bounds[1 - sign[2]].getZ() - pz) * inz;

            if ((tmin > tzmax) || (tzmin > tmax))
                return false;
            if (tzmax < tmax)
                tmax = tzmax;

            return true;
        }

        /**
         * Calculates the surface area of the AABB.
         *
         * @return The surface area of the AABB.
         */
        public double AABBArea() {
            Point extent = max.subtract(min);
            return extent.getX() * extent.getY() + extent.getY() * extent.getZ() + extent.getZ() * extent.getX();
        }

        /**
         * Returns the minimum point of the AABB.
         *
         * @return The minimum point of the AABB.
         */
        public Point getMin() {
            return min;
        }

        /**
         * Returns the maximum point of the AABB.
         *
         * @return The maximum point of the AABB.
         */
        public Point getMax() {
            return max;
        }

        /**
         * Returns the center point of the AABB.
         *
         * @return The center point of the AABB.
         */
        public Point getCenter() {
            return center;
        }

        /**
         * Sets the infinity flag of the AABB.
         *
         * @param isInfinite true to set the AABB as infinite, false otherwise.
         * @return The updated AABB instance.
         */
        public AABB setInfinity(boolean isInfinite) {
            this.isInfinite = isInfinite;
            return this;
        }
    }

    /**
     * The box for AABB improvement
     */

    AABB bbox;


}