/**
 * 
 */
package renderer;

import java.util.List;
import geometries.Intersectable.GeoPoint;

import primitives.Color;
import primitives.Ray;

import scene.Scene;

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
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
		if (intersections == null) {
			return scene.background;
		}
		GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
		return calcColor(closestPoint);
	}

	/**
	 * Calculates the color at a given point in the scene.
	 * 
	 * @param point the point for which to calculate the color.
	 * @return the color at the given point in the scene.
	 */
	private Color calcColor(GeoPoint geoPoint) {
		return scene.ambientLight.getIntensity().add(geoPoint.geometry.getEmission());
	} /////////////////// לטפל בזה

	/*
	 * private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
	 * 
	 * Vector v = ray.getDir(); Vector n =
	 * intersection.geometry.getNormal(intersection.point); double nv =
	 * alignZero(n.dotProduct(v)); if (nv == 0) return Color.BLACK;
	 * 
	 * Material material = intersection.geometry.getMaterial(); int nShininess =
	 * material.getnShininess(); double kd = material.getkD(), ks =
	 * material.getkS(); //Color color = Color.BLACK; Color color =
	 * intersection.geometry.getEmission(); for (LightSource lightSource :
	 * scene.lights) { Vector l = lightSource.getL(intersection.point); double nl =
	 * alignZero(n.dotProduct(l)); if (nl * nv > 0) { // sign(nl) == sing(nv) Color
	 * lightIntensity = lightSource.getIntensity(intersection.point); color =
	 * color.add(calcDiffusive(kd, l, n, lightIntensity), calcSpecular(ks, l, n, v,
	 * nShininess, lightIntensity)); } } return color; }
	 */

}
