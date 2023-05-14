/**
 * 
 */
package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * The AmbientLight class represents an ambient light source in a 3D scene.
 * @author אתוש
 *
 */
public class AmbientLight {	
	/*
	 * no ambient light
	 */
	public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);
	
	/*
	 * ambient light intensity
	 */
	private Color intensity;

	/**
	 * Constructs an ambient light object with a given intensity and ambient coefficient.
	 * The intensity of the light is calculated by scaling the ambient coefficient with the given color.
	 * 
	 * @param Ia the color of the light source
	 * @param Ka(Double3) the ambient coefficient of the light source
	 */
	public AmbientLight(Color Ia, Double3 Ka) {
		this.intensity = Ia.scale(Ka);
	}

	/**
	 * Constructs an ambient light object with the given ambient coefficient.
	 * The intensity of the light is calculated by scaling the ambient coefficient with the default color BLACK.
	 @param Ka the ambient coefficient of the light source
	 */
	public AmbientLight(double Ka) {
		this.intensity = intensity.scale(Ka);
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
