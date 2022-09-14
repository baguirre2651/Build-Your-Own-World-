package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;

/** the Avatar Movement **/
public class Avatar implements Serializable {
    int xPos;
    int yPos;
    TETile[][] exploreWorld;
    World world;
    Point currentLocation;
    Point oldPosition;

    Avatar(Point position, World generatedWorld) {
        xPos = position.getX();
        yPos = position.getY();
        exploreWorld = generatedWorld.getRenderedWorld();
        currentLocation = position;
        world = generatedWorld;
        exploreWorld[xPos][yPos] = Tileset.AVATAR;
    }

    public void travelNorth() {
        if (valid(xPos, yPos + 1)) {
            oldPosition = new Point(xPos, yPos);
            yPos += 1;
            currentLocation = new Point(xPos, yPos);
            world.updatePlayerPosition(oldPosition, currentLocation);
        }
    }

    public void travelSouth() {
        if (valid(xPos, yPos - 1)) {
            oldPosition = new Point(xPos, yPos);
            yPos -= 1;
            currentLocation = new Point(xPos, yPos);
            world.updatePlayerPosition(oldPosition, currentLocation);
        }
    }

    public void travelWest() {
        if (valid(xPos - 1, yPos)) {
            oldPosition = new Point(xPos, yPos);
            xPos -= 1;
            currentLocation = new Point(xPos, yPos);
            world.updatePlayerPosition(oldPosition, currentLocation);
        }
    }

    public void travelEast() {
        if (valid(xPos + 1, yPos)) {
            oldPosition = new Point(xPos, yPos);
            xPos += 1;
            currentLocation = new Point(xPos, yPos);
            world.updatePlayerPosition(oldPosition, currentLocation);
        }
    }

    public boolean valid(int x, int y) {
        return exploreWorld[x][y].character() == Tileset.FLOOR.character();
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }
}
