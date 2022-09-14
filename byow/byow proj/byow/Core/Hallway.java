package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;

public class Hallway implements Serializable {

    /**
     * Where the world hallways are rendered from.
     **/
    private final TETile[][] renderedWorld;
    /**
     * Start of the horizontal path.
     **/
    private int startX;
    private int endX;
    /**
     * Start of the vertical path.
     **/

    private int startY;
    private int endY;


    /**
     * Makes a path between 2 rooms.
     *
     * @param temp1:           room1.
     * @param temp2:           room2.
     * @param finalWorldFrame: rendered world frame.
     **/
    public Hallway(Room temp1, Room temp2, TETile[][] finalWorldFrame) {
        renderedWorld = finalWorldFrame;
        makeXHallway(temp1, temp2);
        makeYHallway(temp1, temp2);
    }

    /**
     * The X coordinate hallway between 2 rooms.
     * Build's walls and makes sure a wall is not placed on a floor tile that already exists.
     **/
    public void makeXHallway(Room temp1, Room temp2) {
        int distance = temp1.getPoint().getXDistance(temp2.getPoint());
        if (distance < 0) {
            startX = temp1.getPoint().getX();
            endX = temp1.getPoint().getX() - distance;
        } else {
            startX = temp1.getPoint().getX() - distance;
            endX = temp1.getPoint().getX();
        }
        for (int x = startX - 1; x <= endX + 1; x++) {
            if (renderedWorld[x][temp1.getPoint().getY() - 1] != Tileset.FLOOR) {
                renderedWorld[x][temp1.getPoint().getY() - 1] = Tileset.WALL;
            }
            if (renderedWorld[x][temp1.getPoint().getY() + 1] != Tileset.FLOOR) {
                renderedWorld[x][temp1.getPoint().getY() + 1] = Tileset.WALL;
            }
        }
        for (int x = startX; x <= endX; x++) {
            renderedWorld[x][temp1.getPoint().getY()] = Tileset.FLOOR;
        }
    }

    /**
     * The Y coordinate between 2 rooms.
     * Build's walls and makes sure a wall is not placed on a floor tile that already exists.
     **/

    public void makeYHallway(Room temp1, Room temp2) {
        int distance = temp1.getPoint().getYDistance(temp2.getPoint());
        if (distance < 0) {
            startY = temp1.getPoint().getY();
            endY = temp1.getPoint().getY() - distance;
        } else {
            startY = temp1.getPoint().getY() - distance;
            endY = temp1.getPoint().getY();
        }
        for (int y = startY - 1; y <= endY + 1; y++) {
            if (renderedWorld[temp2.getPoint().getX() + 1][y] != Tileset.FLOOR) {
                renderedWorld[temp2.getPoint().getX() + 1][y] = Tileset.WALL;
            }
            if (renderedWorld[temp2.getPoint().getX() - 1][y] != Tileset.FLOOR) {
                renderedWorld[temp2.getPoint().getX() - 1][y] = Tileset.WALL;
            }
        }
        for (int y = startY; y <= endY; y++) {
            renderedWorld[temp2.getPoint().getX()][y] = Tileset.FLOOR;
        }
    }


}
