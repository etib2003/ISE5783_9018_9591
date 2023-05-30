package renderer;

import java.util.List;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.Color;
import primitives.Double3;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;
import primitives.Material;

import static primitives.Util.alignZero;

/**
 * The RayTracerBasic class is a concrete implementation of the RayTracerBase
 * abstract class. This class is responsible for tracing rays in the scene and
 * calculating their color. The class implements the traceRay method, which
 * returns the color of the closest point of intersection with an object in the
 * scene. The class also implements the calcColor method, which calculates the
 * color at a given point in the scene.
 * 
 * @author Eti and Chavi
 *
 */
public class RayTracerBasic extends RayTracerBase {
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;
	private static final Double3 INIT_CALC_COLOR_K = Double3.ONE;

	/**
	 * Constructs a RayTracerBasic object with the given scene.
	 * 
	 * @param scene the scene to be rendered.
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
	}

	/**
	 * Traces a given ray and returns the color of the closest point of intersection
	 * with an object in the scene. If there are no intersections, returns the
	 * background color of the scene.
	 * 
	 * @param ray the ray to be traced.
	 * @return the color of the closest point of intersection with an object in the
	 *         scene.
	 */
	@Override
	public Color traceRay(Ray ray) {
		GeoPoint closestPoint = findClosestIntersection(ray);
		return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
	}

	private GeoPoint findClosestIntersection(Ray ray) {
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
		return intersections == null ? null : ray.findClosestGeoPoint(intersections);
	}

