/**
 * 
 */
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * @author Eti and Chavi
 *
 */
public class DirectionalLight extends Light implements LightSource {
	private Vector direction;

	/**
	 * @param intensity
	 * @param direction
	 */
	public DirectionalLight(Color intensity, Vector direction) {
		super(intensity);
		this.direction = direction.normalize();
	}

	@Override
	public Color getIntensity(Point point) {
		return getIntensity();
	}

	@Override
	public Vector getL(Point point) {
		return direction;
	}
}
