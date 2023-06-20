package unittests.renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.Geometry;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.LightSource;
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

	@Test
	public void createClownImage3() {
		Camera camera = new Camera(new Point(0, 0, -40), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPSize(800, 800)
				.setVPDistance(1000).setDoFActive(true).setFocalLength(55).setApertureRadius(1);
		//.setNumPoints(50).setFocalPlaneDistance(55).setApertureSize(1);

		Material spMaterial = new Material().setKd(0.6).setKs(0.9).setShininess(3000).setkT(0.0).setkR(0.0);
		Material spMaterial1 = new Material().setKd(0.2).setKs(0.6).setShininess(60);
		Material trMaterial1 = new Material().setKd(0.0).setKd(0.0).setShininess(1000).setkT(0);
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.15)));

		// Head (Sphere)
		Sphere head = (Sphere) new Sphere(new Point(0, 0, -100), 4d).setEmission(new Color(244, 186, 126))
				.setMaterial(spMaterial);
		scene.geometries.add(head);

		// Face
		Sphere leftEye = (Sphere) new Sphere(new Point(-1, 1.7, -96), 0.55d).setEmission(new Color(BLACK))
				.setMaterial(spMaterial);
		Sphere rightEye = (Sphere) new Sphere(new Point(1, 1.7, -96), 0.55d).setEmission(new Color(BLACK))
				.setMaterial(spMaterial);
		Sphere nose = (Sphere) new Sphere(new Point(0, 0.2, -96), 0.62d).setEmission(new Color(RED))
				.setMaterial(spMaterial);
		/*Sphere mouth = (Sphere) new Sphere(new Point(0, -1.4, -96), 0.72d).setEmission(new Color(BLUE))
				.setMaterial(spMaterial);//for pic4*/
		Triangle mouth = (Triangle) new Triangle(new Point(-1, -1, -96), new Point(1, -1, -96), new Point(0, -2.2, -96))
			    .setEmission(new Color(BLUE))
			    .setMaterial(spMaterial);//for pic3-5
		scene.geometries.add(leftEye, rightEye, nose, mouth);

		// Bow Tie (Triangles)
		Triangle bowTie1 = (Triangle) new Triangle(new Point(-2, -3, -94.5), new Point(0, -3.5, -94.5),
				new Point(-1.5, -5, -94.5)).setEmission(new Color(253, 142, 40)).setMaterial(trMaterial1);
		Triangle bowTie2 = (Triangle) new Triangle(new Point(2, -3, -94.5), new Point(0, -3.5, -94.5),
				new Point(1.5, -5, -94.5)).setEmission(new Color(253, 142, 40)).setMaterial(trMaterial1);
		scene.geometries.add(bowTie1, bowTie2);

		// Body (Sphere)
		Sphere body = (Sphere) new Sphere(new Point(0, -8, -100), 5.5d).setEmission(new Color(244, 186, 126))
				.setMaterial(spMaterial);
		scene.geometries.add(body);

		// Buttons (Spheres)
		Sphere button1 = (Sphere) new Sphere(new Point(0, -5, -94.5), 0.5d).setEmission(new Color(RED))
				.setMaterial(spMaterial);
		Sphere button2 = (Sphere) new Sphere(new Point(0, -7, -94.5), 0.5d).setEmission(new Color(253, 213, 40))
				.setMaterial(spMaterial);
		Sphere button3 = (Sphere) new Sphere(new Point(0, -9, -94.5), 0.5d).setEmission(new Color(GREEN))
				.setMaterial(spMaterial);
		scene.geometries.add(button1, button2, button3);

		// Hands (Triangles)
		Triangle leftHand = (Triangle) new Triangle(new Point(-6.5, -7.4, -98), new Point(-8, -3.5, -98),
				new Point(-2, -3.4, -98)).setEmission(new Color(250, 186, 126)).setMaterial(trMaterial1);

		Triangle rightHand = (Triangle) new Triangle(new Point(6.5, -7.4, -98), new Point(8, -3.5, -98),
				new Point(2, -3.4, -98)).setEmission(new Color(250, 186, 126)).setMaterial(trMaterial1);

		scene.geometries.add(leftHand, rightHand);

		// Legs (Triangles)
		Triangle leftLeg = (Triangle) new Triangle(new Point(-4, -13, -105), new Point(-2, -17, -105),
				new Point(0, -13, -105)).setEmission(new Color(250, 186, 126)).setMaterial(trMaterial1);

		Triangle rightLeg = (Triangle) new Triangle(new Point(4, -13, -105), new Point(2, -17, -105),
				new Point(0, -13, -105)).setEmission(new Color(250, 186, 126)).setMaterial(trMaterial1);

		scene.geometries.add(leftLeg, rightLeg);

		// Shoes (Rectangles)
		Polygon leftShoe = (Polygon) new Polygon(new Point(-3.5, -15, -100), new Point(-1.5, -15, -100),
				new Point(-1.5, -16.3, -100), new Point(-3.5, -16.3, -100)).setEmission(new Color(60, 60, 60));
		Polygon rightShoe = (Polygon) new Polygon(new Point(3.5, -15, -100), new Point(1.5, -15, -100),
				new Point(1.5, -16.3, -100), new Point(3.5, -16.3, -100)).setEmission(new Color(60, 60, 60));
		scene.geometries.add(leftShoe, rightShoe);

		// stage (Rectangles)
		Polygon stage = (Polygon) new Polygon(new Point(6, -18, -105), new Point(6, -21, -105),
				new Point(-6, -21, -105), new Point(-6, -18, -105)).setEmission(new Color(250, 186, 126)).setMaterial(trMaterial1);
   
		scene.geometries.add(stage);

		// Hat (Triangle)
		Triangle hat = (Triangle) new Triangle(new Point(-3, 2.5, -100), new Point(3, 2.5, -100), new Point(0, 6.5, -100))
				.setEmission(new Color(RED)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
				.setMaterial(trMaterial1);
		scene.geometries.add(hat);

		
		// Balls3 (Spheres)
		Sphere ball1 = (Sphere) new Sphere(new Point(-17, 6, -150), 4.5d).setEmission(new Color(255, 0, 0))
				.setMaterial(spMaterial1);
		Sphere ball2 = (Sphere) new Sphere(new Point(-11, 17, -150), 4.5d).setEmission(new Color(255, 150, 0))
				.setMaterial(spMaterial1);
		Sphere ball3 = (Sphere) new Sphere(new Point(11, 17, -150), 4.5d).setEmission(new Color(255, 255, 0))
				.setMaterial(spMaterial1);
		Sphere ball4 = (Sphere) new Sphere(new Point(-7.5, -3, -90), 1.95d).setEmission(new Color(0, 255, 0))
				.setMaterial(spMaterial1);
		Sphere ball5 = (Sphere) new Sphere(new Point(0, 23, -150), 4.5d).setEmission(new Color(0, 0, 255))
				.setMaterial(spMaterial1);
		
		/*
		// Balls4 (Spheres)
				Sphere ball1 = (Sphere) new Sphere(new Point(-17, 6, -150), 4.5d).setEmission(new Color(0, 255, 0))
						.setMaterial(spMaterial1);
				Sphere ball2 = (Sphere) new Sphere(new Point(-11, 17, -150), 4.5d).setEmission(new Color(255, 0, 0))
						.setMaterial(spMaterial1);
				Sphere ball3 = (Sphere) new Sphere(new Point(11, 17, -150), 4.5d).setEmission(new Color(0, 0, 255))
						.setMaterial(spMaterial1);
				Sphere ball4 = (Sphere) new Sphere(new Point(17, 6, -150), 4.5d).setEmission(new Color(255, 255, 0))
						.setMaterial(spMaterial1);
				Sphere ball5 = (Sphere) new Sphere(new Point(0, 23, -150), 4.5d).setEmission(new Color(255, 150, 0))
						.setMaterial(spMaterial1);
		*/
		
		/*
		// Balls5 (Spheres)
		Sphere ball1 = (Sphere) new Sphere(new Point(7.5, -3, -90), 1.95d).setEmission(new Color(255, 255, 0))//green(255, 255, 0)
				.setMaterial(spMaterial1);
		Sphere ball2 = (Sphere) new Sphere(new Point(-11, 17, -150), 4.5d).setEmission(new Color(0, 255, 0))//red(0, 255, 0)
				.setMaterial(spMaterial1);
		Sphere ball3 = (Sphere) new Sphere(new Point(11, 17, -150), 4.5d).setEmission(new Color(255, 150, 0))//blue(255, 150, 0)
				.setMaterial(spMaterial1);
		Sphere ball4 = (Sphere) new Sphere(new Point(17, 6, -150), 4.5d).setEmission(new Color(0, 0, 255))//yellow->(0, 0, 255)
				.setMaterial(spMaterial1);
		Sphere ball5 = (Sphere) new Sphere(new Point(0, 23, -150), 4.5d).setEmission(new Color(255, 0, 0))//orange255, 0, 0)
				.setMaterial(spMaterial1);
		*/
		
		scene.geometries.add(ball1, ball2, ball3, ball4, ball5);
				

		// Background plane defined by three points
		Point p1 = new Point(-1000, -1000, -150);
		Point p2 = new Point(1000, -1000, -350);
		Point p3 = new Point(1000, 1000, -350);
		Plane backgroundPlane = (Plane) new Plane(p1, p2, p3).setEmission(new Color(200, 153, 200)).
				setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setkT(0).setkR(0));
		scene.geometries.add(backgroundPlane);

		// Light source
		scene.lights.add(new SpotLight(new Color(700,400,400), new Point(0, 100, 0), new Vector(0, 0, -1))
				.setkL(0.000004).setkQ(0.00009));

		scene.lights.add(new DirectionalLight(new Color(150, 150, 50), new Vector(-50, -1, -1)));
		ImageWriter imageWriter = new ImageWriter("clownImage9", 800, 800);
		camera.setImageWriter(imageWriter).setRayTracer(new RayTracerBasic(scene)).renderImage().writeToImage();
	}

}
