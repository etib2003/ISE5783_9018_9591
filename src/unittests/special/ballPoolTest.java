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
import lighting.LightSource;
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

class ballPoolTest {
	private Scene scene = new Scene("Ball Pool");

	@Test
	public void createBallPoolImage() {
		Camera camera = new Camera(new Point(0, 0, -40), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPSize(800, 800)
				.setVPDistance(58);

		Material ballMaterial = new Material().setKd(0.6).setKs(0.9).setShininess(3000);

		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.15));

		int rowCount = 45; // Number of rows in the ball pool
		int ballsPerRow = 45; // Number of balls per row

		double ballRadius = 8; // Radius of each ball
		double poolWidth = 2 * ballRadius * ballsPerRow; // Total width of the ball pool
		double poolLength = 2 * ballRadius * rowCount; // Total length of the ball pool

		double startX = -poolWidth / 2 + ballRadius; // Starting x-coordinate of the first ball
		double startY = -poolLength / 2 + ballRadius; // Starting y-coordinate of the first ball

		Color[] colorArray = new Color[5];
		// Assign colors to the array elements
		colorArray[0] = new Color(255, 255, 0);
		colorArray[1] = new Color(0, 255, 0);
		colorArray[2] = new Color(255, 150, 0);
		colorArray[3] = new Color(0, 0, 255);
		colorArray[4] = new Color(255, 0, 0);
		int color = 0;

		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < ballsPerRow; j++) {
				double x = startX + j * 2 * ballRadius; // x-coordinate of the current ball
				double y = startY + i * 2 * ballRadius; // y-coordinate of the current ball
				double z = -100; // z-coordinate of the balls (depth)

				Point center = new Point(x, y, z);
				Sphere ball = (Sphere) new Sphere(center, ballRadius).setEmission(colorArray[color++ % 5])
						.setMaterial(ballMaterial);
				scene.geometries.add(ball);
			}
			color++;
		}

		// Light source
		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(0, 100, 0), new Vector(0, 0, -1))
				.setkL(0.000004).setkQ(0.00009));

		ImageWriter imageWriter = new ImageWriter("ballPoolImage", 800, 800);
		scene.geometries.BuildBVH();
		camera.setImageWriter(imageWriter).setRayTracer(new RayTracerBasic(scene)).renderImage().writeToImage();
	}
}
