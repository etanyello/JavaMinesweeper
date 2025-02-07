import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class InputParser {

    public static int AskForIntInput(Scanner input, String query, int lowerBounds, int upperBounds)
    {
        int returnVal = lowerBounds-1;
        while(returnVal < lowerBounds || returnVal > upperBounds) {
            try {
                System.out.print(query);
                returnVal = input.nextInt();
                if(returnVal < lowerBounds || returnVal > upperBounds) System.out.println("Invalid Input, enter again\n");
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input, enter again\n");
            }
            input.nextLine();
        }
        return returnVal;
    }

    public static int[] AskForCoordinates(Scanner input, String query, int maxSize, String error)
    {
        //format must be in letter-number coordinate, example: a1, b4, c3
        int[] returnVal = new int[2];
        while(true)
        {
            System.out.print(query);
            String userInput = input.nextLine();
            if(userInput.length() == 1 || userInput.length() > 3) { System.out.println(error); continue;}

            int yValue = (int)Character.toLowerCase(userInput.charAt(0)) - 97;
            if(yValue < 0 || yValue > maxSize) { System.out.println(error); continue;}

            int xValue =  Integer.parseInt(userInput.substring(1)) - 1;
            if(xValue < 0 || xValue >= maxSize) { System.out.println(error); continue;}

            returnVal[0] = xValue;
            returnVal[1] = yValue;
            //Valid tile entered...
            return returnVal;
        }
    }
}
