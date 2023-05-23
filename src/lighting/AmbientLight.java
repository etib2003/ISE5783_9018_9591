package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * The AmbientLight class represents an ambient light source in a 3D scene.
 * 
 * It extends the Light class and provides methods for creating and manipulating
 * ambient light sources. Ambient light is a type of light that illuminates all
 * objects in a scene uniformly, without any specific direction or position.
 * 
 * @author Eti and Chavi
 */
public class AmbientLight extends Light {

	/** The constant representing no ambient light */
	public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

	/**
	 * Constructs an ambient light object with a given intensity and ambient
	 * coefficient. The intensity of the light is calculated by scaling the ambient
	 * coefficient with the given color.
	 *
	 * @param iA The color of the light source.
	 * @param kA The ambient coefficient of the light source (Double3).
	 */
	public AmbientLight(Color iA, Double3 kA) {
		super(iA.scale(kA));
	}

	/**
	 * Constructs an ambient light object with the given ambient coefficient. The
	 * intensity of the light is calculated by scaling the ambient coefficient with
	 * the default color BLACK.
	 *
	 * @param iA The color of the light source.
	 * @param kA The ambient coefficient of the light source.
	 */
	public AmbientLight(Color iA, double kA) {
		super(iA.scale(kA));
	}
}
