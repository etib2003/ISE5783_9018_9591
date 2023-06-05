package unittests.renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.PointLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author dzilb
 */
public class ReflectionRefractionTests {
	private Scene scene = new Scene("Test scene");

	/** Produce a picture of a sphere lighted by a spot light */
	@Test
	public void twoSpheres() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(150, 150).setVPDistance(1000);

		scene.geometries.add( //
				new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setkT(0.3)),
				new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
		scene.lights.add( //
				new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
						.setkL(0.0004).setkQ(0.0000006));

		camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

	/** Produce a picture of a sphere lighted by a spot light */
	@Test
	public void twoSpheresOnMirrors() {
		Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(2500, 2500).setVPDistance(10000); //

		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		scene.geometries.add( //
				new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100)) //
						.setMaterial(
								new Material().setKd(0.25).setKs(0.25).setShininess(20).setkT(new Double3(0.5, 0, 0))),
				new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), new Point(670, 670, 3000)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setkR(1)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
						new Point(-1500, -1500, -2000)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setkR(new Double3(0.5, 0, 0.4))));

		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
				.setkL(0.00001).setkQ(0.000005));

		ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(200, 200).setVPDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

		scene.geometries.add( //
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setkT(0.6)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
				.setkL(4E-5).setkQ(2E-7));

		ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}


	/**
	 * Produce a picture of a pyramid lighted by a spot light with a mirror effects
	 */
	@Test
	public void pyramidTransparentSphere() {
		Camera camera = new Camera(new Point(-140, 20, 35), new Vector(1, -0.15, -0.25), new Vector(1, 0, 4))
				.setVPSize(200, 200).setVPDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

		scene.geometries.add( //
				new Plane(new Point(10, 0, -30), new Point(0, 10, -30), new Point(-10, 0, -30))
						.setEmission(new Color(160, 0, 160))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setkT(0).setkR(1)),
				new Polygon(new Point(10, 0, -10), new Point(0, 10, -10), new Point(-10, 0, -10),
						new Point(0, -10, -10))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setkT(0.2).setkR(0)),
				new Polygon(new Point(10, 0, -10), new Point(0, 10, -10), new Point(0, 10, 0), new Point(10, 0, 0))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setkT(0.4)),
				new Polygon(new Point(10, 0, -10), new Point(0, -10, -10), new Point(0, -10, 0), new Point(10, 0, 0))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setkT(0).setkR(1)),
				new Polygon(new Point(-10, 0, -10), new Point(0, 10, -10), new Point(0, 10, 0), new Point(-10, 0, 0))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setkT(0.8)),
				new Polygon(new Point(-10, 0, -10), new Point(0, -10, -10), new Point(0, -10, 0), new Point(-10, 0, 0))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setkT(0.4)),
				new Polygon(new Point(10, 0, 0), new Point(0, -10, 0), new Point(-10, 0, 0), new Point(0, 10, 0))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setkT(0.5)),
				new Triangle(new Point(10, 0, 0), new Point(0, -10, 0), new Point(0, 0, 10)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(60).setkT(0.5).setkR(0)), //
				new Triangle(new Point(10, 0, 0), new Point(0, 10, 0), new Point(0, 0, 10)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(60).setkT(1)), //
				new Triangle(new Point(-10, 0, 0), new Point(0, 10, 0), new Point(0, 0, 10)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(60).setkT(0.8)), //
				new Triangle(new Point(-10, 0, 0), new Point(0, -10, 0), new Point(0, 0, 10)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(60).setkT(1)), //
				new Sphere(new Point(0, 0, 3), 2) //
						.setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setkT(0.6)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(30, 30, 100), new Vector(0, 0, -1)) //
				.setkL(4E-5).setkQ(2E-7));

		ImageWriter imageWriter = new ImageWriter("refractionShadowPyramid", 600, 600);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage(); //
		camera.writeToImage();
	}
}
