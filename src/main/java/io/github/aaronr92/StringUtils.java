package io.github.aaronr92;

import lombok.SneakyThrows;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class StringUtils {

    /**
     * Encodes input string into a bit-array with UTF-8 encoding
     * @param inputString string to convert in UTF-8
     */
    @SneakyThrows
    public static byte[] stringToBits(String inputString) {
        return stringToBits(inputString, StandardCharsets.UTF_8);
    }

    /**
     * Encodes input string into a bit-array with specified encoding
     * @param inputString string to convert in specified encoding
     * @param charset encoding of string
     */
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
                bits[i * charSize + j] = (byte) (bit ? 1 : 0);
            }
        }

        byte[] result = new byte[bits.length + 1];

        result[0] = getCharsetDeterminant(charset);

        System.arraycopy(bits, 0, result, 1, bits.length);

        return result;
    }

    public static String bitsToString(byte[] encodedText)
            throws UnsupportedEncodingException {
        if (encodedText == null )
            throw new IllegalArgumentException("Invalid input bits");

        Charset encoding = getEncoding(encodedText[0]);
        int charSize = getCharacterSize(encoding);

        byte[] bits = new byte[encodedText.length - 1];
        System.arraycopy(encodedText, 1, bits, 0, bits.length);

        byte[] decodedBytes = new byte[bits.length / 8];

        for (int i = 0; i < decodedBytes.length; i++) {
            byte decodedByte = 0;

            for (int j = 0; j < charSize; j++) {
                byte bit = bits[i * charSize + j];

                if (bit == 127)
                    break;

                decodedByte |= (byte) (bit << ((charSize - 1) - j));
            }

            decodedBytes[i] = decodedByte;
        }

        try {
            return new String(decodedBytes, encoding);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static byte getCharsetDeterminant(Charset charset)
            throws UnsupportedEncodingException {
        if (charset == StandardCharsets.UTF_8)
            return 0;
        if (charset == StandardCharsets.US_ASCII)
            return 1;

        throw new UnsupportedEncodingException();
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
            return 8;

        throw new UnsupportedEncodingException();
    }

    private static Charset getEncoding(byte encodingByte) {
        return switch (encodingByte) {
            case 0 -> StandardCharsets.UTF_8;
            case 1 -> StandardCharsets.US_ASCII;
            default -> throw new IllegalArgumentException("Unexpected value: " + encodingByte);
        };
    }

}
