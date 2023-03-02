package geometries;

import primitives.*;;

public class Cylinder extends Tube {
	private double height;

	public Cylinder(double h, Ray r, double radius) {
		super(r, radius);
		height = h;
	}

	public double getHeight() {
		return height;
	}

	@Override
	public Vector getNormal(Point p) {
		return null;
	}
}
