/**
 * 
 */
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The LightSource interface represents a light source in a scene.
 * 
 * @author Eti and Chavi
 *
 */
public interface LightSource {
	public Color getIntensity(Point p);

	public Vector getL(Point p);
}
