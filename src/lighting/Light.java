package lighting;

import primitives.Color;

/**
 * Abstract class representing a light source in a 3D scene.
 * 
 * @author Eti and Chavi
 */
abstract class Light {
	/** Represents the intensity of a light source. */
	protected final Color intensity;

	/**
	 * Constructs a Light object with the given intensity.
	 *
	 * @param intensity the intensity of the light
	 */
	protected Light(Color intensity) {
		this.intensity = intensity;
	}

	/**
	 * Returns the intensity of the light.
	 *
	 * @return the intensity of the light
	 */
	public Color getIntensity() {
		return intensity;
	}
}