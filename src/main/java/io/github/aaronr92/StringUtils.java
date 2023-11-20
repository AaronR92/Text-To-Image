package io.github.aaronr92;

import lombok.SneakyThrows;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringUtils {

    /**
     * Converts input string into a bit-array with UTF-8 encoding
     * @param inputString string to convert into UTF-8
     */
    @SneakyThrows
    public static byte[] stringToBits(String inputString) {
        return stringToBits(inputString, StandardCharsets.UTF_8);
    }

    public static byte[] stringToBits(String inputString, Charset charset)
            throws UnsupportedEncodingException {
        // Checking for supported encoding
        checkEncoding(charset);

        // Array of bytes that contains input string
        byte[] bytes = inputString.getBytes(charset);

        // Getting the size of a single character in specified encoding
        int charSize = getCharacterSize(charset);

        // Array of bits that will be returned
        byte[] bits = new byte[bytes.length * charSize];

        for (int i = 0; i < bytes.length; i++) {
            for (int j = 0; j < charSize; j++) {
                // Calculating bit value
                boolean bit = (bytes[i] & (1 << ((charSize - 1) - j))) != 0;

                // Writing calculated bit into the array
                bits[i * 8 + j] = (byte) (bit ? 1 : 0);
            }
        }

        return bits;
    }

    private static void checkEncoding(Charset charset)
            throws UnsupportedEncodingException {
        if (charset != StandardCharsets.UTF_8 && charset != StandardCharsets.US_ASCII)
            throw new UnsupportedEncodingException();
    }

    private static int getCharacterSize(Charset charset)
            throws UnsupportedEncodingException {
        if (charset == StandardCharsets.UTF_8)
            return 8;
        if (charset == StandardCharsets.US_ASCII)
            return 7;

        throw new UnsupportedEncodingException();
    }

}
