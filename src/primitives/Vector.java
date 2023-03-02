package primitives;

public class Vector extends Point {
	public Vector(double x, double y, double z) {
		super(x, y, z);
		if (xyz.equals(Double3.ZERO))
			throw new IllegalArgumentException("Zero vector");
	}

	Vector(Double3 point) {
		super(point);
		if (xyz.equals(Double3.ZERO))
			throw new IllegalArgumentException("Zero vector");
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public Vector add(Vector v) {
		return new Vector(this.xyz.add(v.xyz));
	}

	public Vector scale(double rhs) {
		return new Vector(this.xyz.scale(rhs));
	}

	public double dotProduct(Vector v) {
		return ((xyz.d1 * v.xyz.d1) + (xyz.d2 * v.xyz.d2) + (xyz.d3 * v.xyz.d3));
	}

	public Vector crossProduct(Vector v) {
		return new Vector((xyz.d2 * v.xyz.d3) - (xyz.d3 * v.xyz.d2), (xyz.d3 * v.xyz.d1) - (xyz.d1 * v.xyz.d3),
				(xyz.d1 * v.xyz.d2) - (xyz.d2 * v.xyz.d1));
	}

	public double lengthSquared() {
		return dotProduct(this);
	}

	public double length() {
		return Math.sqrt(lengthSquared());
	}

	public Vector normalize() {
		return new Vector(xyz.reduce(length()));
	}
}
