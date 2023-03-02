package primitives;

public class Point {
	protected final Double3 xyz;

	public Point(double x, double y, double z) {
		xyz = new Double3(x, y, z);
	}

	Point(Double3 point) {
		xyz = point;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Point other)
			return this.xyz.equals(other.xyz);
		return false;
	}

	@Override
	public String toString() {
		return xyz.toString();
	}

	public Vector subtract(Point p) {
		return new Vector(this.xyz.subtract(p.xyz));
	}

	public Point add(Vector v) {
		return new Point(this.xyz.add(v.xyz));
	}

	public double distanceSquared(Point p) {
		return (this.xyz.d1 - p.xyz.d1) * (this.xyz.d1 - p.xyz.d1) + (this.xyz.d2 - p.xyz.d2) * (this.xyz.d2 - p.xyz.d2)
				+ (this.xyz.d3 - p.xyz.d3) * (this.xyz.d3 - p.xyz.d3);
	}

	public double distance(Point p) {
		return Math.sqrt(distanceSquared(p));
	}
}