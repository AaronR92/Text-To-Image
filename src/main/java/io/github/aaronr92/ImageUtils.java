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
                Math.ceil(Math.sqrt((double) text.length / 2)) * pixelSize
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
                    int pixel = getColor(text[i], text[i + 1]);

                    drawPixel(imageBuilder, pixel, pixelSize, x, y);

                    i += 2;
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

        int preciseSideSize = (int) Math.ceil(((double) sideSize / pixelSize)) * 2;

        byte[] pixelData = new byte[
                (int) (preciseSideSize * Math.ceil((double) preciseSideSize / 2))
                ];

        int aY = 0;

        for (int y = 0; y < sideSize; y += pixelSize) {
            int aX = 0;
            for (int x = 0; x < sideSize; x += pixelSize) {
                int pixel = image.getRGB(x, y);

                var bits = getBitsFromColor(pixel);

                pixelData[aY * preciseSideSize + aX] = bits.a();
                aX++;
                pixelData[aY * preciseSideSize + aX] = bits.b();
                aX++;
            }
            aY++;
        }

        return pixelData;
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

    private static int getColor(byte firstBit, byte secondBit) {
        if (firstBit == 0 && secondBit == 0) {
            return WHITE;
        }
        if (firstBit == 0 && secondBit == 1) {
            return GREEN;
        }
        if (firstBit == 1 && secondBit == 0) {
            return RED;
        }
        if (firstBit == 1 && secondBit == 1) {
            return BLUE;
        }

        throw new IllegalArgumentException();
    }

    private static Pair<Byte, Byte> getBitsFromColor(int color) {
        return switch (color) {
            case WHITE -> Pair.of((byte) 0, (byte) 0);
            case GREEN -> Pair.of((byte) 0, (byte) 1);
            case RED -> Pair.of((byte) 1, (byte) 0);
            case BLUE -> Pair.of((byte) 1, (byte) 1);
            case BLACK -> Pair.of((byte) 127, (byte) 127);
            default -> throw new IllegalArgumentException(
                    String.format("Unsupported color [%d]", color)
            );
        };
    }

}
