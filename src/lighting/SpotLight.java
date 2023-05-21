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
public class SpotLight extends PointLight {
	private Vector direction;
	private double narrowBeam = 1;// from 4253

	/**
	 * @param intensity
	 * @param position
	 * @param direction
	 */
	public SpotLight(Color intensity, Point position, Vector direction) {
		super(intensity, position);
		this.direction = direction.normalize();
	}

	// from 4253
	/**
	 * setter for narrowBeam
	 *
	 * @param _narrowBeam the new value for narrowBeam
	 * @return this light
	 */
	public SpotLight setNarrowBeam(double narrowBeam) {
		this.narrowBeam = narrowBeam;
		return this;
	}

	// from 4253
	@Override
	public Color getIntensity(Point point) {
		// check if it is flashlight
		if (narrowBeam != 1) {
			return super.getIntensity(point)
					.scale(Math.pow(Math.max(0, direction.dotProduct(getL(point))), narrowBeam));
		}
		return super.getIntensity(point).scale(Math.max(0, direction.dotProduct(getL(point))));
	}

}
