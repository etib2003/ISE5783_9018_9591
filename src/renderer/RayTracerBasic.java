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
import primitives.Point;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;;

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

	private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
		Color color = calcLocalEffects(geoPoint, ray);
		return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray, level, k));
	}

	private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
		//Color color = Color.BLACK;
		Vector v = ray.getDir();
		Vector n = gp.geometry.getNormal(gp.point);
		Material material = gp.geometry.getMaterial();
		return calcGlobalEffect(constructReflectedRay(gp, v, n), level, k, material.kR)
				.add(calcGlobalEffect(constructRefractedRay(gp, v, n), level, k, material.kT));
	}

	private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
		Double3 kkx = k.product(kx);
		if (kkx.lowerThan(MIN_CALC_COLOR_K))
			return Color.BLACK;
		GeoPoint gp = findClosestIntersection(ray);
		if (gp == null)
			return scene.background.scale(kx);
		return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir())) ? Color.BLACK
				: calcColor(gp, ray, level - 1, kkx);
	}
	
	private Ray constructReflectedRay(GeoPoint gp,Vector v,Vector n)
	{//#TODO
		return null;
	}
	
	private Ray constructRefractedRay(GeoPoint gp,Vector v,Vector n)
	{//#TODO
		return null;
	}

	/**
	 * Calculates the local effects of color at a point in the scene.
	 * 
	 * @param gp  the geometry point to calculate color for
	 * @param ray the ray that intersects the point
	 * @return the color at the given point, accounting for local effects
	 */
	private Color calcLocalEffects(GeoPoint gp, Ray ray) {
		Color color = Color.BLACK;
		Vector vector = ray.getDir();
		Vector normal = gp.geometry.getNormal(gp.point);
		double nv = alignZero(normal.dotProduct(vector));
		if (nv == 0)
			return color;
		Material material = gp.geometry.getMaterial();
		for (LightSource lightSource : scene.lights) {
			Vector lightVector = lightSource.getL(gp.point);
			double nl = alignZero(normal.dotProduct(lightVector));
			if (nl * nv > 0) {
				if (unshaded(gp, lightVector, normal, lightSource, nv)) {
					Color lightIntensity = lightSource.getIntensity(gp.point);
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
	private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource lightSource, double nv) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay= new Ray(gp.point, lightDirection, n);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);

		if (intersections != null) {
			double distance = lightSource.getDistance(gp.point);
			for (GeoPoint intersection : intersections) {
				if (intersection.point.distance(gp.point) < distance)
					return false;
			}
		}

		return true;
	}

}