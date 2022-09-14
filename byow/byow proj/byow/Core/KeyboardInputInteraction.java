package byow.Core;


import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class KeyboardInputInteraction implements Serializable {
    private final int WIDTH;
    private final int HEIGHT;
    private final TERenderer renderer;
    private final KeyboardInput inputSource;
    private final Character[] seedCharacters = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private final HashSet<Character> validSeedKeys = new HashSet<>(Arrays.asList(seedCharacters));
    File worldFile;
    private String SEED;
    private World stringGameWorld;
    private Avatar player;
    private GameFile gameFile;

    /** How everything interacts with Keyboard **/

    public KeyboardInputInteraction(TERenderer ter, TETile[][] stringWorld) {
        WIDTH = stringWorld.length;
        HEIGHT = stringWorld[0].length;
        this.renderer = ter;
        worldFile = Paths.get(".", "myWorlds.txt").toFile();
        init();
        renderStart();
        inputSource = new KeyboardInput();

        while (true) {
            char nextKey = inputSource.getNextKey();
            if (nextKey == 'N') {
                runKeyboardGame(ter, stringWorld);
            }
            if (nextKey == 'E') {
                juegoEspanol();
            }
            if (nextKey == 'I') {
                renderStart();
            }
            if (nextKey == 'C') {
                spanishN(ter, stringWorld);
            }
            if (nextKey == 'R') {
                randomWorld(ter, stringWorld);
            }

            if (nextKey == 'L') {
                if (this.worldFile.exists()) {
                    stringGameWorld = loadGame();
                    player = stringGameWorld.avatar();
                    ter.initialize(WIDTH, HEIGHT);
                    while (true) { //added
                        mouse(ter);
                        ter.renderFrame(stringGameWorld.getRenderedWorld());
                        nextKey = inputSource.getNextKey();
                        if (nextKey == ':') {
                            nextKey = inputSource.getNextKey();
                            if (nextKey == 'Q') {
                                saveWorld(stringGameWorld);
                                System.exit(0);
                            }
                            if (nextKey == 'M') {
                                saveWorld(stringGameWorld);
                                new KeyboardInputInteraction(ter, stringWorld);
                            }
                            if (nextKey == 'V') {
                                do {
                                    ter.renderFrame(nightVision(stringGameWorld));
                                    controller(nextKey, player);
                                    nextKey = inputSource.getNextKey();
                                    if (nextKey == ':') {
                                        nextKey = inputSource.getNextKey();
                                    }
                                } while (nextKey != 'V');
                            }
                        }
                        controller(nextKey, player);
                        ter.renderFrame((stringGameWorld.getRenderedWorld()));
                    }
                }
            }
            if (nextKey == 'Q') {
                System.exit(0);
            }
        }
    }

    public void runKeyboardGame(TERenderer ter, TETile[][] stringWorld) {
        seedRenderer();
        stringGameWorld = new World(stringWorld, longSEED(SEED));
        player = stringGameWorld.avatar();
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(stringGameWorld.getRenderedWorld());

        while (true) {
            mouse(ter);
            char nextKey = inputSource.getNextKey();
            if (nextKey == ':') {
                nextKey = inputSource.getNextKey();
                if (nextKey == 'Q') {
                    saveWorld(stringGameWorld);
                    System.exit(0);
                }
                if (nextKey == 'M') {
                    saveWorld(stringGameWorld);
                    new KeyboardInputInteraction(ter, stringWorld);
                }
                if (nextKey == 'V') {
                    do {
                        ter.renderFrame(nightVision(stringGameWorld));
                        controller(nextKey, player);
                        nextKey = inputSource.getNextKey();
                        if (nextKey == ':') {
                            nextKey = inputSource.getNextKey();
                        }
                    } while (nextKey != 'V');
                }
                if (nextKey == 'M') {
                    saveWorld(stringGameWorld);
                    new KeyboardInputInteraction(ter, stringWorld);
                }
            }
            controller(nextKey, player);
            ter.renderFrame((stringGameWorld.getRenderedWorld()));
        }
    }

    /** option to make a new randomized world that starts in darkness **/

    public void randomWorld(TERenderer ter, TETile[][] stringWorld) {
        Random longRan = new Random();
        stringGameWorld = new World(stringWorld, longRan.nextLong());
        player = stringGameWorld.avatar();
        ter.initialize(WIDTH, HEIGHT);
        while (true) {
            ter.renderFrame(nightVision(stringGameWorld));
            char nextKey = inputSource.getNextKey();
            if (nextKey == ':') {
                nextKey = inputSource.getNextKey();
                if (nextKey == 'Q') {
                    saveWorld(stringGameWorld);
                    System.exit(0);
                }
                if (nextKey == 'M') {
                    saveWorld(stringGameWorld);
                    new KeyboardInputInteraction(ter, stringWorld);
                }
                if (nextKey == 'V') {
                    ter.renderFrame(stringGameWorld.getRenderedWorld());
                    controller(nextKey, player);
                    nextKey = inputSource.getNextKey();
                }
            }
            controller(nextKey, player);
            ter.renderFrame((stringGameWorld.getRenderedWorld()));
        }
    }

    /** HUD Display **/

    public void hudDisplay() {
        Font error = new Font("Roboto", Font.BOLD, 20);
        StdDraw.setFont(error);
        StdDraw.setPenColor(Color.ORANGE);
        StdDraw.text(this.WIDTH / 4, this.HEIGHT - 2, "Toggle Night (:V)");
        StdDraw.text(this.WIDTH / 12, this.HEIGHT - 5, "Quit Game (:Q)");
        StdDraw.text(this.WIDTH / 10, this.HEIGHT - 2, "Main Menu (:M)");
        StdDraw.show();

        Font date = new Font("Courier New", Font.BOLD, 20);
        StdDraw.setFont(date);
        StdDraw.setPenColor(Color.WHITE);
        LocalDateTime dateTime = LocalDateTime.now();
        LocalTime localTime = dateTime.toLocalTime();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDate time = LocalDate.now();
        StdDraw.textRight(this.WIDTH - 12, this.HEIGHT - 2, " Today is:" + time
                + " Current Time: " + localTime.toString());
        StdDraw.show();
        StdDraw.pause(100);
    }

    /** mouse uid **/

    public void mouse(TERenderer ter) {
        while (!StdDraw.hasNextKeyTyped()) {
            StdDraw.clear();
            ter.renderFrame(stringGameWorld.getRenderedWorld());
            StdDraw.setPenColor(Color.YELLOW);
            StdDraw.text(this.WIDTH - 10, 36, stringGameWorld.getRenderedWorld()
                    [(int) StdDraw.mouseX()][(int) StdDraw.mouseY()].description());
            hudDisplay();
            StdDraw.show();
            StdDraw.pause(100);
        }
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

    public void saveWorld(World world) {
        createFile();
        gameFile = new GameFile();
        gameFile.saveGame(world);
        PersistenceUtils.writeObject(gameFile, worldFile);
    }

    public World loadGame() {
        gameFile = (GameFile) PersistenceUtils.readObject(worldFile);
        return gameFile.loadGame();
    }

    public TETile[][] nightVision(World game) {
        TETile[][] altVision = new TETile[WIDTH][HEIGHT];
        int xMin = player.getxPos() - 5;
        int xMax = player.getxPos() + 5;
        int yMin = player.getyPos() - 5;
        int yMax = player.getyPos() + 5;

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if ((xMin < i && i < xMax && yMin < j && j < yMax)) {
                    altVision[i][j] = game.getRenderedWorld()[i][j];
                } else {
                    altVision[i][j] = Tileset.NOTHING;
                }
            }
        }
        return altVision;
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


    public void seedRenderer() {
        enterSeedScreen();
        boolean continueSeed = true;
        SEED = "";
        while (continueSeed) {
            char nextKey = inputSource.getNextKey();
            if (nextKey == 'S') {
                continueSeed = false;
            } else if (validSeedKeys.contains(nextKey)) {
                SEED += nextKey;
                newSeedInputFrame(SEED);
            } else {
                invalidSeedScreen();
                StdDraw.pause(700);
            }
            StdDraw.show();
        }
    }


    public long longSEED(String seed) {
        return Long.parseLong(seed);
    }


    public void init() {
        StdDraw.setCanvasSize(this.WIDTH * 16, this.HEIGHT * 16);
        StdDraw.setXscale(0, this.WIDTH);
        StdDraw.setYscale(0, this.HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    public void renderStart() {
        Font title = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(title);
        StdDraw.clear(Color.ORANGE);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT - 5, "==== THE GAME====");
        Font menu = new Font("Comic Sans", Font.BOLD, 18);
        Font author = new Font("Ariel", Font.CENTER_BASELINE, 20);

        StdDraw.setFont(author);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT - 10, "By: Bryan Aguirre");


        StdDraw.setFont(menu);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT - 15, "New Game(N)");
        StdDraw.text(this.WIDTH / 2, this.HEIGHT - 17, "Load Game(L)");
        StdDraw.text(this.WIDTH / 2, this.HEIGHT - 19, "Dark World(R)");
        StdDraw.text(this.WIDTH / 2, this.HEIGHT - 21, "Quit(Q)");
        StdDraw.text(this.WIDTH / 2, this.HEIGHT - 30, "Español(É)");
        StdDraw.show();

    }


    public void newSeedInputFrame(String s) {
        enterSeedScreen();
        Font font = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(WIDTH / 2, this.HEIGHT - 15, s);
        StdDraw.show();
    }

    public void invalidSeedScreen() {
        Font error = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(error);
        StdDraw.clear(Color.WHITE);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT - 5, "Invalid Seed!");
        StdDraw.show();
    }


    public void enterSeedScreen() {
        Font title = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(title);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT - 5, "Enter a seed!");
        Font options = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(options);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT - 13, "Seed : ");
        StdDraw.show();
    }


    /**
     * interface in spanish
     **/

    public void juegoEspanol() {
        Font title = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(title);
        StdDraw.clear(Color.ORANGE);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT - 5,
                "====CŚ61BL: El' Juegó (Version Español!) ====");
        Font options = new Font("Comic Sans", Font.BOLD, 18);
        Font author = new Font("Ariel", Font.CENTER_BASELINE, 20);

        StdDraw.setFont(author);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT - 10, "Juego De : Bryan Aguirrè");


        StdDraw.setFont(options);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT - 15, "Comienca Júegó(C)");
        StdDraw.text(this.WIDTH / 2, this.HEIGHT - 17, "Cargar Júegó(L)");
        StdDraw.text(this.WIDTH / 2, this.HEIGHT - 19, "Mundo Oscuro (R)");
        StdDraw.text(this.WIDTH / 2, this.HEIGHT - 23, "Quitar(Q)");
        StdDraw.text(this.WIDTH / 2, this.HEIGHT - 30, "Inglés(I)");
        StdDraw.show();

    }


    public void pongaSeed() {
        Font title = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(title);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT - 5, "Ponga Une Seed Por Favor!");
        Font options = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(options);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT - 13, "Seed : ");
        StdDraw.show();
    }

    public void malSeed() {
        Font error = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(error);
        StdDraw.clear(Color.WHITE);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT - 5, "JAJAJAJAJA NO!");
        StdDraw.show();
    }

    public void nuevoSeedformat(String s) {
        pongaSeed();
        Font font = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(WIDTH / 2, this.HEIGHT - 15, s);
        StdDraw.show();
    }

    public void pongaSeedFrame() {
        pongaSeed();
        boolean continueSeed = true;
        SEED = "";
        while (continueSeed) {
            char nextKey = inputSource.getNextKey();
            if (nextKey == 'S') {
                continueSeed = false;
            } else if (validSeedKeys.contains(nextKey)) {
                SEED += nextKey;
                nuevoSeedformat(SEED);
            } else {
                malSeed();
                StdDraw.pause(800);
            }
            StdDraw.show();
        }
    }

    public void spanishN(TERenderer ter, TETile[][] stringWorld) {
        pongaSeedFrame();
        stringGameWorld = new World(stringWorld, longSEED(SEED));
        player = stringGameWorld.avatar();
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(nightVision(stringGameWorld));
        while (true) {
            spanishMouse(ter);
            char nextKey = inputSource.getNextKey();
            if (nextKey == ':') {
                nextKey = inputSource.getNextKey();
                if (nextKey == 'Q') {
                    saveWorld(stringGameWorld);
                    System.exit(0);
                }
                if (nextKey == 'M') {
                    saveWorld(stringGameWorld);
                    new KeyboardInputInteraction(ter, stringWorld);
                }
                if (nextKey == 'V') {
                    do {
                        ter.renderFrame(nightVision(stringGameWorld));
                        controller(nextKey, player);
                        nextKey = inputSource.getNextKey();
                        if (nextKey == ':') {
                            nextKey = inputSource.getNextKey();
                        }
                    } while (nextKey != 'V');
                }
                if (nextKey == 'M') {
                    saveWorld(stringGameWorld);
                    new KeyboardInputInteraction(ter, stringWorld);
                }
            }
            controller(nextKey, player);
            ter.renderFrame((stringGameWorld.getRenderedWorld()));
        }
    }

    public void spanishMouse(TERenderer ter) {
        while (!StdDraw.hasNextKeyTyped()) {
            StdDraw.clear();
            ter.renderFrame(stringGameWorld.getRenderedWorld());
            StdDraw.setPenColor(Color.YELLOW);
            StdDraw.text(this.WIDTH - 10, 36, stringGameWorld.getRenderedWorld()
                    [(int) StdDraw.mouseX()][(int) StdDraw.mouseY()].description());
            spanishDisplay();
            StdDraw.show();
            StdDraw.pause(100);
        }
    }

    public void spanishDisplay() {
        Font error = new Font("Roboto", Font.BOLD, 20);
        StdDraw.setFont(error);
        StdDraw.setPenColor(Color.ORANGE);
        StdDraw.text(this.WIDTH / 4, this.HEIGHT - 2, "Togale Noche (:V)");
        StdDraw.text(this.WIDTH / 12, this.HEIGHT - 5, "Quita Juego (:Q)");
        StdDraw.text(this.WIDTH / 10, this.HEIGHT - 2, "Menu (:M)");
        StdDraw.show();

        Font date = new Font("Courier New", Font.BOLD, 20);
        StdDraw.setFont(date);
        StdDraw.setPenColor(Color.WHITE);
        LocalDateTime dateTime = LocalDateTime.now();
        LocalTime localTime = dateTime.toLocalTime();
        DateTimeFormatter format = DateTimeFormatter.
                ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDate time = LocalDate.now();
        StdDraw.textRight(this.WIDTH - 12, this.HEIGHT - 2, " Ahora Es:" + time
                + " El Tiempo Es: " + localTime.toString());
        StdDraw.show();
        StdDraw.pause(100);
    }
}
