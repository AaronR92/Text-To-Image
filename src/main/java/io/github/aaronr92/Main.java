package io.github.aaronr92;

import org.apache.commons.imaging.ImageReadException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException, ImageReadException {
        var encoded = StringUtils.stringToBits("Мой самый секретный пароль: p4ssw@rd");

        ImageUtils.saveImage(
                ImageUtils.drawSquareImage(
                        encoded,
                        2
                ),
                "image.png"
        );

        var bytes = ImageUtils.readSquareImageData(
                "D:\\All Projects\\Java\\TextToImage\\image.png", 2
        );

        System.out.println(encoded.length);
        System.out.println(bytes.length);
        System.out.println(StringUtils.bitsToString(bytes));
    }

}