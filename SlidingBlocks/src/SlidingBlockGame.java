import com.game.slindingblocks.Node;
import com.game.slindingblocks.IDAStarAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class SlidingBlockGame {
    public static void main(String[] args){
        try{
            Scanner input = new Scanner(System.in);
            System.out.print("Enter number of blocks (8, 15, 24, ...): ");
            int numberOfBlocks = getNumberOfBlocks(input);

            System.out.print(String.format("Enter zero position. It should be number between -1 to %d: ", numberOfBlocks));
            int zeroPosition = getZeroPosition(input, numberOfBlocks);

            int[] initialState = getInitialState(input, numberOfBlocks);

            int size = (int) Math.sqrt(numberOfBlocks + 1);

            IDAStarAlgorithm algorithm = new IDAStarAlgorithm(initialState, size, zeroPosition);
            Node solution = algorithm.findSolution();

            List<Node> path = algorithm.getPath(solution);
            System.out.println(path.size());
            algorithm.printSolution(solution);

        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }

    private static int getNumberOfBlocks(Scanner input) {
        int numberOfBlocks = input.nextInt();

        while ((numberOfBlocks < 3) || ((Math.sqrt(numberOfBlocks + 1) % 2 != 1.0)
                && (Math.sqrt(numberOfBlocks + 1) % 2 != 0.0))) {
            System.out.print("Incorrect number of blocks. Please, enter correct number of blocks (8, 15, 24, ...): ");
            numberOfBlocks = input.nextInt();
        }

        return numberOfBlocks;
    }

    private static int getZeroPosition(Scanner input, int numberOfBlocks) {
        int zeroPosition = input.nextInt();

        if(zeroPosition == -1){
            return numberOfBlocks;
        }

        while (zeroPosition < -1 || zeroPosition > numberOfBlocks) {
            System.out.print(String.format("Incorrect zero position. Please, enter number between -1 to %d: ", numberOfBlocks));
            zeroPosition = input.nextInt();
        }

        return zeroPosition;
    }

    private static int[] getInitialState(Scanner input, int numberOfBlocks) throws Exception {
        int[] initialState = new int[numberOfBlocks+1];
        List<Integer> numbers = new ArrayList<>();

        System.out.println("Enter the initial state: ");
        for (int index = 0; index <= numberOfBlocks; index++) {
            int elem = input.nextInt();

            if(elem < -1 || elem > numberOfBlocks || numbers.contains(elem)){
                throw new Exception("Incorrect input");
            }

            numbers.add(elem);
            initialState[index] = elem;
        }

        input.close();
        return initialState;
    }
}

