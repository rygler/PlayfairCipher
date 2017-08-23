package com.playfaircipher;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class Main {

    @Parameter(names = {"--encrypt", "-e"}, description = "A string to encrypt")
    String plaintext;
    @Parameter(names = {"--decrypt", "-d"}, description = "A string to decrypt")
    String ciphertext;
    @Parameter(names = {"--key", "-k"}, description = "The key")
    String key;


    public static void main(String... args) {
        Main main = new Main();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);
        main.run();
    }

    private void run() {
        if (plaintext != null && key != null) {
            System.out.println(Playfair.encrypt(plaintext, key));
        } else if (ciphertext != null && key != null) {
            System.out.println(Playfair.decrypt(ciphertext, key));
        }
//        else if (ciphertext != null) {
//            System.out.println(Playfair.decrypt(ciphertext));
//        }
    }
}

