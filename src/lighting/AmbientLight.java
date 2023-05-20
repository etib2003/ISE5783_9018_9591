/**
 * 
 */
package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * The AmbientLight class represents an ambient light source in a 3D scene.
 * 
 * @author Eti and Chavi
 *
 */
public class AmbientLight {
	/**
	 * no ambient light
	 */
	public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

	/**
	 * ambient light intensity
	 */
	private final Color intensity;

	/**
	 * Constructs an ambient light object with a given intensity and ambient
	 * coefficient. The intensity of the light is calculated by scaling the ambient
	 * coefficient with the given color.
	 * 
	 * @param iA the color of the light source
	 * @param kA (Double3) the ambient coefficient of the light source
	 */
	public AmbientLight(Color iA, Double3 kA) {
		this.intensity = iA.scale(kA);
	}

	/**
	 * Constructs an ambient light object with the given ambient coefficient. The
	 * intensity of the light is calculated by scaling the ambient coefficient with
	 * the default color BLACK.
	 * 
	 * @param iA the color of the light source
	 * @param kA the ambient coefficient of the light source
	 */
	public AmbientLight(Color iA, double kA) {
		this.intensity = iA.scale(kA);
	}

	/**
	 * get intensity of the ambient light.
	 * 
	 * @return the intensity of the ambient light
	 */
	public Color getIntensity() {
		return intensity;
	}

}
