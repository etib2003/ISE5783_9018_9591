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
//תועד

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
		return calcColor(closestPoint, ray);
	}

	/**
	 * Calculates the color of a given point in the scene.
	 *
	 * @param geoPoint the point to calculate the color for
	 * @param ray      the ray that intersects the point
	 * @return the color at the given point
	 */
	private Color calcColor(GeoPoint geoPoint, Ray ray) {
		return geoPoint.geometry.getEmission().add(scene.ambientLight.getIntensity(), calcLocalEffects(geoPoint, ray));
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
				Color lightIntensity = lightSource.getIntensity(gp.point);
				color = color.add(lightIntensity.scale(calcDiffusive(material, nl)),
						lightIntensity.scale(calcSpecular(material, normal, lightVector, nl, vector)));
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
		double max = Math.max(0, vector.scale(-1).dotProduct(reflectedVector));
		return material.kS.scale(Math.pow(max, material.nShininess));

	}

	/**
	 * Calculates the diffusive color at a point on a geometry.
	 * 
	 * @param material the material of the geometry
	 * @param nl       the dot product of the normal and light vector
	 * @return the diffusive color at the given point
	 */
	private Double3 calcDiffusive(Material material, double nl) {
		return material.kD.scale(Math.abs(nl));
	}

}
