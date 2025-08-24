package com.mca.translator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MorseDictionary {

    private static final Map<Character, boolean[]> morseAlphabet = createMorseDictionary();

    private static Map<Character, boolean[]> createMorseDictionary() {
        Map<Character, boolean[]> morseAlphabet = new HashMap<>();
        morseAlphabet.put('A', new boolean[] {false, true});
        morseAlphabet.put('B', new boolean[] {true, false, false, false});
        morseAlphabet.put('C', new boolean[] {true, false, true, false});
        morseAlphabet.put('D', new boolean[] {true, false, false});
        morseAlphabet.put('E', new boolean[] {false});
        morseAlphabet.put('F', new boolean[] {false, false, true, false});
        morseAlphabet.put('G', new boolean[] {true, true, false});
        morseAlphabet.put('H', new boolean[] {false, false, false, false});
        morseAlphabet.put('I', new boolean[] {false, false});
        morseAlphabet.put('J', new boolean[] {false, true, true, true});
        morseAlphabet.put('K', new boolean[] {true, false, true});
        morseAlphabet.put('L', new boolean[] {false, true, false, false});
        morseAlphabet.put('M', new boolean[] {true, true});
        morseAlphabet.put('N', new boolean[] {true, false});
        morseAlphabet.put('O', new boolean[] {true, true, true});
        morseAlphabet.put('P', new boolean[] {false, true, true, false});
        morseAlphabet.put('Q', new boolean[] {true, true, false, true});
        morseAlphabet.put('R', new boolean[] {false, true, false});
        morseAlphabet.put('S', new boolean[] {false, false, false});
        morseAlphabet.put('T', new boolean[] {true});
        morseAlphabet.put('U', new boolean[] {false, false, true});
        morseAlphabet.put('V', new boolean[] {false, false, false, true});
        morseAlphabet.put('W', new boolean[] {false, true, true});
        morseAlphabet.put('X', new boolean[] {true, false, false, true});
        morseAlphabet.put('Y', new boolean[] {true, false, true, true});
        morseAlphabet.put('Z', new boolean[] {true, true, false, false});
        morseAlphabet.put(' ', new boolean[] {});

        return morseAlphabet;
    }

    public static boolean[] getMorseCode(char character) {
        if (character <= 'z' && character >= 'a') {
            character -= 'a' - 'A';
        }
        return morseAlphabet.get(character);
    }

    public static char getChar(boolean[] morseCode) {
        for (Map.Entry<Character, boolean[]> entry : morseAlphabet.entrySet()) {
            if (Arrays.equals(entry.getValue(), morseCode)) {
                return entry.getKey();
            }
        }
        return '!';
    }

    public static boolean isSymbolSupported(char symbol) {
        if (symbol >= 'a' && symbol <= 'z') {
            symbol -= 'a' - 'A';
        }
        return morseAlphabet.containsKey(symbol);
    }

}
