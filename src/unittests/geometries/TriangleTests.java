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
class TriangleTests {

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
	 */
	@Test
	public void testGetNormal() {
		Triangle t = new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
		double sqrt = Math.sqrt(1d / 3);
		assertEquals(new Vector(sqrt, sqrt, sqrt), t.getNormal(new Point(0, 0, 1)), "Not good normal");

		try {
			new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
		} catch (IllegalArgumentException e) {
			fail("Failed constructing a correct triangle");
		}
		// =============== Equivalence Partitions Tests ==============
		// TC01: simple check
		Triangle tr = new Triangle(new Point(0, 1, 0), new Point(1, 0, 0), new Point(1, 1, 0));
		assertEquals(new Vector(0, 0, 1), tr.getNormal(new Point(0, 1, 0)), "the normal not correct!");
	}

}
