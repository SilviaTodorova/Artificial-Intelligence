package bg.sofia.uni.fmi.mjt.algorithm;

import java.nio.BufferOverflowException;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;

public class Population implements Iterable<Chromosome> {

    private PriorityQueue<Chromosome> chromosomes;
    private int maxSize;

    public Population (int maxSize) {
        this.maxSize = maxSize;
        chromosomes = new PriorityQueue<>();
    }

    public void add (Chromosome chromosome) {
        if (chromosomes.size() == maxSize) {
            throw new BufferOverflowException();
        }
        chromosomes.add(chromosome);
    }

    public Chromosome[] getChromosomes () {
        Chromosome[] array = new Chromosome[chromosomes.size()];

        int i = 0;
        for (Chromosome chromo : chromosomes) {
            array[i++] = chromo;
        }

        return array;
    }

    public int size () {
        return chromosomes.size();
    }

    public int getAverageDistance () {

        int averageDistance = 0;

        for (Chromosome chromosome : chromosomes) {
            averageDistance += chromosome.getDistance();
        }

        return averageDistance / chromosomes.size();
    }

    public static Population getRandomPopulation(int numOfCities, int sizeOfPop, Random random) {
        City[] cities = new City[numOfCities];

        for (int i = 0; i < numOfCities; i++) {
            cities[i] = City.getRandomCity(random);
        }

        Population population = new Population(sizeOfPop);

        for (int i = 0; i < sizeOfPop; i++) {
            population.add(new Chromosome(cities, random));
        }

        return population;
    }

    public Chromosome getMostFit () {
        return chromosomes.peek();
    }

    public Iterator<Chromosome> iterator () {
        return chromosomes.iterator();
    }

    public Population deepCopy () {
        Population population = new Population(maxSize);
        chromosomes.forEach((chromosome) -> population.add(chromosome));
        return population;
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder("Population:");

        for (Chromosome chromosome : chromosomes) {
            sb.append("\n");
            sb.append(chromosome);
            sb.append(" Value: ");
            sb.append(chromosome.getDistance());
        }

        return new String(sb);
    }

}

