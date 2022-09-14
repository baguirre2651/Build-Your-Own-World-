package byow.Core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * saves the game using a HashMap
 **/

public class GameFile implements Serializable {
    private final Map<String, World> gameStorage;


    public GameFile() {
        this.gameStorage = new HashMap<>();
    }

    public void saveGame(World world) {
        this.gameStorage.put("world", world);
    }

    public World loadGame() {
        return this.gameStorage.get("world");
    }

}
