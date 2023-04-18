/**
 * 
 */
package unittests.geometries;

import primitives.*;
import geometries.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author אתוש
 *
 */
class PlaneTests {
	/**
	 * test for {@link geometries.Plane#Plane(Point, Point, Point)}.
	 */
	@Test
	public void testPlane() {
		// =============== Equivalence Partitions Tests ==============
		// TC01: constructor acting well
		assertDoesNotThrow(()->new Plane(new Point(0, 1, 0), new Point(1, 0, 0), new Point(1, 1, 0)),
				"Failed constructing a correct plane");

		// =============== Boundary Values Tests ==================

		// TC11: three points in one ray
		assertThrows(IllegalArgumentException.class,
				() -> new Plane(new Point(1, 2, 3), new Point(2, 4, 6), new Point(4, 8, 12)),
				"Constructed a Plane with tree points in one ray");

		// TC12: two points unit
		assertThrows(IllegalArgumentException.class,
				() -> new Plane(new Point(1, 2, 3), new Point(1, 2, 3), new Point(1, 1, 1)),
				"Constructed a Plane with two same points");
	}

	/**
	 * Test method for {@link geometries.Plane#getNormal()}.
	 */
	@Test
	public void testGetNormal() {
		Plane pl = new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));

		assertEquals(1, pl.getNormal().length(), "ERROR: Normal length diffrent than 1");

		// =============== Equivalence Partitions Tests ==============
		// TC01:
		assertEquals(new Vector(1 / Math.sqrt(3), 1 / Math.sqrt(3), 1 / Math.sqrt(3)), pl.getNormal(new Point(1, 0, 0)),
				"Bad normal to plane");
	}
	
	
	
	
	
	
	
	
	
	
}
