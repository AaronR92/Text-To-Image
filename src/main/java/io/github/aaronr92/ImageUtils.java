package io.github.aaronr92;

import org.apache.commons.imaging.common.ImageBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtils {

    public static BufferedImage drawImage(int width, int height) {
        ImageBuilder imageBuilder = new ImageBuilder(width, height, false);

        // Filling each pixel
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int color = (x + y) % 2 == 0 ? 0x000000 : 0xFFFFFF;
                imageBuilder.setRGB(x, y, color);
            }
        }

        return imageBuilder.getBufferedImage();
    }

    public static void saveImage(BufferedImage image, String filename)
            throws IOException {
        var file = new File(filename);

        ImageIO.write(image, "png", file);

        System.out.println("Image was saved successfully!");
        System.out.printf("Path [%s]", file.getAbsolutePath());
    }

}
