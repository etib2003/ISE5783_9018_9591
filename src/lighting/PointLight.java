package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a point light source in a lighting system. It extends the Light
 * class and implements the LightSource interface.
 * 
 * @author Eti and Chavi
 *
 */
public class PointLight extends Light implements LightSource {
	/** Represents the position of the point light source. */
	private final Point position;
	/** Represents the constant attenuation factor of the point light source. */
	private double kC = 1;
	/** Represents the linear attenuation factor of the point light source. */
	private double kL = 0;
	/** Represents the quadratic attenuation factor of the point light source. */
	private double kQ = 0;

	/**
	 * Creates a new PointLight with the specified intensity and position.
	 * 
	 * @param intensity the color and intensity of the light
	 * @param position  the position of the light source
	 */
	public PointLight(Color intensity, Point position) {
		super(intensity);
		this.position = position;
	}

	/**
	 * Sets the constant attenuation factor of the light.
	 * 
	 * @param kC the constant attenuation factor to set
	 * @return the PointLight object for method chaining
	 */
	public PointLight setkC(double kC) {
		this.kC = kC;
		return this;
	}

	/**
	 * Sets the linear attenuation factor of the light.
	 * 
	 * @param kL the linear attenuation factor to set
	 * @return the PointLight object for method chaining
	 */
	public PointLight setkL(double kL) {
		this.kL = kL;
		return this;

	}

	/**
	 * Sets the quadratic attenuation factor of the light.
	 * 
	 * @param kQ the quadratic attenuation factor to set
	 * @return the PointLight object for method chaining
	 */
	public PointLight setkQ(double kQ) {
		this.kQ = kQ;
		return this;

	}

	/**
	 * Calculates and returns the intensity of the light at the specified point. The
	 * intensity is attenuated based on the distance from the light source.
	 * 
	 * @param point the point at which to calculate the intensity
	 * @return the attenuated intensity at the point
	 */
	@Override
	public Color getIntensity(Point point) {
		double d2 = position.distanceSquared(point);
		return intensity.reduce(kC + kL * Math.sqrt(d2) + kQ * d2);
	}

	/**
	 * Calculates and returns the direction of the light from the specified point.
	 * 
	 * @param point the point from which to calculate the light direction
	 * @return the normalized direction vector from the light source to the point
	 */
	@Override
	public Vector getL(Point point) {
		return point.subtract(position).normalize();
	}

	/**
	 * Returns the distance from the light source to a given point.
	 *
	 * @param point The point for which to calculate the distance.
	 * @return The distance from the light source to the point.
	 */
	@Override
	public double getDistance(Point point) {
		return point.distance(position);
	}
}
