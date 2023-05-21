/**
 * 
 */
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
     * function calculates color of point
     *
     * @param geoPoint point to color
     * @return color
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return geoPoint.geometry.getEmission().add(scene.ambientLight.getIntensity(), calcLocalEffects(geoPoint, ray));
    }


	 /**
     * function calculates local effects of color on point
     * @param gp geometry point to color
     * @param ray ray that intersects
     * @return color
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
                color = color.add(lightIntensity.scale(calcDiffusive(material, nl)), lightIntensity.scale(calcSpecular(material, normal, lightVector, nl, vector)));
            }
        }
        return color;
    }

    /**
     * function calculates specular color
     * @param material material of geometry
     * @param normal normal of geometry
     * @param lightVector light vector
     * @param nl dot product of normal and light vector
     * @param vector direction of ray
     * @return specular color
     */
    private Double3 calcSpecular(Material material, Vector normal, Vector lightVector, double nl, Vector vector) {
        Vector reflectedVector = lightVector.subtract(normal.scale(2 * nl));
        double max = Math.max(0, vector.scale(-1).dotProduct(reflectedVector));
        return material.kS.scale(Math.pow(max, material.nShininess));

    }

    /**
     * function calculates diffusive color
     * @param material material of geometry
     * @param nl dot product of normal and light vector
     * @return diffusive color
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }

   
}
