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
class TubeTests {

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
	 */
	@Test
	public void testGetNormal() {
		Ray ray = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
		Tube tube = new Tube(ray,Math.sqrt(2));

		// =============== Equivalence Partitions Tests ==============
		// TC01: simple check
		assertEquals(new Vector(1, 1, 0).normalize(), tube.getNormal(new Point(1, 1, 2)), "the normal is not correct");

		// =============== Boundary Values Tests ==================
		// TC11: Test that works for connection of the point to the head of the ray of the tube
		assertEquals(new Vector(1, 1, 0).normalize(), tube.getNormal(new Point(1, 1, 1)), "the normal is not correct");
	
	}
}
