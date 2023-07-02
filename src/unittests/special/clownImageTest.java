/**
 * 
 */
package unittests.special;

import static java.awt.Color.BLACK;
import static java.awt.Color.BLUE;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;

import org.junit.jupiter.api.Test;

import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionalLight;
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
class clownImageTest {
	private Scene scene = new Scene("Test scene");

	@Test
	public void createClownImage3() {
		Camera camera = new Camera(new Point(0, 0, -40), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPSize(800, 800)
				.setVPDistance(1000).setDoFActive(true).setFocalLength(55).setApertureRadius(1).setGridDensity(8);

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
		/*
		 * Sphere mouth = (Sphere) new Sphere(new Point(0, -1.4, -96),
		 * 0.72d).setEmission(new Color(BLUE)) .setMaterial(spMaterial);//for pic4
		 */
		Triangle mouth = (Triangle) new Triangle(new Point(-1, -1, -96), new Point(1, -1, -96), new Point(0, -2.2, -96))
				.setEmission(new Color(BLUE)).setMaterial(spMaterial);// for pic3-5
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
				new Point(-6, -21, -105), new Point(-6, -18, -105)).setEmission(new Color(250, 186, 126))
				.setMaterial(trMaterial1);

		scene.geometries.add(stage);

		// Hat (Triangle)
		Triangle hat = (Triangle) new Triangle(new Point(-3, 2.5, -100), new Point(3, 2.5, -100),
				new Point(0, 6.5, -100)).setEmission(new Color(RED))
				.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)).setMaterial(trMaterial1);
		scene.geometries.add(hat);

		/*
		 * // Balls3 (Spheres) Sphere ball1 = (Sphere) new Sphere(new Point(-17, 6,
		 * -150), 4.5d).setEmission(new Color(255, 0, 0)) .setMaterial(spMaterial1);
		 * Sphere ball2 = (Sphere) new Sphere(new Point(-11, 17, -150),
		 * 4.5d).setEmission(new Color(255, 150, 0)) .setMaterial(spMaterial1); Sphere
		 * ball3 = (Sphere) new Sphere(new Point(11, 17, -150), 4.5d).setEmission(new
		 * Color(255, 255, 0)) .setMaterial(spMaterial1); Sphere ball4 = (Sphere) new
		 * Sphere(new Point(-7.5, -3, -90), 1.95d).setEmission(new Color(0, 255, 0))
		 * .setMaterial(spMaterial1); Sphere ball5 = (Sphere) new Sphere(new Point(0,
		 * 23, -150), 4.5d).setEmission(new Color(0, 0, 255)) .setMaterial(spMaterial1);
		 */

		/*
		 * // Balls4 (Spheres) Sphere ball1 = (Sphere) new Sphere(new Point(-17, 6,
		 * -150), 4.5d).setEmission(new Color(0, 255, 0)) .setMaterial(spMaterial1);
		 * Sphere ball2 = (Sphere) new Sphere(new Point(-11, 17, -150),
		 * 4.5d).setEmission(new Color(255, 0, 0)) .setMaterial(spMaterial1); Sphere
		 * ball3 = (Sphere) new Sphere(new Point(11, 17, -150), 4.5d).setEmission(new
		 * Color(0, 0, 255)) .setMaterial(spMaterial1); Sphere ball4 = (Sphere) new
		 * Sphere(new Point(17, 6, -150), 4.5d).setEmission(new Color(255, 255, 0))
		 * .setMaterial(spMaterial1); Sphere ball5 = (Sphere) new Sphere(new Point(0,
		 * 23, -150), 4.5d).setEmission(new Color(255, 150, 0))
		 * .setMaterial(spMaterial1);
		 */

		// Balls5 (Spheres)
		Sphere ball1 = (Sphere) new Sphere(new Point(7.5, -3, -90), 1.95d).setEmission(new Color(255, 255, 0))
				.setMaterial(spMaterial1);
		Sphere ball2 = (Sphere) new Sphere(new Point(-11, 17, -150), 4.5d).setEmission(new Color(0, 255, 0))
				.setMaterial(spMaterial1);
		Sphere ball3 = (Sphere) new Sphere(new Point(11, 17, -150), 4.5d).setEmission(new Color(255, 150, 0))
				.setMaterial(spMaterial1);
		Sphere ball4 = (Sphere) new Sphere(new Point(17, 6, -150), 4.5d).setEmission(new Color(0, 0, 255))
				.setMaterial(spMaterial1);
		Sphere ball5 = (Sphere) new Sphere(new Point(0, 23, -150), 4.5d).setEmission(new Color(255, 0, 0))
				.setMaterial(spMaterial1);

		scene.geometries.add(ball1, ball2, ball3, ball4, ball5);

		// Background plane defined by three points
		Point p1 = new Point(-1000, -1000, -150);
		Point p2 = new Point(1000, -1000, -350);
		Point p3 = new Point(1000, 1000, -350);
		Plane backgroundPlane = (Plane) new Plane(p1, p2, p3).setEmission(new Color(200, 153, 200))
				.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setkT(0).setkR(0));
		scene.geometries.add(backgroundPlane);

		// Light source
		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(0, 100, 0), new Vector(0, 0, -1))
				.setkL(0.000004).setkQ(0.00009));

		scene.lights.add(new DirectionalLight(new Color(150, 150, 50), new Vector(-50, -1, -1)));
		ImageWriter imageWriter = new ImageWriter("clownImage12", 800, 800);
		// scene.geometries.BuildBVH();
		camera.setImageWriter(imageWriter).setRayTracer(new RayTracerBasic(scene)).renderImage().writeToImage();
	}

}
