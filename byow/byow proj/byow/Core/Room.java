package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;

public class Room implements Serializable {

    /**
     * the width of the room
     **/

    private final int WIDTH;

    /**
     * the height of the room
     **/
    private final int HEIGHT;

    /**
     * the center point of the room
     **/

    private final Point center;

    /**
     * the horizontal construction of floor tiles
     **/
    private int startX;

    private int endX;
    /**
     * the vertical construction of floor tiles
     **/
    private int startY;

    private int endY;


    /**
     * Creates a room around the point in the center with a random width and height
     **/
    public Room(Point root, TETile[][] finalWorldFrame, int roomWidth, int roomHeight) {
        center = root;
        WIDTH = roomWidth;
        HEIGHT = roomHeight;
        generateDimensions();
        setFloors(finalWorldFrame);
        setWalls(finalWorldFrame);
    }

    /**
     * Calculates the corner from the center point and sets the parameters/dimensions of a room
     **/
    public void generateDimensions() {
        startX = center.getX() - (HEIGHT / 2);
        endX = center.getX() + (int) Math.ceil((double) WIDTH / 2);

        startY = center.getY() - (HEIGHT / 2);
        endY = center.getY() + (int) Math.ceil((double) HEIGHT / 2);
    }


    /**
     * Sets floor tiles of the room
     **/

    public void setFloors(TETile[][] finalWorldFrame) {
        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                finalWorldFrame[x][y] = Tileset.FLOOR;
            }
        }
    }

    /**
     * sets the walls of the room
     * Checks if walls are valid
     **/

    public void setWalls(TETile[][] finalWorldFrame) {
        for (int y = startY; y < endY; y++) {
            if (finalWorldFrame[startX - 1][y] != Tileset.FLOOR) {
                finalWorldFrame[startX - 1][y] = Tileset.WALL;
            }
            if (finalWorldFrame[endX][y] != Tileset.FLOOR) {
                finalWorldFrame[endX][y] = Tileset.WALL;
            }
        }
        for (int x = startX; x <= endX; x++) {
            if (finalWorldFrame[x][startY - 1] != Tileset.FLOOR) {
                finalWorldFrame[x][startY - 1] = Tileset.WALL;
            }
            if (finalWorldFrame[x][endY] != Tileset.FLOOR) {
                finalWorldFrame[x][endY] = Tileset.WALL;
            }
        }
        if (finalWorldFrame[startX - 1][startY - 1] != Tileset.FLOOR) {
            finalWorldFrame[startX - 1][startY - 1] = Tileset.WALL;
        }
    }

    /**
     * Returns The Center Point of a Room
     **/
    public Point getPoint() {
        return center;
    }

    /**
     * Room's Width
     **/
    public int getWidth() {
        return WIDTH;
    }

    /**
     * Room's Height
     **/
    public int getHeight() {
        return HEIGHT;
    }

    /**
     * MinX of a Room
     **/
    public int getMinX() {
        return startX;
    }

    /**
     * MaxX of a Room
     **/
    public int getMaxX() {
        return endX;
    }

    /**
     * MinY of a Room
     **/
    public int getMinY() {
        return startY;
    }

    /**
     * MaxY of a Room
     **/
    public int getMaxY() {
        return endY;
    }
}
