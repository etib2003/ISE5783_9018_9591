package geometries;
import primitives.*;

public class Sphere extends RadialGeometry {
	private Point center;
	public Sphere(Point p, double radius) {
		super(radius);
		center = p;
	}

	public Point getCenter() {
		return center;
	}

	@Override
	public Vector getNormal(Point p) {
		return null;
	}
}
