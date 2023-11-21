package io.github.aaronr92;

import org.apache.commons.imaging.ImageReadException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException, ImageReadException {
        var encoded = StringUtils.stringToBits("Ultra secret password: p4ssw@rd");

        System.out.println("Encoded: " + Arrays.toString(encoded));

        ImageUtils.saveImage(
                ImageUtils.drawSquareImage(
                        encoded,
                        1
                ),
                "image.png"
        );

        var bytes = ImageUtils.readSquareImageData(
                "D:\\All Projects\\Java\\TextToImage\\image.png", 1
        );

        System.out.println(encoded.length);
        System.out.println(bytes.length);
        System.out.println(StringUtils.bitsToString(bytes));
    }

}