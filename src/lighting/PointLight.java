/**
 * 
 */
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * @author Eti and Chavi
 *
 */
public class PointLight extends Light implements LightSource {
	private Point position;
	private double kC = 1;
	private double kL = 0;
	private double kQ = 0;
	
	/**
	 * @param intensity
	 * @param position
	 */
	public PointLight(Color intensity, Point position) {
		super(intensity);
		this.position = position;
	}

	/**
	 * @param kC the kC to set
	 */
	public PointLight setkC(double kC) {
		this.kC = kC;
		return this;
	}

	/**
	 * @param kL the kL to set
	 */
	public PointLight setkL(double kL) {
		this.kL = kL;
		return this;

	}

	/**
	 * @param kQ the kQ to set
	 */
	public PointLight setkQ(double kQ) {
		this.kQ = kQ;
		return this;

	}


	// לבדוק לגבי זה
	@Override
	public Color getIntensity(Point point) {
		double d = position.distance(point);
		return super.getIntensity().reduce(kC + kL * d + kQ * d * d);
	}

	@Override
	public Vector getL(Point point) {
		return point.subtract(position).normalize();
	}
}
