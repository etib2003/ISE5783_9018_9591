/**
 * 
 */
package unittests.special;

import static java.awt.Color.BLUE;
import static java.awt.Color.GREEN;

import org.junit.jupiter.api.Test;

import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.Color;
import primitives.Double3;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

/**
 * @author אתוש
 *
 */
class pyramidTransparentSphereTest {
	private Scene scene = new Scene("Test scene");

	/**
	 * Produce a picture of a pyramid lighted by a spot light with a mirror effects
	 */
	@Test
	public void pyramidTransparentSphere() {
		Camera camera = new Camera(new Point(-140, 20, 35), new Vector(1, -0.15, -0.25), new Vector(1, 0, 4))
				.setVPSize(200, 200).setVPDistance(1000);//.setNumPoints(25).setFocalPlaneDistance(140).setApertureSize(1);

		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.15)));

		scene.geometries.add(
				new Plane(new Point(10, 0, -30), new Point(0, 10, -30), new Point(-10, 0, -30))
						.setEmission(new Color(BLUE))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setkT(0).setkR(1)),
				new Polygon(new Point(10, 0, -5), new Point(0, 10, -5), new Point(-10, 0, -5), new Point(0, -10, -5))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setkT(0.2).setkR(0)),
				new Polygon(new Point(10, 0, -5), new Point(0, 10, -5), new Point(0, 10, 0), new Point(10, 0, 0))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setkT(0.4)),
				new Polygon(new Point(10, 0, -5), new Point(0, -10, -5), new Point(0, -10, 0), new Point(10, 0, 0))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setkT(0).setkR(1)),
				new Polygon(new Point(-10, 0, -5), new Point(0, 10, -5), new Point(0, 10, 0), new Point(-10, 0, 0))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setkT(0.8)),
				new Polygon(new Point(-10, 0, -5), new Point(0, -10, -5), new Point(0, -10, 0), new Point(-10, 0, 0))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setkT(0.4)),
				new Polygon(new Point(10, 0, 0), new Point(0, -10, 0), new Point(-10, 0, 0), new Point(0, 10, 0))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setkT(0.5)),
				new Triangle(new Point(10, 0, 0), new Point(0, -10, 0), new Point(0, 0, 10))
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(60).setkT(0.5).setkR(0)),
				new Triangle(new Point(10, 0, 0), new Point(0, 10, 0), new Point(0, 0, 10))
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(60).setkT(1)),
				new Triangle(new Point(-10, 0, 0), new Point(0, 10, 0), new Point(0, 0, 10))
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(60).setkT(0.8)),
				new Triangle(new Point(-10, 0, 0), new Point(0, -10, 0), new Point(0, 0, 10))
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(60).setkT(1)),
				new Sphere(new Point(0, 0, 3), 2).setEmission(new Color(254, 41, 77))
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setkT(0.8)),
				new Sphere(new Point(1.5, 0, 2), 1).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setkT(0.6)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(30, 30, 100), new Vector(0, 0, -1))
				.setkL(4E-5).setkQ(2E-7));

		ImageWriter imageWriter = new ImageWriter("refractionShadowPyramid", 600, 600);
		camera.setImageWriter(imageWriter).setRayTracer(new RayTracerBasic(scene)).renderImage();
		camera.writeToImage();
	}


}
