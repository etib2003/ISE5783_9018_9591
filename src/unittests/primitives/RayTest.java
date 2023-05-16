/**
 * 
 */
package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Unit tests for primitives.Ray class
 * 
 * @author Eti and Chavi
 *
 */
class RayTest {

	/**
	 * Test method for {@link primitives.Ray#findClosestPoint(java.util.List)}.
	 */
	@Test
	void testFindClosestPoint() {
		Vector vector = new Vector(0, -0.5, 0);
		Point p1 = new Point(1, 1, 1);
		Point p2 = new Point(2, 2, 2);
		Point p3 = new Point(3, 3, 3);

		List<Point> l = List.of(p1, p2, p3);

		// ============ Equivalence Partitions Tests ==============
		// TC01: The closest point is in the middle of the list
		Ray ray1 = new Ray(new Point(2, 2.5, 2), vector);
		assertEquals(p2, ray1.findClosestPoint(l), "The point in the middle");

		// =============== Boundary Values Tests ==================
		// TC10: The closest point is the first point in the list
		Ray ray2 = new Ray(new Point(1, 1.5, 1), vector);
		assertEquals(p1, ray2.findClosestPoint(l), "The point is the first one");

		// TC11: The closest point is the last point in the list
		Ray ray3 = new Ray(new Point(3, 3.5, 3), vector);
		assertEquals(p3, ray3.findClosestPoint(l), "The point is the last one");

		// TC12: The list is null
		l = List.of(); // empties the list
		assertNull(ray3.findClosestPoint(l), "The list is empty");

	}

	/**
	 * Test method for {@link primitives.Ray#getPoint(double)}.
	 */
	@Test
	void testGetPoint() {
		Vector vector = new Vector(1, 0, 0);
		Point p = new Point(1, 2, 3);
		Ray ray = new Ray(p, vector);

		// ============ Equivalence Partitions Tests ==============
		// TC01: Check getPoint(t) for t=0
		assertEquals(p, ray.getPoint(0), "getPoint doesnt work for t=0");
		// TC01: Check getPoint(t) for t!=0
		assertEquals(new Point(3, 2, 3), ray.getPoint(2), "getPoint doesnt work for t!=0");

	}
}
