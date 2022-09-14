package byow.Core;

import edu.princeton.cs.algs4.StdDraw;

import java.io.Serializable;

public class KeyboardInput implements Serializable {
    public char getNextKey() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toUpperCase(StdDraw.nextKeyTyped());
                return c;
            }
        }
    }

    public boolean possibleNextInput() {
        return true;
    }
}
