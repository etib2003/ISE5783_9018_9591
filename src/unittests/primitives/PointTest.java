/**
 * 
 */
package unittests.primitives;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.*;


/**
 * * Unit tests for primitives.Point class
 * @author אתוש
 *
 */
class PointTest {
	Point p0 = new Point(0,0,0);
	Point p1 = new Point(1, 1, 1);
	Point p2 = new Point(2, 2, 2);
	Vector v1 = new Vector(-1, -1, -1);
	Vector v2 = new Vector(-1, 1, 0);
	Vector v3 = new Vector(1,1,1);
	Vector v4 = new Vector(3,3,3);


	/**
	 * Test method for {@link primitives.Point#add(primitives.Vector)}.
	 */
	@Test
	public void testAdd() {
		// ============ Equivalence Partitions Tests ==============
		// TA01: Test that the adding is correct
		assertEquals(new Point(0,2,1), p1.add(v2), "Function add doesnt work correct");
		// =============== Boundary Values Tests ==================
		// TA11: Test that the adding of zero point is correct
		assertEquals(p1, p0.add(v3),"Function add doesnt work correct for zero point ");
		// TA12: Test that the adding of opposites is correct
		assertEquals(p0, p1.add(v1),"Function add doesnt work correct for opposites ");

	}
	
	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}.
	 */
	@Test
	public void testSubtract() {
		// ============ Equivalence Partitions Tests ==============
		// TA01: Test that the subtracting is correct
		assertEquals(v3, v4.subtract(p2), "Function subtract doesnt work correct");
		// =============== Boundary Values Tests ==================
		// TA11: Test that the subtracting of zero point is correct
		assertEquals(v1, v1.subtract(p0),"Function subtract doesnt work correct for zero point");
		// TA12: Test that the subtracting of similar vectors is correct
		assertThrows(IllegalArgumentException.class,()->v3.subtract(p1), 
				"Function subtract doesnt work correct for similar" );	
	}

	/**
	 * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
	 */
	@Test
	public void testDistanceSquared() {
		// =======Equivalence Partitions Tests=======
		// TD01: Test that the distance between two points is correct (simple test)
		assertEquals(3, p1.distanceSquared(p2), "Function distanceSquared doesn't work correct");
	}

	/**
	 * Test method for {@link primitives.Point#distance(primitives.Point)}.
	 */
	@Test
	public void testDistance() {
		// =======Equivalence Partitions Tests=======
		// TC01: testing if the distance between two points -with sqrt- is correct
		// (simple test)
		assertEquals(Math.sqrt(3), p1.distance(p2), "Function distance doesn't work correct");	}

}