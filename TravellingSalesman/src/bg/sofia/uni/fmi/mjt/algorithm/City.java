package bg.sofia.uni.fmi.mjt.algorithm;

import java.util.Random;

public class City {
    private String id;
    private static int count;
    private int x, y;

    public City (int x, int y) {
        this.x = x;
        this.y = y;
        count++;
        this.id = String.format("City-%d",count);
    }

    public int getX () {
        return x;
    }

    public int getY () {
        return y;
    }

    public static City getRandomCity (Random random) {
        int x = random.nextInt(500);
        int y = random.nextInt(500);
        return new City(x, y);
    }

    public static double distance (City city1, City city2) {

        int x1 = city1.getX();
        int y1 = city1.getY();

        int x2 = city2.getX();
        int y2 = city2.getY();

        int xDiff = x2 - x1;
        int yDiff = y2 - y1;

        return Math.sqrt(xDiff*xDiff + yDiff*yDiff);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (x != city.x) return false;
        if (y != city.y) return false;
        return true;

    }

    @Override
    public int hashCode() {
        int result = this.id.hashCode();
        result = 31 * result + x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return " (" + x + ", " + y + ")";
    }
}
