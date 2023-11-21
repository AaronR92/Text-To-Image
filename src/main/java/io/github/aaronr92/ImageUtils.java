package io.github.aaronr92;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.ImageBuilder;
import org.apache.commons.imaging.formats.png.PngImageParser;
import org.apache.commons.imaging.formats.png.PngImagingParameters;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

    public static BufferedImage drawImage(int width, int height) {
        ImageBuilder imageBuilder = new ImageBuilder(width, height, false);

        // Filling each pixel
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int color = (x + y) % 2 == 0 ? BLACK : WHITE;
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
        System.out.printf("Path [%s]\n", file.getAbsolutePath());
    }

    public static byte[] readSquareImageData(String path)
            throws IOException, ImageReadException {
        File imageFile = new File(path);

        if (!imageFile.canRead())
            throw new IOException(
                    String.format("Failed to read file with path [%s]", path)
            );

        var parser = new PngImageParser();

        var image = parser.getBufferedImage(imageFile, new PngImagingParameters());
        int sideSize = image.getWidth();

        byte[] rawPixelData = new byte[sideSize * sideSize];

        for (int y = 0; y < sideSize; y++) {
            for (int x = 0; x < sideSize; x++) {
                int pixel = image.getRGB(x, y);

                if (pixel == WHITE)
                    rawPixelData[y * sideSize + x] = 0;
                else if (pixel == BLACK)
                    rawPixelData[y * sideSize + x] = 1;
            }
        }

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
