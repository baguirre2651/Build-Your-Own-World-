package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Generates a world with a given seed.
 */
public class World implements Serializable {
    /**
     * A seed for the pseudo-random world.
     */
    private final long SEED;
    /**
     * Random generator.
     */
    private final Random RANDOM;
    /**
     * An array of all the valid rooms created.
     */
    private final ArrayList<Room> allRooms;
    /**
     * A buffer used in between rooms to avoid overlap.
     */
    private final int BUFFER = 2;
    /**
     * The constructor creates a pseudo-random number of rooms with different varying dimensions.
     * After the rooms are created, all of the rooms have a hallway created to connect them.
     */

    Point avatarPoint;
    Avatar avatar;
    private TETile[][] renderedWorld;
    /**
     * The world's width.
     */
    private int WIDTH;
    /**
     * The world's height.
     */
    private int HEIGHT;
    /**
     * The min and max x pos coordinates for potential points.
     */
    private int leftBound;
    private int rightBound;
    /**
     * The min and max y pos coordinates for potential points.
     */
    private int lowerBound;
    private int upperBound;
    /**
     * The possible min and max pos widths of a room.
     */
    private int posMaxWidth;
    private int posMinWidth;
    /**
     * The possible min and max pos heights of a room.
     */
    private int possibleMaxHeight;
    private int possibleMinHeight;

    public World(TETile[][] finalWorldFrame, long seed) {
        init(finalWorldFrame);
        SEED = seed;
        RANDOM = new Random(SEED);
        allRooms = new ArrayList<>();
        createStructures();
        connectAllRooms();
        avatar = avatar();
    }
    //n7193300625454684331saaawasdaawdwsd
    //n7193300625454684331saaawasdaawd:q
    //5066


    /**
     * Creates the blank world.
     */
    public void init(TETile[][] finalWorldFrame) {
        setParameters(finalWorldFrame);
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                this.renderedWorld[x][y] = Tileset.WATER;
            }
        }
    }




    /**
     * Creates a pseudo-random number of rooms at different points.
     * The method sets a number indicating the number of room attempts.
     * From a pseudo-random point, width, and height of a room,
     * a  checks a potential room for any overlap.
     * The method appends the room to a list of existing rooms.
     */
    public void createStructures() {
        int numRooms = RANDOM.nextInt((30 - 20)) + 20;
        while (numRooms > 0) {
            Point randomPoint = randomPoint();
            int randomWidth = randomWidth();
            int randomHeight = randomHeight();

            if (validRoom(allRooms, randomPoint, randomWidth, randomHeight)) {
                Room newRoom = new Room(randomPoint, renderedWorld, randomWidth, randomHeight);
                allRooms.add(newRoom);
            }
            numRooms--;
        }
    }

    /**
     * Builds a path connecting room1 (temp1) and room2 (temp2).
     */
    public void connectRooms(Room rm1, Room rm2) {
        new Hallway(rm1, rm2, renderedWorld);
    }

    /**
     * Validator checks whether a room significantly overlaps with another room.
     * The method creates the four corners for our potential room.
     * All four corners are checked for overlap.
     * Creates a room using the checkIsInside method.
     */
    private boolean validRoom(ArrayList<Room> rooms, Point possibleNewRoom,
                              int randomWidth, int randomHeight) {
        boolean exists = true;
        for (Room validRoom : rooms) {
            int posMinX = possibleNewRoom.getX()
                    - (randomWidth / 2);
            int posMaxX = possibleNewRoom.getX()
                    + (int) Math.ceil((double) randomWidth / 2);
            int posMinY = possibleNewRoom.getY() - (randomHeight / 2);
            int posMaxY = possibleNewRoom.getY()
                    + (int) Math.ceil((double) randomHeight / 2);

            Point bottomLeft = new Point(posMinX, posMinY);
            Point upperLeft = new Point(posMaxX, posMinY);
            Point bottomRight = new Point(posMaxX, posMaxY);
            Point upperRight = new Point(posMinX, posMaxY);

            if (checkInside(bottomLeft, validRoom)
                    || checkInside(upperLeft, validRoom)
                    || checkInside(bottomRight, validRoom)
                    || checkInside(upperRight, validRoom)) {
                exists = false;
            }
        }
        return exists;
    }

    /**
     * Builds the dimensions around a room and compares the
     * X and Y coordinate of the potential new room's corner.
     * With the dimensions of an existing room.
     */

    public boolean checkInside(Point possibleCorner, Room validRoom) {
        return validRoom.getMinX() - BUFFER < possibleCorner.getX()
                && possibleCorner.getX() < validRoom.getMaxX() + BUFFER
                && validRoom.getMinY() - BUFFER < possibleCorner.getY()
                && possibleCorner.getY() < validRoom.getMaxY() + BUFFER;
    }

    /**
     * Returns a random point within the bounds of the world's width and height
     */
    public Point randomPoint() {
        return new Point(RANDOM.nextInt(rightBound - leftBound) + leftBound,
                RANDOM.nextInt(upperBound - lowerBound) + lowerBound);
    }

    /**
     * Returns a random width for a room between
     * the possibleMinWidth and the possibleMaxWidth.
     */
    public int randomWidth() {
        return RANDOM.nextInt(posMaxWidth - posMinWidth) + posMinWidth;
    }

    /**
     * Returns a random height for a room between
     * of MinHeight and the MaxHeight possibly.
     */
    public int randomHeight() {
        return RANDOM.nextInt(possibleMaxHeight - possibleMinHeight) + possibleMinHeight;
    }

    /**
     * Sets the width and height dimensions of our world.
     */
    public void setParameters(TETile[][] finalWorldFrame) {
        this.WIDTH = finalWorldFrame.length;
        this.HEIGHT = finalWorldFrame[0].length;
        this.renderedWorld = new TETile[WIDTH][HEIGHT];

        this.posMaxWidth = 10;
        this.posMinWidth = 3;
        this.possibleMaxHeight = 10;
        this.possibleMinHeight = 3;

        this.leftBound = posMaxWidth / 2;
        this.rightBound = WIDTH - posMaxWidth / 2;
        this.lowerBound = possibleMaxHeight / 2;
        this.upperBound = HEIGHT - possibleMaxHeight / 2;
    }


    /**
     * Iterates through our list of rooms, the method connects each room to each other.
     * Directly connected the first and last rooms.
     */
    public void connectAllRooms() {
        Room nextRoom = allRooms.get(0);
        for (int i = 1; i < allRooms.size(); i++) {
            connectRooms(nextRoom, allRooms.get(i));
            nextRoom = allRooms.get(i);
        }
        connectRooms(allRooms.get(0), nextRoom);
        avatarPoint = (allRooms.get(0).getPoint());
    }

    /**returns generated world used for StdDraw **/

    public TETile[][] getRenderedWorld() {
        return this.renderedWorld;
    }

    /** Avatar position **/
    public Avatar avatar() {
        return new Avatar(avatarPoint, this);
    }

    /** Updates the Avatar's Movement **/

    public void updatePlayerPosition(Point oldPosition, Point currentLocation) {
        renderedWorld[oldPosition.getX()][oldPosition.getY()] = Tileset.FLOOR;
        int playerX = currentLocation.getX();
        int playerY = currentLocation.getY();
        avatarPoint = new Point(playerX, playerY);
        renderedWorld[currentLocation.getX()][currentLocation.getY()] = Tileset.AVATAR;
        avatar();
    }
}
