package io.github.aaronr92;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ImageUtils.saveImage(
                ImageUtils.drawSquareImage(
                        StringUtils.stringToBits("Hello!")
                ),
                "image.png"
        );
    }
}