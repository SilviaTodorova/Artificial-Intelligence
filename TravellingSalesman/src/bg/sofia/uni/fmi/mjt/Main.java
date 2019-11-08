package bg.sofia.uni.fmi.mjt;

import bg.sofia.uni.fmi.mjt.algorithm.GeneticAlgorithm;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int numberOfcities = getNumberOfCities();
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(numberOfcities);

        geneticAlgorithm.run();
        geneticAlgorithm.printProperties();
        geneticAlgorithm.printResults();
    }

    public static int getNumberOfCities(){
        Scanner in = new Scanner(System.in);

        System.out.print("Enter positive number of cities: ");
        int numberOfCities = in.nextInt();
        while (numberOfCities < 0) {
            System.out.print("Incorrect number of cities. Please, enter positive number: ");
            numberOfCities = in.nextInt();
        }

        in.close();
        return  numberOfCities;
    }
}
