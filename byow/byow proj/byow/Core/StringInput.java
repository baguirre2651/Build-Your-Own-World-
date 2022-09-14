package byow.Core;

import java.io.Serializable;
import java.util.Locale;

public class StringInput implements Serializable {
    private final String input;
    private int index;


    public StringInput(String s) {
        index = 0;
        input = s.toUpperCase(Locale.ROOT);
    }

    public char getNextKey() {
        char returnChar = input.charAt(index);
        index += 1;
        return returnChar;
    }

    public boolean possibleNextInput() {
        return index < input.length();
    }

    public String rest() {
        return input.substring(index);
    }
}


