/**
 * 
 */
package scene;

import primitives.Color;
import lighting.AmbientLight;
import geometries.Geometries;

/**
 * The Scene class represents a 3D scene that includes a name, background color,
 * ambient light, and geometries. The class provides methods to set and get the
 * background color, ambient light, and geometries.
 * 
 * @author אתוש
 */
public class Scene {
	/** The name of the scene. */
	public String name;
	/** The background color of the scene. */
	public Color background = Color.BLACK;
	/** The ambient light of the scene. */
	public AmbientLight ambientLight = AmbientLight.NONE;
	/** The geometries of the scene. */
	public Geometries geometries = new Geometries();

	/**
	 * Constructs a new scene with the given name.
	 *
	 * @param name the name of the scene
	 */
	public Scene(String name) {
		this.name = name;
	}

	/**
	 * Sets the background color of the scene.
	 *
	 * @param background the background color to set
	 * @return this scene
	 */
	public Scene setBackground(Color background) {
		this.background = background;
		return this;
	}

	/**
	 * Sets the ambient light of the scene.
	 *
	 * @param ambientLight the ambient light to set
	 * @return this scene
	 */
	public Scene setAmbientLight(AmbientLight ambientLight) {
		this.ambientLight = ambientLight;
		return this;
	}

	/**
	 * Sets the geometries of the scene.
	 *
	 * @param geometries the geometries to set
	 * @return this scene
	 */
	public Scene setGeometries(Geometries geometries) {
		this.geometries = geometries;
		return this;
	}
}
