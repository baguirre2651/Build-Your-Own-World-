package byow.Core;

import byow.TileEngine.TETile;

import java.io.File;
import java.io.IOException;


/**
 * @source: stack overflow for BigInteger and getByte integration for Seed
 **/
public class StringInputInteraction {
    //2d array of tiles represents world state
    StringInput stringInput;
    World stringGameWorld;
    String SEED;
    long longSEED;
    File worldFile;
    GameFile gameFile;
    Avatar player;


    public StringInputInteraction(TETile[][] stringWorld, String s) {
        stringInput = new StringInput(s);
        SEED = s.substring(1, s.indexOf('s'));
        longSEED = Long.parseLong(SEED);
        char nextKey = stringInput.getNextKey();
        while (stringInput.possibleNextInput()) {
            longSEED += nextKey;
            nextKey = stringInput.getNextKey();
        }
        stringGameWorld = new World(stringWorld, longSEED);
    }

    public TETile[][] worldTileRepresentation() {
        return stringGameWorld.getRenderedWorld();
    }


    public void saveWorld(World world) {
        createFile();
        gameFile = new GameFile();
        gameFile.saveGame(world);
        PersistenceUtils.writeObject(gameFile, worldFile);
    }

    public long longSEED(String seed) {
        return Long.parseLong(seed);
    }

    public void createFile() {
        if (!worldFile.exists()) {
            try {
                this.worldFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public World loadGame() {
        gameFile = (GameFile) PersistenceUtils.readObject(worldFile);
        return gameFile.loadGame();
    }

    public void controller(Character direction, Avatar givenAvatar) {
        this.player = givenAvatar;
        if (direction == 'W') {
            player.travelNorth();
        }
        if (direction == 'A') {
            player.travelWest();
        }
        if (direction == 'S') {
            player.travelSouth();
        }
        if (direction == 'D') {
            player.travelEast();
        }
    }

}
