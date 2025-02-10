import java.security.InvalidParameterException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public abstract class InputParser {

    public static boolean AskForBoolInput(Scanner input, String query, char trueOption, char falseOption)
    {
        String userInput = "";
        while(true)
        {
            System.out.print(query);
            userInput = input.nextLine();

            if(Objects.equals(userInput, Character.toString(trueOption).toLowerCase())) return true;
            if(Objects.equals(userInput, Character.toString(falseOption).toLowerCase())) return false;

            System.out.println("Invalid Input, enter again\n");
        }
    }


    public static int AskForIntInput(Scanner input, String query, int lowerBounds, int upperBounds)
    {
        int returnVal = lowerBounds-1;
        while(returnVal < lowerBounds || returnVal > upperBounds) {
            try {
                System.out.print(query);
                returnVal = input.nextInt();
                if(returnVal < lowerBounds || returnVal > upperBounds) System.out.println(ANSIcolors.RED_TEXT + "Invalid Input, enter again\n" + ANSIcolors.ANSI_RESET);
            } catch (InputMismatchException e) {
                System.out.println(ANSIcolors.RED_TEXT + "Invalid Input, enter again\n" + ANSIcolors.ANSI_RESET);
            }
            input.nextLine();
        }
        return returnVal;
    }

    public static String AskForCommand(Scanner input, String query, List<String> ValidCommands)
    {
        while(true)
        {
            System.out.print(query);
            String fullInput = input.nextLine().toLowerCase();
            String[] inputs = fullInput.split("\\s+"); //use instead of space to split multiple spaces

            if((fullInput.length() == 2 || fullInput.length() == 3)) return fullInput;

            if(!ValidCommands.contains(inputs[0])) {
                System.out.println(ANSIcolors.RED_TEXT + "Unknown command...\n" + ANSIcolors.ANSI_RESET);
                continue;
            }

            return fullInput;
        }
    }

    public static int[] AskForCoordinates(String userInput, int maxSize) throws InvalidParameterException
    {
        //format must be in letter-number coordinate, example: a1, b4, c3
        int[] returnVal = new int[2];

        if(userInput.length() == 1 || userInput.length() > 3) {
            throw new InvalidParameterException( ANSIcolors.RED_TEXT + "Invalid tile length... try again.\n" + ANSIcolors.ANSI_RESET);
        }

        int yValue = (int)Character.toLowerCase(userInput.charAt(0)) - 97;
        if(yValue < 0 || yValue > maxSize) {
            throw new InvalidParameterException( ANSIcolors.RED_TEXT + "Row " + userInput.charAt(0) + " is out of bounds, try again.\n" + ANSIcolors.ANSI_RESET);
        }

        int xValue =  Integer.parseInt(userInput.substring(1)) - 1;
        if(xValue < 0 || xValue >= maxSize) {
            throw new InvalidParameterException( ANSIcolors.RED_TEXT + "Column " + (xValue+1) + " is out of bounds, try again.\n" + ANSIcolors.ANSI_RESET);
        }

        returnVal[0] = xValue;
        returnVal[1] = yValue;
        //Valid tile entered...
        return returnVal;

    }
}
