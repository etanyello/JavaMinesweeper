import java.security.InvalidParameterException;
import java.util.List;
import java.util.Scanner;

public class MinesweeperPlayer {
    private MinesweeperGrid Grid;
    private List<String> ValidCommands = List.of("help", "flag", "grid", "show", "commands", "reset", "new");

    private boolean GameOver = false;
    private int turn;
    private int gridSize;

    public void StartGame()
    {
        Scanner input = new Scanner(System.in);

        InitialiseNewGame(input);

        while(!GameOver) { GetPlayerCommand(input); }
    }

    private void InitialiseNewGame(Scanner input)
    {
        gridSize = InputParser.AskForIntInput(input, "How big do you want your grid (" + ANSIcolors.BLUE_TEXT  + "5-26" + ANSIcolors.ANSI_RESET + "): ", 5, 26);
        float difficulty = 0.05f + (0.05f * (float)InputParser.AskForIntInput(input, "What difficulty would you like: (" + ANSIcolors.BLUE_TEXT  + "1-5" + ANSIcolors.ANSI_RESET + "): ", 1, 5));
        System.out.println("\n");
        Grid = new MinesweeperGrid(gridSize, difficulty);
        turn = 0;
        PrintGrid();
    }

    private int GetPlayerCommand(Scanner input)
    {
        String userCommand = InputParser.AskForCommand(input, "Enter your command: ", ValidCommands);
        String[] userCommands = userCommand.split("\\s+");

        switch (userCommands[0])
        {
            case "help", "commands": CMD_Help(); return 1;
            case "flag": CMD_FlagTile(userCommands[1]); PrintGrid(); return 1;
            case "show", "grid": PrintGrid(); return 1;
            case "reset", "new": System.out.println("\n"); InitialiseNewGame(input); return 1;
        }

        //User entered a tile (a1, d4, b8, etc)
        if(userCommand.length() == 2 ||  userCommand.length() == 3){
            if(CMD_ClickTile(userCommand)) return 0;
            PrintGrid();
            if(Grid.CheckForWin()) {
                System.out.println("\n" +ANSIcolors.YELLOW_TEXT + "! ! ! You Won ! ! !" + ANSIcolors.ANSI_RESET);
                GameOver = true;
            }
            return 1;
        }

        System.out.println(ANSIcolors.RED_TEXT + "Unknown command..." + ANSIcolors.ANSI_RESET);
        return 0;
    }


    private void PrintGrid()
    {
        Grid.PrintGrid();
        System.out.println("There are " + ANSIcolors.BLUE_TEXT + Grid.GetTotalBombs() + ANSIcolors.ANSI_RESET + " bombs on the grid...");
    }

    private boolean CMD_ClickTile(String tile)
    {
        try {
            int[] targetTile = InputParser.AskForCoordinates(tile, gridSize);
            Grid.SelectTile(targetTile[0], targetTile[1]);

            if (turn == 0) {
                Grid.ClearSurroundingBombs(targetTile[0], targetTile[1]);
                Grid.SetSurroundingTiles();
            }

            if (Grid.GetTileAt(targetTile[0], targetTile[1]) != null) {
                if (Grid.GetTileAt(targetTile[0], targetTile[1]).IsBomb()) {
                    GameOver = true;
                    Grid.PrintGrid();
                    System.out.println(ANSIcolors.RED_TEXT + "! ! ! Game Over ! ! !" + ANSIcolors.ANSI_RESET);
                    return true;
                }
            }
            Grid.FloodFill(targetTile[0], targetTile[1]);

            turn++;
        }
        catch (InvalidParameterException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    private boolean CMD_FlagTile(String tile)
    {
        try {
            int[] targetTile = InputParser.AskForCoordinates(tile, gridSize);
            Grid.FlagTile(targetTile[0], targetTile[1]);
        }
        catch (InvalidParameterException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    private void CMD_Help()
    {
        System.out.println("\nCommands:");
        System.out.println(ANSIcolors.BLUE_TEXT + "help, commands" + ANSIcolors.ANSI_RESET + " - Displays a list of commands");
        System.out.println(ANSIcolors.BLUE_TEXT + "flag <tile>" + ANSIcolors.ANSI_RESET + " - Flags a tile you think a bomb is at");
        System.out.println(ANSIcolors.BLUE_TEXT + "grid, show" + ANSIcolors.ANSI_RESET + " - Prints out the grid");
        System.out.println(ANSIcolors.BLUE_TEXT + "reset, new" + ANSIcolors.ANSI_RESET + " - Generates a new Grid");
        System.out.println(ANSIcolors.BLUE_TEXT + "<tile>" + ANSIcolors.ANSI_RESET + " - Selects that tile (e.g \"a4\" or \"f6\")");

        System.out.println();
    }


}
