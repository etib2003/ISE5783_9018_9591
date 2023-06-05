package unittests.renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
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

	@Test
	public void effectTests() {
		Scene scene = new Scene("Custom2 Image Test");

		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPSize(200, 200)
				.setVPDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

		// Define the triangle
		Triangle triangle = (Triangle) new Triangle(new Point(-75, -75, -150), new Point(0, 75, -150),
				new Point(75, -75, -150)).setEmission(new Color(255, 255, 0))
				.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60));

		// Define the spheres
		Sphere sphere1 = (Sphere) new Sphere(new Point(-100, -100, -150), 50).setEmission(new Color(255, 0, 0))
				.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60));
		Sphere sphere2 = (Sphere) new Sphere(new Point(100, -100, -150), 50).setEmission(new Color(0, 255, 0))
				.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60));
		Sphere sphere3 = (Sphere) new Sphere(new Point(0, 100, -150), 50).setEmission(new Color(0, 0, 255))
				.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60));

		Plane plane1 = (Plane) new Plane(new Point(-75, -75, -150), new Point(0, 75, -150), new Point(100, -100, -150))
				.setEmission(new Color(0, 0, 0)).setMaterial(new Material().setkR(1));

		scene.geometries.add(/*triangle,*/ sphere1, sphere2, /*sphere3,*/ plane1);

		
		
		scene.geometries.add( //
				new Sphere(new Point(0, 0, -50), 80d).setEmission(new Color(255, 50, 0)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setkT(0.3)),
				new Sphere(new Point(0, 0, -50), 40d).setEmission(new Color(0, 255, 25)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
		
		
		// Define the light source
		//scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)).setkL(4E-5).setkQ(2E-7));

		scene.lights.add( //
				new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
						.setkL(0.0004).setkQ(0.0000006));
		/*
		 * // Create the shadowing elements scene.addGeometries( // Shadow plane for
		 * sphere1 new Plane(new Point(-75, -75, -150), new Point(0, 75, -150), new
		 * Point(-100, -100, -150)) .setEmission(new Color(0, 0, 0)) .setMaterial(new
		 * Material().setkR(1)) // Shadow plane for sphere2 new Plane(triangle.getP2(),
		 * triangle.getP3(), sphere2.getCenter()) .setEmission(new Color(0, 0, 0))
		 * .setMaterial(new Material().setKr(1)), // Shadow plane for sphere3 new
		 * Plane(triangle.getP3(), triangle.getP1(), sphere3.getCenter())
		 * .setEmission(new Color(0, 0, 0)) .setMaterial(new Material().setKr(1)) );
		 */

		ImageWriter imageWriter = new ImageWriter("custom2_image_with_shadows", 600, 600);
		camera.setImageWriter(imageWriter).setRayTracer(new RayTracerBasic(scene)).renderImage().writeToImage();
	}

	@Test
	public void effectsTests() {
		Camera camera = new Camera(new Point(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0));
		camera.setVPDistance(1000).setVPSize(200, 200);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), new Double3(0.15)));
		scene.geometries.add( //
				new Sphere(new Point(50, 50, 30), 10).setEmission(new Color(java.awt.Color.red))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30).setkT(0).setkR(0.2)),
				new Sphere(new Point(50, 50, 30), 10).setEmission(new Color(java.awt.Color.BLACK))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(40)),
				new Sphere(new Point(0, -10, 50), 10).setEmission(new Color(java.awt.Color.CYAN))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
				new Sphere(new Point(50, -10, 30), 30).setEmission(new Color(java.awt.Color.BLUE))
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setkT(0.6).setkR(0)),
				new Sphere(new Point(-20, 50, 45), 10).setEmission(new Color(java.awt.Color.RED))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
				new Sphere(new Point(-20, 50, 45), 20).setEmission(new Color(java.awt.Color.BLUE))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setkT(0.3)),
				new Sphere(new Point(0, 15, 30), 10).setEmission(new Color(java.awt.Color.ORANGE))
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setkT(0.6).setkR(0)),
				new Sphere(new Point(-50, -20, 30), 20).setEmission(new Color(java.awt.Color.MAGENTA))
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setkT(0).setkR(0.5))

		);

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(40, -40, -160), new Vector(-1, 1, 4))
				.setkC(1).setkL(4E-4).setkQ(2E-5));
		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(300, 30, 0), new Vector(-2, 3, 3)).setkC(1)
				.setkL(4E-4).setkQ(2E-5));
		camera.setImageWriter(new ImageWriter("ballsInTheAir", 600, 600)).setRayTracer(new RayTracerBasic(scene));
		camera.renderImage(); //
		camera.writeToImage();

	}

}
