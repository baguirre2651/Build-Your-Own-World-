package byow.Core;


import java.io.Serializable;

public class Point implements Serializable {
    /**
     * the x coordinate of a point
     **/

    private final int X;


    /**
     * the y coordinate of a point
     **/

    private final int Y;


    public Point(int givX, int givY) {
        X = givX;
        Y = givY;
    }


//    public Integ(int x) {
//        X = x;
//    }
//
//    public Integ2 (int y) {
//        Y = y;
//    }

    /**
     * x coordinate of a point position
     **/

    public int getX() {
        return X;
    }

    /**
     * y coordinate of a point position
     **/
    public int getY() {
        return Y;
    }


    /**
     * gets the distance between the two points vertically
     **/

    public int getYDistance(Point nextPoint) {
        return this.getY() - nextPoint.getY();
    }


    /**
     * gets the distance between 2 points horizontally
     **/

    public int getXDistance(Point nextPoint) {
        return this.getX() - nextPoint.getX();
    }


}

