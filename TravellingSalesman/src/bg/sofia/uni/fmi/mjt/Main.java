package bg.sofia.uni.fmi.mjt;

import bg.sofia.uni.fmi.mjt.enums.CrossoverType;
import bg.sofia.uni.fmi.mjt.enums.MutationType;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int numberOfcities = getNumberOfCities();
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(numberOfcities, CrossoverType.ONE_POINT, MutationType.SWAP);

        geneticAlgorithm.run();
        geneticAlgorithm.printProperties();
        geneticAlgorithm.printResults();
    }

    private static int getNumberOfCities() {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter positive number of cities: ");
        int numberOfCities = in.nextInt();
        while (numberOfCities < 0) {
            System.out.print("Incorrect number of cities. Please, enter positive number: ");
            numberOfCities = in.nextInt();
        }

        in.close();
        return numberOfCities;
    }
}
