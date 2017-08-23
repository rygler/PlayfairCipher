package com.playfaircipher;

import com.google.common.primitives.Chars;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

class Playfair {

    static String encrypt(String message, String key) {
        message = getAdjustedMessage(message);
        key = getAdjustedKey(key);
        char[][] charMatrix = buildMatrix(key);
        return encodeStringWithMatrix(message, charMatrix, true);
    }

    static String decrypt(String ciphertext, String key) {
        key = getAdjustedKey(key);
        char[][] charMatrix = buildMatrix(key);
        return encodeStringWithMatrix(ciphertext, charMatrix, false);
    }

//    static boolean decrypt(String ciphertext) {
//        return false;
//    }

    private static String getAdjustedMessage(String message) {
        String newMessage = message.toUpperCase().replaceAll("[^A-Z]", "").replaceAll("(.)\\1", "$1" + 'X' + "$1");
        if (newMessage.length() % 2 == 0) {
            return newMessage;
        }
        newMessage += 'Q';
        return newMessage;
    }

    private static String encodeStringWithMatrix(String message, char[][] charMatrix, boolean encrypting) {
        StringBuilder builder = new StringBuilder(message.length());
        for (int i = 0; i < message.length(); i += 2) {
            builder.append(encodePair(charMatrix, message.charAt(i), message.charAt(i + 1), encrypting));
        }
        return builder.toString();
    }



    private static String encodePair(char[][] charMatrix, char a, char b, boolean encrypting) {
        StringBuilder builder = new StringBuilder(2);
        int rowA = -1, colA = -1, rowB = -1, colB = -1;
        char c, d;
        int rowC, colC, rowD, colD;

        // Find positions of letters
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (charMatrix[row][col] == a) {
                    rowA = row;
                    colA = col;
                }
                if (charMatrix[row][col] == b) {
                    rowB = row;
                    colB = col;
                }
            }
        }

        if (rowA == rowB) { // Letters are on same row
            rowC = rowA;
            rowD = rowA;

            colC = encrypting ? (colA + 1) % 5 : (colA == 0 ? 4 : colA - 1);
            colD = encrypting ? (colB + 1) % 5 : (colB == 0 ? 4 : colB - 1);
        } else if (colA == colB) { // Letters are on same column
            colC = colA;
            colD = colA;

            rowC = encrypting ? (rowA + 1) % 5 : (rowA == 0 ? 4 : rowA - 1);
            rowD = encrypting ? (rowB + 1) % 5 : (rowB == 0 ? 4 : rowB - 1);
        } else {
            rowC = rowA;
            colC = colB;
            rowD = rowB;
            colD = colA;
        }
        c = charMatrix[rowC][colC];
        d = charMatrix[rowD][colD];
        return builder.append(new char []{c, d}).toString();
    }

    private static char[][] buildMatrix(String key) {
        HashSet<Character> usedChars = new HashSet<>(25);
        char[][] charMatrix = new char[5][5];
        String newKey = getAdjustedKey(key);
        String newAlphabet = getNewAlphabet(newKey);
        int counter = 0;
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                charMatrix[row][col] = newAlphabet.charAt(counter);
                counter++;
            }
        }
        return charMatrix;
    }

    private static String getNewAlphabet(String key) {
        StringBuilder builder = new StringBuilder(26);
        Set<Character> usedChars = new HashSet<>(Chars.asList(key.toCharArray()));
        builder.append(key);
        String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        for (char c : alphabet.toCharArray()) {
            if (!usedChars.contains(c)) {
                builder.append(c);
                usedChars.add(c);
            }
        }
        return builder.toString();
    }

    private static String getAdjustedKey(String key) {
        String newKey = key.toUpperCase().replaceAll("[^A-Z]", "").replaceAll("J", "I");
        Set<Character> charSet = new LinkedHashSet<>();
        for (char c : newKey.toCharArray()) {
            charSet.add(c);
        }

        StringBuilder builder = new StringBuilder(charSet.size());
        for (Character character : charSet) {
            builder.append(character);
        }
        return builder.toString();
    }


}
