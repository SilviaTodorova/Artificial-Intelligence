package bg.sofia.uni.fmi.mjt;

import java.nio.BufferOverflowException;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;

public class Population implements Iterable<Chromosome> {

    private PriorityQueue<Chromosome> chromosomes;
    private int maxSize;

    public Population(int maxSize) {
        this.maxSize = maxSize;
        chromosomes = new PriorityQueue<>();
    }

    public void add(Chromosome chromosome) {
        if (chromosomes.size() == maxSize) {
            throw new BufferOverflowException();
        }
        chromosomes.add(chromosome);
    }

    public Chromosome[] getChromosomes() {
        Chromosome[] array = new Chromosome[chromosomes.size()];

        int i = 0;
        for (Chromosome chr : chromosomes) {
            array[i++] = chr;
        }

        return array;
    }

    public int size() {
        return chromosomes.size();
    }

    public static Population getRandomPopulation(int numOfCities, int sizeOfPop) {
        City[] cities = new City[numOfCities];
        Random r = new Random();

        for (int i = 0; i < numOfCities; i++) {
            cities[i] = City.getRandomCity(r);
        }

        Population population = new Population(sizeOfPop);

        for (int i = 0; i < sizeOfPop; i++) {
            Chromosome chr = new Chromosome(cities);
            chr.shuffle();

            population.add(chr);
        }

        return population;
    }

    public Chromosome getMostFit() {
        return chromosomes.peek();
    }

    public Iterator<Chromosome> iterator() {
        return chromosomes.iterator();
    }

    public Population deepCopy() {
        Population population = new Population(maxSize);
        chromosomes.forEach(population::add);
        return population;
    }

    @Override
    public String toString() {
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

