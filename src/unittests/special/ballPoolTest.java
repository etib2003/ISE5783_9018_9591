/**
 * 
 */
package unittests.special;

import org.junit.jupiter.api.Test;

import geometries.Intersectable;
import geometries.Sphere;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

class ballPoolTest {
	@Test
	public void createBallPoolImage() {
		Intersectable.setCBR();
		Scene scene = new Scene("Ball Pool");

		Material ballMaterial = new Material().setKd(0.6).setKs(0.9).setShininess(3000);

		int rowCount = 45; // Number of rows in the ball pool
		int ballsPerRow = 45; // Number of balls per row

		double ballRadius = 6; // Radius of each ball
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

		double x = startX + 263;// x-coordinate of the current ball
		double y = startY + 263;// y-coordinate of the current ball
		double z = -70; // z-coordinate of the balls (depth)

		Point center = new Point(x, y, z);
		Sphere ball = (Sphere) new Sphere(center, 25).setEmission(colorArray[color++ % 5]).setMaterial(ballMaterial);
		scene.geometries.add(ball);

		scene.geometries.BuildBVH();

		// Light source
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.15));
		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(0, 100, 0), new Vector(0, 0, -1))
				.setkL(0.000004).setkQ(0.00009));

		new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(600, 600).setVPDistance(1000) //
				.setDoFActive(true).setGridDensity(2).setApertureRadius(100).setFocalLength(1070) //
				.setImageWriter(new ImageWriter("ballPoolImage1", 800, 800)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage().writeToImage();
	}
}
