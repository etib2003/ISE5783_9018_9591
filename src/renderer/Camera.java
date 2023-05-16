package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import primitives.Color;

import static primitives.Util.*;

import java.util.MissingResourceException;

/**
 * This class represents a camera in a 3D space. It is responsible for creating
 * rays from the camera's position to the view plane, and rendering the image
 * seen by the camera using a ray tracing algorithm.
 * 
 * @author Eti and Chavi
 */
public class Camera {
	/** The position of the camera */
	private final Point p0;
	/** The up direction of the camera */
	private Vector vUp;
	/** The direction the camera is facing */
	private Vector vTo;
	/** The vector to the right of the camera */
	private Vector vRight;
	/** The width and height of the view plane */
	private double width;
	private double height;
	/** The distance from the camera to the view plane */
	private double distance;
	/** The image writer used to write the rendered image */
	private ImageWriter imageWriter;
	/** The ray tracer used to trace the rays from the camera to the scene */
	private RayTracerBase rayTracerBase;

	/**
	 * Sets the image writer for the camera.
	 * 
	 * @param imageWriter The image writer to set.
	 * @return This camera instance.
	 */
	public Camera setImageWriter(ImageWriter imageWriter) {
		this.imageWriter = imageWriter;
		return this;
	}

	/**
	 * Sets the ray tracer for the camera.
	 * 
	 * @param rayTracerBase The ray tracer to set.
	 * @return This camera instance.
	 */
	public Camera setRayTracerBase(RayTracerBase rayTracerBase) {
		this.rayTracerBase = rayTracerBase;
		return this;
	}

	/**
	 * Returns the point P0 of the camera.
	 *
	 * @return The point P0 of the camera.
	 */
	public Point getP0() {
		return p0;
	}

	/**
	 * Returns the up vector (vUp) of the camera.
	 *
	 * @return The up vector (vUp) of the camera.
	 */
	public Vector getvUp() {
		return vUp;
	}

	/**
	 * Returns the "to" vector (vTo) of the camera.
	 *
	 * @return The "to" vector (vTo) of the camera.
	 */
	public Vector getvTo() {
		return vTo;
	}

	/**
	 * Returns the right vector (vRight) of the camera.
	 *
	 * @return The right vector (vRight) of the camera.
	 */
	public Vector getvRight() {
		return vRight;
	}

	/**
	 * Returns the width of the camera.
	 *
	 * @return The width of the camera.
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * Returns the height of the camera.
	 *
	 * @return The height of the camera.
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * Returns the distance of the camera from the screen.
	 *
	 * @return The distance of the camera from the screen.
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * Constructs a camera with the given position, direction, and up direction.
	 * 
	 * @param p0  The position of the camera.
	 * @param vTo The direction the camera is facing.
	 * @param vUp The up direction of the camera.
	 * @throws IllegalArgumentException If vUp and vTo are not orthogonal.
	 */
	public Camera(Point p0, Vector vTo, Vector vUp) throws IllegalArgumentException {
		if (!isZero(vTo.dotProduct(vUp))) {
			throw new IllegalArgumentException("constructor threw - vUp and vTo are not orthogonal");
		}
		this.p0 = p0;
		this.vUp = vUp.normalize();
		this.vTo = vTo.normalize();
		this.vRight = vTo.crossProduct(vUp).normalize();
	}

	/**
	 * Sets the size of the view plane.
	 * 
	 * @param width  The width of the view plane.
	 * @param height The height of the view plane.
	 * @return This camera instance.
	 */
	public Camera setVPSize(double width, double height) {
		this.width = width;
		this.height = height;
		return this;
	}

	/**
	 * Sets the distance between the camera and the viewport.
	 * 
	 * @param distance the distance between the camera and the viewport
	 * @return the Camera object with the updated viewport distance
	 */
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
		double rY = height / nY; // Ratio - pixel height
		double rX = width / nX; // Ratio - pixel width

		double yI = alignZero(-(i - (nY - 1) / 2d) * rY); // move pc Yi pixels
		double xJ = alignZero((j - (nX - 1) / 2d) * rX); // move pc Xj pixels

		Point pIJ = pc;
		if (!isZero(xJ))
			pIJ = pIJ.add(vRight.scale(xJ));
		if (!isZero(yI))
			pIJ = pIJ.add(vUp.scale(yI));

		return new Ray(p0, pIJ.subtract(p0));
	}

	/**
	 * Renders the image by iterating through each pixel in the image writer and
	 * casting a ray for each pixel, then writing the resulting color to the image
	 * writer. Throws a MissingResourceException if either the image writer or the
	 * ray tracer base are not set.
	 */
	public void renderImage() throws MissingResourceException {
		if (imageWriter == null)
			throw new MissingResourceException("Camera resource not set", "Camera", "imageWriter");

		if (rayTracerBase == null)
			throw new MissingResourceException("Camera resource not set", "Camera", "rayTracerBase");

		int nX = imageWriter.getNx();
		int nY = imageWriter.getNy();

		for (int j = 0; j < nX; j++) {
			for (int i = 0; i < nY; i++) {
				Color color = castRay(j, i, nX, nY);
				this.imageWriter.writePixel(j, i, color);
			}
		}
	}

	/**
	 * Casts a ray through the given pixel (i,j) on the view plane and returns the
	 * color that results from tracing the ray.
	 * 
	 * @param i the x-coordinate of the pixel on the view plane
	 * @param j the y-coordinate of the pixel on the view plane
	 * @return the color resulting from tracing the ray through the given pixel
	 */
	private Color castRay(int j, int i, int nX, int nY) {
		Ray ray = constructRay(nX, nY, j, i);
		return this.rayTracerBase.traceRay(ray);
	}

	/**
	 * Writes a grid of pixels to the image writer, with a given interval between
	 * the grid lines and a specified color. Throws a MissingResourceException if
	 * the image writer is not set.
	 * 
	 * @param interval the interval between grid lines
	 * @param color    the color to use for the grid lines
	 */
	public void printGrid(int interval, Color color) throws MissingResourceException {
		if (imageWriter == null)
			throw new MissingResourceException("Camera resource not set", "Camera", "Image writer");
		// === running on the view plane===//
		int nX = imageWriter.getNx();
		int nY = imageWriter.getNy();

		for (int j = 0; j < nX; ++j)
			for (int i = 0; i < nY; ++i)
				if (i % interval == 0 || j % interval == 0)
					imageWriter.writePixel(j, i, color);

	}

	/**
	 * Writes the image to file using the image writer. Throws a
	 * MissingResourceException if the image writer is not set.
	 */
	public void writeToImage() throws MissingResourceException {
		if (imageWriter == null)
			throw new MissingResourceException("Camera resource not set", "Camera", "Image writer");
		imageWriter.writeToImage();
	}
}
