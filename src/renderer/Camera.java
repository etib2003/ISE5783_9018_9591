package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import primitives.Color;

import static primitives.Util.*;

import java.util.MissingResourceException;

/**
 * a class that represents a camera.
 */
public class Camera {
	private Point p0;
	private Vector vUp;
	private Vector vTo;
	private Vector vRight;
	private double width;
	private double height;
	private double distance;
	private ImageWriter imageWriter;
	private RayTracerBase rayTracerBase;

	/**
	 * @param imageWriter the imageWriter to set
	 */
	public Camera setImageWriter(ImageWriter imageWriter) {
		this.imageWriter = imageWriter;
		return this;
	}

	/**
	 * @param rayTracerBase the rayTracerBase to set
	 */
	public Camera setRayTracerBase(RayTracerBase rayTracerBase) {
		this.rayTracerBase = rayTracerBase;
		return this;
	}

	// getters:
	public Point getP0() {
		return p0;
	}

	public Vector getvUp() {
		return vUp;
	}

	public Vector getvTo() {
		return vTo;
	}

	public Vector getvRight() {
		return vRight;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public double getDistance() {
		return distance;
	}

	// constructor:
	public Camera(Point p0, Vector vTo, Vector vUp) throws IllegalArgumentException {
		if (!isZero(vTo.dotProduct(vUp))) {
			throw new IllegalArgumentException("constructor threw - vUp and vTo are not orthogonal");
		}
		this.p0 = p0;
		this.vUp = vUp.normalize();
		this.vTo = vTo.normalize();
		this.vRight = vTo.crossProduct(vUp).normalize();
	}

	// setViewPlane:
	public Camera setVPSize(double width, double height) {
		this.width = width;
		this.height = height;
		return this;
	}

	// setVPDistance:
	public Camera setVPDistance(double distance) {
		this.distance = distance;
		return this;
	}

	// region constructRay
	/**
	 * constructs a ray from the camera through pixel i,j.
	 * 
	 * @param nX number of pixels on the width of the view plane.
	 * @param nY number of pixels on the height of the view plane.
	 * @param j  location of the pixel in the X direction.
	 * @param i  location of the pixel in the Y direction.
	 * @return the constructed ray - from p0 through the wanted pixel.
	 */
	public Ray constructRay(int nX, int nY, int j, int i) {
		Point pc = p0.add(vTo.scale(distance)); // center of the view plane
		double Ry = height / nY; // Ratio - pixel height
		double Rx = width / nX; // Ratio - pixel width

		double yJ = alignZero(-(i - (nY - 1) / 2d) * Ry); // move pc Yi pixels
		double xJ = alignZero((j - (nX - 1) / 2d) * Rx); // move pc Xj pixels

		Point PIJ = pc;
		if (!isZero(xJ))
			PIJ = PIJ.add(vRight.scale(xJ));
		if (!isZero(yJ))
			PIJ = PIJ.add(vUp.scale(yJ));

		return new Ray(p0, PIJ.subtract(p0));
	}

	public void renderImage() {
		if (imageWriter == null)
			throw new MissingResourceException("Camera resource not set", "Camera", "imageWriter");

		if (rayTracerBase == null)
			throw new MissingResourceException("Camera resource not set", "Camera", "rayTracerBase");

		int nX = imageWriter.getNx();
		int nY = imageWriter.getNy();

		for (int i = 0; i < nX; i++) {
			for (int j = 0; j < nY; j++) {
				Color color = castRay(i, j);
				this.imageWriter.writePixel(i, j, color);
			}
		}
	}

	private Color castRay(int i, int j) {
		Ray ray = constructRay(this.imageWriter.getNx(), this.imageWriter.getNy(), i, j);
		return this.rayTracerBase.traceRay(ray);
	}

	public void printGrid(int interval, Color color) {
		if (imageWriter == null)
			throw new MissingResourceException("Camera resource not set", "Camera", "Image writer");
		// === running on the view plane===//
		int nX = imageWriter.getNx();
		int nY = imageWriter.getNy();

		for (int i = 0; i < nX; ++i)
			for (int j = 0; j < nY; ++j)
				if (j % interval == 0 || i % interval == 0)
					imageWriter.writePixel(i, j, color);

	}

	public void writeToImage() {
		if (imageWriter == null)
			throw new MissingResourceException("Camera resource not set", "Camera", "Image writer");
		imageWriter.writeToImage();

	}
}