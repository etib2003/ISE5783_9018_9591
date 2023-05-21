package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

//תועד
/**
 * Represents a spot light source in a lighting system.
 *
 * @author Eti and Chavi
 *
 */
public class SpotLight extends PointLight {
	/**
	 * Represents the direction of the spot light source.
	 */
	private Vector direction;
	/**
	 * Represents the narrowness of the light beam emitted by the spot light source.
	 * A value of 1 represents a wide beam, while values less than 1 create a
	 * narrower beam.
	 */
	private double narrowBeam = 1;// from 4253

	/**
	 * Creates a new SpotLight with the specified intensity, position, and
	 * direction.
	 * 
	 * @param intensity the color and intensity of the light
	 * @param position  the position of the light source
	 * @param direction the direction in which the light is emitted
	 */
	public SpotLight(Color intensity, Point position, Vector direction) {
		super(intensity, position);
		this.direction = direction.normalize();
	}

	// from 4253
	/**
	 * Sets the narrowness of the light beam. A value of 1 represents a wide beam,
	 * while values less than 1 create a narrower beam.
	 * 
	 * @param narrowBeam the narrowness of the light beam
	 * @return the SpotLight object for method chaining
	 */
	public SpotLight setNarrowBeam(double narrowBeam) {
		this.narrowBeam = narrowBeam;
		return this;
	}

	// from 4253
	/**
	 * Calculates and returns the intensity of the light at the specified point. The
	 * intensity is attenuated based on the distance and the angle between the light
	 * direction and the surface normal.
	 * 
	 * @param point the point at which to calculate the intensity
	 * @return the attenuated intensity at the point
	 */
	@Override
	public Color getIntensity(Point point) {
		// check if it is flashlight
		return narrowBeam != 1
				? super.getIntensity(point).scale(Math.pow(Math.max(0, direction.dotProduct(getL(point))), narrowBeam))
				: super.getIntensity(point).scale(Math.max(0, direction.dotProduct(getL(point))));

	}

}
