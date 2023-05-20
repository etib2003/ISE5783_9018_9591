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
public class AmbientLight extends Light {
	/**
	 * no ambient light
	 */
	public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

	/**
	 * Constructs an ambient light object with a given intensity and ambient
	 * coefficient. The intensity of the light is calculated by scaling the ambient
	 * coefficient with the given color.
	 * 
	 * @param iA the color of the light source
	 * @param kA (Double3) the ambient coefficient of the light source
	 */
	public AmbientLight(Color iA, Double3 kA) {
		super(iA.scale(kA));
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
		super(iA.scale(kA));
	}
}
