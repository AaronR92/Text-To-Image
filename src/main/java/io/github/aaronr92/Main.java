package io.github.aaronr92;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        byte[] bits = StringUtils.stringToBits("Hello World!");
        System.out.print("Bits: ");
        for (byte b : bits) {
            System.out.print(b);
        }
        System.out.println();

        System.out.println(StringUtils.bitsToString(bits));
    }
}