/**
 * 
 */
package lighting;

import primitives.Color;

/**
 * Abstract class representing a light source in a 3D scene.
 *
 * @author Eti and Chavi
 */

abstract class Light {
	private Color intensity;

	protected Light(Color intensity) {
		this.intensity = intensity;
	}

	/**
	 * @return the intensity
	 */
	public Color getIntensity() {
		return intensity;
	}
}
