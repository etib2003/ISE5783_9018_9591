/**
 * 
 */
package primitives;

/**
 * @author Eti and Chavi
 *
 */
public class Material {
	public Double3 kD = Double3.ZERO; // diffuse
	public Double3 kS = Double3.ZERO; // specular
	public int nShininess = 0;

	/**
	 * @param kD the kD to set
	 */
	public Material setKd(Double3 kD) {
		this.kD = kD;
		return this;
	}

	public Material setKd(double kD) {
		this.kD = new Double3(kD);
		return this;
	}

	/**
	 * @param kS the kS to set
	 */
	public Material setKs(Double3 kS) {
		this.kS = kS;
		return this;

	}

	public Material setKs(double kS) {
		this.kS = new Double3(kS);
		return this;
	}

	/**
	 * @param nShininess the nShininess to set
	 */
	public Material setShininess(int nShininess) {
		this.nShininess = nShininess;
		return this;

	}

}
