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
public class SpotLight extends PointLight {
	private Vector direction;
    private double narrowBeam = 1;//from 4253


	/**
	 * @param intensity
	 * @param position
	 * @param direction
	 */
	public SpotLight(Color intensity, Point position, Vector direction) {
		super(intensity, position);
		this.direction = direction;
	}
	
	//from 4253
	 /**
     * setter for narrowBeam
     *
     * @param _narrowBeam the new value for narrowBeam
     * @return this light
     */
    public SpotLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }

}