	/**
	 * Calculates the color of a given point in the scene.
	 *
	 * @param geoPoint the point to calculate the color for
	 * @param ray      the ray that intersects the point
	 * @return the color at the given point
	 */
	private Color calcColor(GeoPoint geoPoint, Ray ray) {
		return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INIT_CALC_COLOR_K).add(scene.ambientLight.getIntensity());
	}

	/*
	private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
		Color color = calcLocalEffects(geoPoint, ray, k);
		return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray, level, k));
	}*/
	
	private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
		Color color = calcLocalEffects(geoPoint, ray);
		return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray, level, k));
	}

	private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
		Color color = Color.BLACK;
		Vector v = ray.getDir();
		Vector n = gp.geometry.getNormal(gp.point);
		Material material = gp.geometry.getMaterial();
		return calcGlobalEffects(gp, level, color, material.kR, k, constructReflectedRay(gp, v, n))
				.add(calcGlobalEffects(gp, level, color, material.kT, k, constructRefractedRay(gp, v, n)));
	}

	private Color calcGlobalEffects(GeoPoint geoPoint, int level, Color color, Double3 kx, Double3 k, Ray ray) {
		Double3 kkx = kx.product(k);
		if (kkx.lowerThan(MIN_CALC_COLOR_K))
			return Color.BLACK;
		GeoPoint reflectedPoint = findClosestIntersection(ray);
		if (reflectedPoint != null) {
			color = color.add(calcColor(reflectedPoint, ray, level - 1, kkx).scale(kx));
		}
		return color;
	}

	private Ray constructReflectedRay(GeoPoint gp, Vector v, Vector n) {// #TODO
		Vector reflectedVector = v.subtract(n.scale(2 * v.dotProduct(n)));
		return new Ray(gp.point, reflectedVector, n);
	}

	private Ray constructRefractedRay(GeoPoint gp, Vector v, Vector n) {// #TODO
		return new Ray(gp.point, v, n);
	}

	/*
	 * private Color calcLocalEffects(GeoPoint geoPoint, Ray ray,Double3 k) { Color
	 * color = geoPoint.geometry.getEmission(); Vector vector = ray.getDir(); Vector
	 * normal = geoPoint.geometry.getNormal(geoPoint.point); double nv =
	 * alignZero(normal.dotProduct(vector)); if (nv == 0) return color; Material
	 * material = geoPoint.geometry.getMaterial(); for (LightSource lightSource :
	 * scene.lights) { Vector lightVector = lightSource.getL(geoPoint.point); double
	 * nl = alignZero(normal.dotProduct(lightVector)); if (nl * nv > 0) { Double3
	 * ktr = transparency(geoPoint,lightSource, lightVector, normal);
	 * if(!(ktr.product(k).lowerThan(MIN_CALC_COLOR_K))) { Color lightIntensity =
	 * lightSource.getIntensity(geoPoint.point).scale(ktr); color =
	 * color.add(lightIntensity.scale(calcDiffusive(material, nl)),
	 * lightIntensity.scale(calcSpecular(material, normal, lightVector, nl,
	 * vector))); } } } return color; }
	 */

	private Color calcLocalEffects(GeoPoint geoPoint, Ray ray) {
		Color color = geoPoint.geometry.getEmission();
		Vector vector = ray.getDir();
		Vector normal = geoPoint.geometry.getNormal(geoPoint.point);
		double nv = alignZero(normal.dotProduct(vector));
		if (nv == 0)
			return color;
		Material material = geoPoint.geometry.getMaterial();
		for (LightSource lightSource : scene.lights) {
			Vector lightVector = lightSource.getL(geoPoint.point);
			double nl = alignZero(normal.dotProduct(lightVector));
			if (nl * nv > 0) {
				if (unshaded(geoPoint, lightVector, normal, lightSource)) {
					Color lightIntensity = lightSource.getIntensity(geoPoint.point);
					color = color.add(lightIntensity.scale(calcDiffusive(material, nl)),
							lightIntensity.scale(calcSpecular(material, normal, lightVector, nl, vector)));
				}
			}
		}
		return color;
	}

	/**
	 * Calculates the specular color at a point on a geometry.
	 * 
	 * @param material    the material of the geometry
	 * @param normal      the normal of the geometry
	 * @param lightVector the light vector
	 * @param nl          the dot product of the normal and light vector
	 * @param vector      the direction of the ray
	 * @return the specular color at the given point
	 */
	private Double3 calcSpecular(Material material, Vector normal, Vector lightVector, double nl, Vector vector) {
		Vector reflectedVector = lightVector.subtract(normal.scale(2 * nl));
		double minusVR = alignZero(-vector.dotProduct(reflectedVector));
		return minusVR <= 0 ? Double3.ZERO : material.kS.scale(Math.pow(minusVR, material.nShininess));
	}

	/**
	 * Calculates the diffusive color at a point on a geometry.
	 * 
	 * @param material the material of the geometry
	 * @param nl       the dot product of the normal and light vector
	 * @return the diffusive color at the given point
	 */
	private Double3 calcDiffusive(Material material, double nl) {
		return material.kD.scale(nl < 0 ? -nl : nl);
	}

	/**
	 * function will check if point is unshaded
	 *
	 * @param gp geometry point to check
	 * @param l  light vector
	 * @param n  normal vector
	 * @return true if unshaded
	 */
	private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource lightSource) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(gp.point, lightDirection, n);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);

		if (intersections == null)
			return true;
		double distance = lightSource.getDistance(gp.point);
		for (GeoPoint intersection : intersections) {
			if (alignZero(intersection.point.distance(gp.point)) <= distance
					&& intersection.geometry.getMaterial().kT.equals(Double3.ZERO))
				return false;
		}
		return true;
	}
	/*
	 * private Double3 transparency(GeoPoint gp, LightSource ls, Vector l, Vector n)
	 * { // #TODO Vector lightDirection = l.scale(-1); // from point to light source
	 * Ray lightRay = new Ray(gp.point, lightDirection, n); List<GeoPoint>
	 * intersections = scene.geometries.findGeoIntersections(lightRay);
	 * 
	 * if (intersections == null) return Double3.ONE; Double3 ktr = Double3.ONE;
	 * 
	 * double distance = ls.getDistance(gp.point); for (GeoPoint intersection :
	 * intersections) { if (alignZero(intersection.point.distance(gp.point)) <=
	 * distance) ktr=ktr.product(intersection.geometry.getMaterial().kT); if
	 * (ktr.lowerThan(MIN_CALC_COLOR_K)) return new Double3(0.0); else return ktr;
	 * 
	 * 
	 * } return ktr; }
	 */

}