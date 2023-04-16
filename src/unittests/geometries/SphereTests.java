/**
 * 
 */
package unittests.geometries;

import primitives.*;
import geometries.*;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;

/**
 * @author אתוש
 *
 */
class SphereTests {

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
	 */

	@Test
	public void testGetNormal() {
		double radius = 5;
		Point center = new Point(1, 2, 3);
		Sphere sphere = new Sphere(center, radius);
		Vector normal = new Vector(4d/5, 0, 3d/5);
		
		// ============ Equivalence Partitions Tests ==============
		assertEquals(normal, sphere.getNormal(new Point(5, 2, 6)), "bad normal to sphere");
	}
}
