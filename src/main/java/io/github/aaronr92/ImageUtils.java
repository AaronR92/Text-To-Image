package io.github.aaronr92;

import org.apache.commons.imaging.common.ImageBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtils {

    public static BufferedImage drawSquareImage(byte[] text) {
        return drawSquareImage(text, 1);
    }

    public static BufferedImage drawSquareImage(byte[] text, int pixelSize) {
        int sideSize = (int) (
                Math.ceil(Math.sqrt(text.length)) * pixelSize
        );

        ImageBuilder imageBuilder = new ImageBuilder(
                sideSize,
                sideSize,
                false
        );

        int i = 0;

        try {
            for (int y = 0; y < sideSize; y += pixelSize) {
                for (int x = 0; x < sideSize; x += pixelSize) {
                    int pixel = text[i] == 0 ? 0xFFFFFF : 0x000000;
                    drawPixel(imageBuilder, pixel, pixelSize, x, y);
                    i++;
                }
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {}

        return imageBuilder.getBufferedImage();
    }

    private static void drawPixel(
            ImageBuilder imageBuilder,
            int color,
            int pixelSize,
            int startingX,
            int startingY
    ) {
        for (int y = startingY; y < pixelSize + startingY; y++) {
            for (int x = startingX; x < pixelSize + startingX; x++) {
                imageBuilder.setRGB(x, y, color);
            }
        }
    }

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
