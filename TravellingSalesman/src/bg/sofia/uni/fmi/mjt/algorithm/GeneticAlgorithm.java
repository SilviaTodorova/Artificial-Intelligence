package bg.sofia.uni.fmi.mjt.algorithm;

import bg.sofia.uni.fmi.mjt.algorithm.*;
import bg.sofia.uni.fmi.mjt.enums.CrossoverType;
import bg.sofia.uni.fmi.mjt.enums.MutationType;

import java.util.*;

public class GeneticAlgorithm {
    private static final int POPULATION_SIZE = 1000;
    private static final int MAX_GENERATIONS = 1000;

    private Population population;
    private Population initialPop;

    private int numberOfCities;
    private CrossoverType crossoverType = CrossoverType.ONE_POINT;
    private MutationType mutationType = MutationType.INSERTION;



    private int k;                  // For tournament selection.
    private double crossoverRate;   // Odds of crossover occurring.
    private double mutationRate;    // Odds of mutation occurring.

    private double localSearchRate; // Odds of local search occurring on entire generation.
    private Random random;

    private ArrayList<Integer> bestDistanceOfEachGeneration;

    public GeneticAlgorithm (int numberOfCities) {
        this.numberOfCities = numberOfCities;
        initialPop = Population.getRandomPopulation(numberOfCities, POPULATION_SIZE, new Random());
        this.random = new Random();
        this.setPopulation(initialPop);
        this.setK(3);
        this.setCrossoverRate( 0.90);
        this.setMutationRate(0.04);
        this.setLocalSearchRate(0.00);
        this.setCrossoverType(CrossoverType.TWO_POINT);
        this.setMutationType(MutationType.INSERTION);

        bestDistanceOfEachGeneration = new ArrayList<>();
    }

    public void setPopulation (Population population) {
        if (population == null) {
            throw new IllegalArgumentException();
        }
        initialPop = population;
        this.population = initialPop.deepCopy();
    }

    public void setK (int k) {
        if (k < 0) {
            throw new IllegalArgumentException();
        }
        this.k = k;
    }

    public void setCrossoverRate (double crossoverRate) {
        if (crossoverRate < 0 || crossoverRate > 1) {
            throw new IllegalArgumentException();
        }
        this.crossoverRate = crossoverRate;
    }

    public void setMutationRate (double mutationRate) {
        if (mutationRate < 0 || mutationRate > 1) {
            throw new IllegalArgumentException();
        }
        this.mutationRate = mutationRate;
    }

    public void setLocalSearchRate (double localSearchRate) {
        if (localSearchRate < 0 || localSearchRate > 1) {
            throw new IllegalArgumentException();
        }
        this.localSearchRate = localSearchRate;
    }

    public void setCrossoverType (CrossoverType crossoverType) {
        this.crossoverType = crossoverType;
    }

    public void setMutationType (MutationType mutationType) {
        this.mutationType = mutationType;
    }

    public void run () {
        for (int i = 0; i < MAX_GENERATIONS; i++) {
            population = createNextGeneration();
            bestDistanceOfEachGeneration.add(population.getMostFit().getDistance());
        }
    }

    private Population createNextGeneration () {
        Population nextGen = new Population(population.size());

        while (nextGen.size() < population.size()-1) {
            Chromosome p1 = Selection.tournamentSelection(population, k, random);
            Chromosome p2 = Selection.tournamentSelection(population, k, random);

            boolean doCrossover     = (random.nextDouble() <= crossoverRate);
            boolean doMutate1       = (random.nextDouble() <= mutationRate);
            boolean doMutate2       = (random.nextDouble() <= mutationRate);
            boolean doLocalSearch1  = (random.nextDouble() <= localSearchRate);
            boolean doLocalSearch2  = (random.nextDouble() <= localSearchRate);

            if (doCrossover) {
                ArrayList<Chromosome> children = crossover(p1, p2);
                p1 = children.get(0);
                p2 = children.get(1);
            }

            if (doMutate1) p1 = mutate(p1);
            if (doMutate2) p2 = mutate(p2);

            if (doLocalSearch1) p1 = performLocalSearch(p1);
            if (doLocalSearch2) p2 = performLocalSearch(p2);

            nextGen.add(p1);
            nextGen.add(p2);

        }

        if (nextGen.size() != POPULATION_SIZE) {
            nextGen.add(Selection.tournamentSelection(population, k, random));
        }

        if (nextGen.size() != POPULATION_SIZE) {
            throw new AssertionError("Next generation population should be full.");
        }

        return nextGen;
    }

    private Chromosome performLocalSearch (Chromosome chromosome) {

        int bestDistance = chromosome.getDistance();
        City[] array = chromosome.getArray();
        City[] bestArray = array.clone();

        for (int i = 0; i < array.length-1; i++) {
            for (int k = i+1; k < array.length; k++) {

                City[] temp = array.clone();

                // Reverse order from i to k.
                for (int j = i; j <= (i+k)/2; j++) {
                    swap(temp, j, k - (j-i));
                }

                Chromosome c = new Chromosome(temp);

                int distance = c.getDistance();
                if (distance < bestDistance) {
                    bestDistance = distance;
                    bestArray = c.getArray();
                }

            }
        }

        return new Chromosome(bestArray);
    }

    private static void swap (City[] array, int i, int j) {
        City temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private Chromosome mutate (Chromosome chromosome) {
        if (mutationType == MutationType.SWAP) {
            return Mutation.swap(chromosome, random);
        } else {
            return Mutation.insertion(chromosome, random);
        }
    }

    private ArrayList<Chromosome> crossover (Chromosome p1, Chromosome p2) {
        ArrayList<Chromosome> children;
        if (crossoverType == CrossoverType.ONE_POINT) {
            children = Crossover.onePointCrossover(p1, p2, random);
        } else {
            children = Crossover.twoPointCrossover(p1, p2, random);
        }
        return children;
    }

    public void printProperties () {
        System.out.println("Genetic Algorithm Properties");
        System.out.println("Number of Cities:   " + this.numberOfCities);
        System.out.println("Population Size:    " + POPULATION_SIZE);
        System.out.println("Max. Generation:    " + MAX_GENERATIONS);
        System.out.println("Crossover Type:     " + crossoverType);
        System.out.println("Mutation Type:      " + mutationType);
        System.out.println();
    }

    public void printResults () {
        System.out.println("Genetic Algorithm Results");

        Set<Integer> indexes = new TreeSet<>();
        indexes.add(0);
        indexes.add(MAX_GENERATIONS / 4);
        indexes.add(MAX_GENERATIONS / 3);
        indexes.add(MAX_GENERATIONS / 2);
        indexes.add(MAX_GENERATIONS - 1);

        for (Integer index: indexes) {
            System.out.print(String.format("Average Distance of generation %d: %d%n",index+1,this.bestDistanceOfEachGeneration.get(index)));
        }
    }

}
