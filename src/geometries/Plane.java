package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {
	private Point p0;
	private Vector normal;

	public Plane(Point p1, Point p2, Point p3) {
		p0=p1;
		normal = null;
	}

	Plane(Point p, Vector n) {
		p0 = p;
		normal = n.normalize();
	}

	public Vector getNormal() {
		return null;
	}

	public Vector getNormal(Point p) {
		return getNormal();
	}
}
