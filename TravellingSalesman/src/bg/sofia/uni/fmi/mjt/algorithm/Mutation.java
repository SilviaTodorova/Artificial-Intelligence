package bg.sofia.uni.fmi.mjt.algorithm;

import java.util.Random;

public class Mutation {
    static Chromosome insertion (Chromosome chromosome, Random random) {
        City[] cities = chromosome.getArray();
        int randomIndex = random.nextInt(cities.length);
        int randomDestination = random.nextInt(cities.length);

        if (randomIndex < randomDestination) {
            City temp = cities[randomIndex];
            for (int i = randomIndex; i < randomDestination; i++) {
                cities[i] = cities[i+1];
            }
            cities[randomDestination] = temp;
        } else {
            City temp = cities[randomIndex];
            for (int i = randomIndex; i > randomDestination; i--) {
                cities[i] = cities[i-1];
            }
            cities[randomDestination] = temp;
        }
        return new Chromosome(cities);
    }

    static Chromosome swap(Chromosome chromosome, Random random) {
        City[] cities = chromosome.getArray();
        int l = cities.length;
        swap(cities, random.nextInt(l), random.nextInt(l));
        return new Chromosome(cities);
    }

    private static void swap(City[] array, int i, int j) {
        City temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}

