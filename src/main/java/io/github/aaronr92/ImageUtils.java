package io.github.aaronr92;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.ImageBuilder;
import org.apache.commons.imaging.formats.png.PngImageParser;
import org.apache.commons.imaging.formats.png.PngImagingParameters;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ImageUtils {

    private static final int RED = 0xFFFF0000;
    private static final int GREEN = 0xFF00FF00;
    private static final int BLUE = 0xFF0000FF;
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

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
                true
        );

        int i = 0;

        try {
            for (int y = 0; y < sideSize; y += pixelSize) {
                for (int x = 0; x < sideSize; x += pixelSize) {
                    int pixel = text[i] == 0 ? WHITE : BLACK;
                    drawPixel(imageBuilder, pixel, pixelSize, x, y);
                    i++;
                }
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {}

        return imageBuilder.getBufferedImage();
    }

    public static void saveImage(BufferedImage image, String filename)
            throws IOException {
        var file = new File(filename);

        ImageIO.write(image, "png", file);

        System.out.println("Image was saved successfully!");
        System.out.printf("Path [%s]\n", file.getAbsolutePath());
    }

    public static byte[] readSquareImageData(String path, int pixelSize)
            throws IOException, ImageReadException {
        File imageFile = new File(path);

        if (!imageFile.canRead())
            throw new IOException(
                    String.format("Failed to read file with path [%s]", path)
            );

        var parser = new PngImageParser();

        var image = parser.getBufferedImage(imageFile, new PngImagingParameters());
        int sideSize = image.getWidth();

        int preciseSideSize = (int) Math.ceil(((double) sideSize / pixelSize));

        byte[] rawPixelData = new byte[preciseSideSize * preciseSideSize];

        int aY = 0;

        for (int y = 0; y < sideSize; y += pixelSize) {
            int aX = 0;
            for (int x = 0; x < sideSize; x += pixelSize) {
                int pixel = image.getRGB(x, y);

                if (pixel == WHITE)
                    rawPixelData[aY * preciseSideSize + aX] = 0;
                else if (pixel == BLACK)
                    rawPixelData[aY * preciseSideSize + aX] = 1;
                else
                    rawPixelData[aY * preciseSideSize + aX] = 127;

                aX++;
            }
            aY++;
        }

        System.out.println(Arrays.toString(rawPixelData));

        return rawPixelData;
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

}
