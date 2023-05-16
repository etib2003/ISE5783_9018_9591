package unittests.renderer;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import renderer.Camera;

/**
 * The IntegrationTests class contains integration tests for the renderer
 * package. These tests verify the interaction between the camera and various
 * geometric shapes.
 * 
 * @author Eti and Chavi
 */
class IntegrationTests {

	/**
	 * Constructs rays from each pixel and sums the intersections with a given body.
	 * 
	 * @param camera The camera used to construct the rays.
	 * @param body   The intersectable body to test intersections with.
	 * @param nX     The number of pixels in the x-direction.
	 * @param nY     The number of pixels in the y-direction.
	 * @return The sum of intersections.
	 */
	int sumIntersection(Camera camera, Intersectable body, int nX, int nY) {
		int sum = 0;
		// sum the intersections for each ray from each pixel
		for (int j = 0; j < nX; j++) {
			for (int i = 0; i < nY; i++) {
				Ray ray = camera.constructRay(nX, nY, j, i);
				List<Point> res = body.findIntersections(ray);
				if (res != null) {
					sum += res.size();
				}
			}
		}
		return sum;
	}

	@Test
	void shpereTest() {
		Camera camera1 = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPSize(3, 3)
				.setVPDistance(1);
		Camera camera2 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPSize(3, 3)
				.setVPDistance(1);

		// TC01: test shpere with radius 1
		Sphere s1 = new Sphere(new Point(0, 0, -3), 1);
		int sum1 = sumIntersection(camera1, s1, 3, 3);
		assertEquals(2, sum1, "constructRay() and findIntersections() wrong result");

		// TC02: test shpere with radius 2.5
		Sphere s2 = new Sphere(new Point(0, 0, -2.5), 2.5);
		int sum2 = sumIntersection(camera2, s2, 3, 3);
		assertEquals(18, sum2, "constructRay() and findIntersections() wrong result");

		// TC03: test shpere with radius 2
		Sphere s3 = new Sphere(new Point(0, 0, -2), 2);
		int sum3 = sumIntersection(camera2, s3, 3, 3);
		assertEquals(10, sum3, "constructRay() and findIntersections() wrong result");

		// TC04: test shpere with radius 4
		Sphere s4 = new Sphere(new Point(0, 0, -2), 4);
		int sum4 = sumIntersection(camera2, s4, 3, 3);
		assertEquals(9, sum4, "constructRay() and findIntersections() wrong result");

		// TC05: test shpere with radius 5
		Sphere s5 = new Sphere(new Point(0, 0, 1), 0.5);
		int sum5 = sumIntersection(camera1, s5, 3, 3);
		assertEquals(0, sum5, "constructRay() and findIntersections() wrong result");
	}

	@Test
	void planeTest() {
		Camera camera = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPSize(3, 3)
				.setVPDistance(1);

		// TC01: test plane parallar to view plane
		Plane p1 = new Plane(new Point(1, 1, -3), new Point(2, 1, -3), new Point(1, 2, -3));
		int sum1 = sumIntersection(camera, p1, 3, 3);
		assertEquals(9, sum1, "constructRay() and findIntersections() wrong result");

		// TC02: test plane not parallar to view plane
		Plane p2 = new Plane(new Point(0, 0, -3), new Point(1, 0, -3), new Point(0, 1, -2.5));
		int sum2 = sumIntersection(camera, p2, 3, 3);
		assertEquals(9, sum2, "constructRay() and findIntersections() wrong result");

		// TC03: test plane not parallar to view plane
		Plane p3 = new Plane(new Point(0, 0, -3), new Point(1, 0, -3), new Point(0, 1, -2));
		int sum3 = sumIntersection(camera, p3, 3, 3);
		assertEquals(6, sum3, "constructRay() and findIntersections() wrong result");

	}

	@Test
	void TriangleTest() {
		Camera camera = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPSize(3, 3)
				.setVPDistance(1);

		// TC01: test plane not parallar to view plane
		Triangle t1 = new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
		int sum1 = sumIntersection(camera, t1, 3, 3);
		assertEquals(1, sum1, "constructRay() and findIntersections() wrong result");

		// TC02: test plane not parallar to view plane
		Triangle t2 = new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
		int sum2 = sumIntersection(camera, t2, 3, 3);
		assertEquals(2, sum2, "constructRay() and findIntersections() wrong result");
	}
}
