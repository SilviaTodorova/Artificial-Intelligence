package bg.sofia.uni.fmi.mjt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Selection {
    private static final int ODDS_OF_NOT_PICKING_FITTEST = 5;

    public static Chromosome tournamentSelection(Population population, int k) {
        if (k < 1) {
            throw new IllegalArgumentException();
        }

        Chromosome[] populationAsArray = population.getChromosomes();
        List<Chromosome> kChromosomes = getKChromosomes(populationAsArray, k);
        return getChromosome(kChromosomes);
    }

    private static List<Chromosome> getKChromosomes(Chromosome[] pop, int k) {

        List<Chromosome> kChromosomes = new ArrayList<>();

        Random r = new Random();
        for (int j = 0; j < k; j++) {
            Chromosome chromosome = pop[r.nextInt(pop.length)];
            kChromosomes.add(chromosome);
        }

        return kChromosomes;
    }

    private static Chromosome getChromosome(List<Chromosome> list) {

        Chromosome bestChromosome = getBestChromosome(list);

        Random r = new Random();
        if (r.nextInt(ODDS_OF_NOT_PICKING_FITTEST) == 0 && list.size() != 1) {
            list.remove(bestChromosome);
            return list.get(r.nextInt(list.size()));
        }

        return bestChromosome;
    }

    private static Chromosome getBestChromosome(List<Chromosome> arrayList) {

        Chromosome bestC = null;

        for (Chromosome c : arrayList) {
            if (bestC == null) {
                bestC = c;
            } else if (c.getFitness() < bestC.getFitness()) {
                bestC = c;
            }
        }

        return bestC;
    }

}
