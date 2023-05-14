/**
 * 
 */
package scene;

import primitives.Color;
import lighting.AmbientLight;
import geometries.Geometries;

/**
 * @author אתוש
 *
 */
public class Scene {
	public String name;
	public Color background = Color.BLACK;
	public AmbientLight ambientLight = AmbientLight.NONE;
	public Geometries geometries = new Geometries();

	public Scene(String name) {
		this.name = name;
	}

	/**
	 * @param background the background to set
	 */
	public Scene setBackground(Color background) {
		this.background = background;
		return this;
	}

	/**
	 * @param ambientLight the ambientLight to set
	 */
	public Scene setAmbientLight(AmbientLight ambientLight) {
		this.ambientLight = ambientLight;
		return this;

	}

	/**
	 * @param geometries the geometries to set
	 */
	public Scene setGeometries(Geometries geometries) {
		this.geometries = geometries;
		return this;

	}

}
