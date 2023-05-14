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

	@Test
	void test1() {
		 Color lightBlue = new Color(173,216,230);
		 Color red = new Color(255, 0, 0);

	        ImageWriter imageWriter = new ImageWriter("Test1", 801, 501);
	        for (int i = 0; i < imageWriter.getNx(); i++)
	            for (int j = 0; j < imageWriter.getNy(); j++) {
	                if ((i % 50 == 0) || (j % 50 == 0))
	                    imageWriter.writePixel(i, j, red);
	                else imageWriter.writePixel(i, j, lightBlue);
	            }
	        imageWriter.writeToImage();	}

}
