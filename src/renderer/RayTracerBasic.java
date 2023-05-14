/**
 * 
 */
package renderer;

import java.util.List;

import primitives.Color;
import primitives.Ray;
import primitives.Point;

import scene.Scene;

/**
 * @author אתוש
 *
 */
public class RayTracerBasic extends RayTracerBase {

	public RayTracerBasic(Scene scene) {
		super(scene);
	}

	@Override
    public Color traceRay(Ray ray) {
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if (intersections == null) {
            return scene.background;
        }
        Point closestPoint = ray.findClosestPoint(intersections);
        return calcColor(closestPoint);
    }

	private Color calcColor(Point point) {
		return scene.ambientLight.getIntensity();
	}

}
