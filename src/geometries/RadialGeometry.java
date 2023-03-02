package geometries;

public abstract class RadialGeometry implements Geometry {
	protected double radius;

	RadialGeometry(double r) {
		radius = r;
	}
}
