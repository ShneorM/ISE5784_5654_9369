package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

public class ImageWriterTest {

    /**
     * Test method for {@link renderer.ImageWriter#writeToImage()}
     */
    @Test
    void writeToImage() {
        ImageWriter imageWriter = new ImageWriter("grid", 800, 500);
        int horizontalLine = imageWriter.getNx() / 16;
        int verticalLine = imageWriter.getNy() / 10;
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                if (i % horizontalLine == 0 || j % verticalLine == 0) {
                    imageWriter.writePixel(i, j,new  Color(58, 67, 82));
                } else {
                    imageWriter.writePixel(i, j, new Color(139, 160, 196));
                }

            }
        }

        imageWriter.writeToImage();
    }
}
