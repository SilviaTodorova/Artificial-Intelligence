package bg.sofia.uni.fmi.mjt;

import bg.sofia.uni.fmi.mjt.enums.CrossoverType;
import bg.sofia.uni.fmi.mjt.enums.MutationType;

import java.util.*;

class GeneticAlgorithm {
    private static final int POPULATION_SIZE = 100;
    private static final int MAX_GENERATIONS = 2000;
    private static final int COUNT_SELECT = 100;

    private Population population;
    private Population initialPop;

    private int numberOfCities;

    private CrossoverType crossoverType;
    private MutationType mutationType;

    private int countSelect;
    private double mutationRate;

    private List<Integer> distanceGenerations;

    public GeneticAlgorithm(int numberOfCities, CrossoverType crossoverType, MutationType mutationType) {
        this.numberOfCities = numberOfCities;
        initialPop = Population.getRandomPopulation(numberOfCities, POPULATION_SIZE);

        this.setPopulation(initialPop);
        this.setCountSelect(COUNT_SELECT);
        this.setMutationRate(0.04);

        this.setCrossoverType(crossoverType);
        this.setMutationType(mutationType);

        distanceGenerations = new ArrayList<>();
    }

    private void setPopulation(Population population) {
        if (population == null) {
            throw new IllegalArgumentException();
        }

        initialPop = population;
        this.population = initialPop.deepCopy();
    }

    private void setCountSelect(int countSelect) {
        if (countSelect < 0) {
            throw new IllegalArgumentException();
        }
        this.countSelect = countSelect;
    }

    private void setMutationRate(double mutationRate) {
        if (mutationRate < 0 || mutationRate > 1) {
            throw new IllegalArgumentException();
        }
        this.mutationRate = mutationRate;
    }

    private void setCrossoverType(CrossoverType crossoverType) {
        this.crossoverType = crossoverType;
    }

    private void setMutationType(MutationType mutationType) {
        this.mutationType = mutationType;
    }

    public void run() {
        for (int i = 0; i < MAX_GENERATIONS; i++) {
            population = createNextGeneration();
            distanceGenerations.add(population.getMostFit().getDistance());
        }
    }

    private Population createNextGeneration() {
        Population nextGen = new Population(population.size());

        Random r = new Random();
        while (nextGen.size() < population.size() - 1) {
            Chromosome p1 = Selection.tournamentSelection(population, countSelect);
            Chromosome p2 = Selection.tournamentSelection(population, countSelect);

            List<Chromosome> children = crossover(p1, p2);
            p1 = children.get(0);
            p2 = children.get(1);

            if (r.nextDouble() <= mutationRate) {
                p1 = mutate(p1);
            }

            if (r.nextDouble() <= mutationRate) {
                p2 = mutate(p2);
            }

            nextGen.add(p1);
            nextGen.add(p2);
        }

        if (nextGen.size() != POPULATION_SIZE) {
            nextGen.add(Selection.tournamentSelection(population, countSelect));
        }

        return nextGen;
    }

    private Chromosome mutate(Chromosome chromosome) {
        if (mutationType == MutationType.SWAP) {
            return Mutation.swap(chromosome);
        } else {
            return Mutation.insertion(chromosome);
        }
    }

    private List<Chromosome> crossover(Chromosome p1, Chromosome p2) {
        List<Chromosome> children;
        if (crossoverType == CrossoverType.ONE_POINT) {
            children = Crossover.onePointCrossover(p1, p2);
        } else {
            children = Crossover.twoPointCrossover(p1, p2);
        }
        return children;
    }

    public void printProperties() {
        System.out.printf("%nGenetic Algorithm Properties%n");

        System.out.printf("Number of Cities: %d%n", this.numberOfCities);
        System.out.printf("Population Size: %d%n", POPULATION_SIZE);
        System.out.printf("Max. Generation: %d%n", MAX_GENERATIONS);
        System.out.printf("Crossover Type: %s%n", crossoverType.getName());
        System.out.printf("Mutation Type: %s%n", mutationType.getName());
    }

    public void printResults() {
        System.out.printf("%nGenetic Algorithm Results%n");

        Set<Integer> indexes = new TreeSet<>();
        indexes.add(9);
        indexes.add(MAX_GENERATIONS / 4);
        indexes.add(MAX_GENERATIONS / 3);
        indexes.add(MAX_GENERATIONS / 2);
        indexes.add(MAX_GENERATIONS - 1);

        for (Integer index : indexes) {
            System.out.print(String.format("Distance of generation %d: %d%n", index + 1, this.distanceGenerations.get(index)));
        }
    }

}
