package io.github.aaronr92;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ImageUtils.saveImage(ImageUtils.drawImage(5, 5), "drawnImage.png");
    }
}