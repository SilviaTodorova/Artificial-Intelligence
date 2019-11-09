package bg.sofia.uni.fmi.mjt;

import java.util.Random;

public class City {
    private int x, y;

    private City(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private int getX() {
        return x;
    }

    private int getY() {
        return y;
    }

    public static City getRandomCity(Random random) {
        int x = random.nextInt(500);
        int y = random.nextInt(500);
        return new City(x, y);
    }

    public static double distance(City city1, City city2) {
        int x1 = city1.getX();
        int y1 = city1.getY();

        int x2 = city2.getX();
        int y2 = city2.getY();

        int xDiff = x2 - x1;
        int yDiff = y2 - y1;

        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }
}
