package bg.sofia.uni.fmi.mjt;

import java.util.Arrays;
import java.util.Random;

public class Chromosome implements Comparable<Chromosome> {

    private City[] cities;
    private int fitness = -1;

    public Chromosome(City[] cities) {
        this.cities = cities.clone();
    }

    public void shuffle() {
        Random r = new Random();
        for (int i = 0; i < cities.length; i++) {
            swap(i, r.nextInt(cities.length));
        }
    }

    private void swap(int i, int j) {
        City temp = cities[i];
        cities[i] = cities[j];
        cities[j] = temp;
    }

    public City[] getArray() {
        return cities.clone();
    }

    public int getFitness() {
        if (fitness != -1) {
            return fitness;
        }

        double distanceTravelled = 0;

        for (int i = 1; i < cities.length; i++) {
            distanceTravelled += City.distance(cities[i - 1], cities[i]);
        }

        distanceTravelled += City.distance(cities[cities.length - 1], cities[0]);
        this.fitness = (int) distanceTravelled;
        return fitness;
    }

    @Override
    public int compareTo(Chromosome chromosome) {
        return getFitness() - chromosome.getFitness();
    }

    @Override
    public int hashCode() {
        StringBuilder sb = new StringBuilder();
        for (City city : cities) {
            sb.append(city);
        }
        return (new String(sb)).hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Chromosome)) {
            return false;
        }

        Chromosome c = (Chromosome) o;

        return Arrays.equals(c.cities, cities);
    }
}

