package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

public class Engine {
    public static final int WIDTH = 90;
    public static final int HEIGHT = 50;
    TERenderer ter = new TERenderer();

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     *
     * @return
     */
    public TETile[][] interactWithKeyboard() {
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        KeyboardInputInteraction myKeyboardInput = new
                KeyboardInputInteraction(ter, finalWorldFrame);
        return finalWorldFrame;
    }


    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, both of these calls:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
//        ter.initialize(WIDTH,HEIGHT);
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        StringInputInteraction myStringInput = new StringInputInteraction(finalWorldFrame, input);
//        this.ter.renderFrame(myStringInput.worldTileRepresentation());
        return myStringInput.worldTileRepresentation();
    }
}

