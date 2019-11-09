package bg.sofia.uni.fmi.mjt;

import java.util.Random;

class Mutation {
    public static Chromosome insertion(Chromosome chromosome) {
        City[] cities = chromosome.getArray();
        Random r = new Random();
        int randomIndex = r.nextInt(cities.length);
        int randomDestination = r.nextInt(cities.length);

        if (randomIndex < randomDestination) {
            City temp = cities[randomIndex];
            for (int i = randomIndex; i < randomDestination; i++) {
                cities[i] = cities[i + 1];
            }
            cities[randomDestination] = temp;
        } else {
            City temp = cities[randomIndex];
            for (int i = randomIndex; i > randomDestination; i--) {
                cities[i] = cities[i - 1];
            }
            cities[randomDestination] = temp;
        }
        return new Chromosome(cities);
    }

    public static Chromosome swap(Chromosome chromosome) {
        City[] cities = chromosome.getArray();
        int l = cities.length;
        Random r = new Random();
        swap(cities, r.nextInt(l), r.nextInt(l));
        return new Chromosome(cities);
    }

    private static void swap(City[] array, int i, int j) {
        City temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}

