import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MinesweeperPlayer {
    private MinesweeperGrid Grid;
    private List<String> ValidCommands = List.of("help", "flag", "grid", "show");

    private boolean GameOver = false;
    private int turn;
    private int gridSize;

    public void StartGame()
    {
        Scanner input = new Scanner(System.in);

        //int gridSize = InputParser.AskForIntInput(input, "How big do you want your grid (5-26): ", 5, 26);
        //float difficulty = 0.05f + (0.05f * (float)InputParser.AskForIntInput(input, "What difficulty would you like: (1-5): ", 1, 5));
        //System.out.println("\n");
        //Grid = new MinesweeperGrid(gridSize, difficulty);
        gridSize = 8;
        Grid = new MinesweeperGrid(gridSize, 0.15f);
        turn = 0;
        PrintGrid();

        while(!GameOver)
        {
            GetPlayerCommand(input);
            /*
            int[] targetTile = InputParser.AskForCoordinates(input, "Enter your move: ", gridSize, "Invalid tile, enter again\n");
            Grid.SelectTile(targetTile[0], targetTile[1]);

            if(turn == 0) {
                Grid.ClearSurroundingBombs(targetTile[0], targetTile[1]);
                Grid.SetSurroundingTiles();
            }

            if(Grid.GetTileAt(targetTile[0], targetTile[1]) != null) {
                if(Grid.GetTileAt(targetTile[0], targetTile[1]).IsBomb()) {
                    GameOver = true;
                    Grid.PrintGrid();
                    System.out.println("\033[1;91m" + "! ! ! Game Over ! ! !");
                }
            }
            Grid.FloodFill(targetTile[0], targetTile[1]);

            turn++;
            */
        }
    }

    private int GetPlayerCommand(Scanner input)
    {
        String userCommand = InputParser.AskForCommand(input, "Enter your command: ", ValidCommands);
        String[] userCommands = userCommand.split("\\s+");

        //User entered a tile (a1, d4, b8, etc)
        if(userCommand.length() == 2 ||  userCommand.length() == 3){
            ClickTile(userCommand);
            PrintGrid();
            return 1;
        }

        switch (userCommands[0])
        {
            case "help": System.out.println("help test"); break;
            case "flag": System.out.println("flag test"); break;
            case "show", "grid": System.out.println("show test"); break;
            default: System.out.println("unknown command..."); return 0;
        }
        return 1;
    }


    private void PrintGrid()
    {
        Grid.PrintGrid();
        System.out.println("There are: " + Grid.GetTotalBombs() + " bombs on the grid...");
    }

    private void ClickTile(String tile)
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
                    System.out.println("\033[1;91m" + "! ! ! Game Over ! ! !");
                    return;
                }
            }
            Grid.FloodFill(targetTile[0], targetTile[1]);

            turn++;
        }
        catch (InvalidParameterException e){
            System.out.println(e.getMessage());
        }
    }

}
