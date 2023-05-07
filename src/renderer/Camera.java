package renderer;

import static primitives.Util.isZero;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Camera {

	private Point p0;
	private Vector vUp;
	private Vector vTo;
	private Vector vRight;
	private double width;
	private double height;
	private double distance;

	public double getDistance() {
		return distance;
	}

	public Point getP0() {
		return p0;
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	public Vector getvRight() {
		return vRight;
	}

	public Vector getvTo() {
		return vTo;
	}

	public Vector getvUp() {
		return vUp;
	}

	public Camera(Point p, Vector up, Vector to) {
		if (!isZero(up.dotProduct(to))) {
			throw new IllegalArgumentException("the vectors are not orthogonal");
		}

		vUp = up.normalize();
		vTo = to.normalize();
		vRight = (vUp.crossProduct(vTo)).normalize();
	}

	public Camera setVPSize(double tmpWidth, double tmpHeight) {
		width = tmpWidth;
		height = tmpHeight;
		return this;
	}

	public Camera setVPDistance(double tmpDistance) {
		distance = tmpDistance;
		return this;
	}

	 
	 public Ray constructRay(int nX, int nY, int j, int i) {
	        Point imgCenter = p0.add(vTo.scale(distance));
	        double rY = height / nY, rX = width / nX;
	        double iY = -(i - (nY - 1d) / 2) * rY, jX = (j - (nX - 1d) / 2) * rX;
	        Point ijP = imgCenter;
	        if (jX != 0) ijP = ijP.add(vRight.scale(jX));
	        if (iY != 0) ijP = ijP.add(vUp.scale(iY));
	        Vector ijV = ijP.subtract(p0);
	        return new Ray(p0, ijV);
	    }
	//public Ray constructRay(int nX, int nY, int j, int i) {
		//return null;

	//}
}