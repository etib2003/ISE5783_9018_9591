/**
 * 
 */
package unittests.renderer;

import primitives.Color;
import renderer.ImageWriter;
import org.junit.jupiter.api.Test;

/**
 * @author אתוש
 *
 */
class ImageWriterTest {
	/**
	 * Test the writeToImage() method of the ImageWriter class. This test creates an
	 * image with alternating red and light blue pixels, with red pixels at every
	 * 50th column and row.
	 */

	@Test
	void test1() {
		Color lightBlue = new Color(173, 216, 230);
		Color red = new Color(255, 0, 0);
		ImageWriter imageWriter = new ImageWriter("pixels", 801, 501);
		int nX=imageWriter.getNx();
		int nY=imageWriter.getNy();
		for (int i = 0; i < nX; i++)
			for (int j = 0; j < nY; j++) {
				if ((i % 50 == 0) || (j % 50 == 0))
					imageWriter.writePixel(i,j, red);
				else
					imageWriter.writePixel(i,j, lightBlue);
			}
		imageWriter.writeToImage();
	}

}
