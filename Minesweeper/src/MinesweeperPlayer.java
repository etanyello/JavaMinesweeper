import java.util.Scanner;

public class MinesweeperPlayer {
    private MinesweeperGrid Grid;
    private boolean GameOver = false;
    private int turn;

    public void StartGame()
    {
        Scanner input = new Scanner(System.in);

        //int gridSize = InputParser.AskForIntInput(input, "How big do you want your grid (5-26): ", 5, 26);
        //float difficulty = 0.05f + (0.05f * (float)InputParser.AskForIntInput(input, "What difficulty would you like: (1-5): ", 1, 5));
        //System.out.println("\n");
        //Grid = new MinesweeperGrid(gridSize, difficulty);
        int gridSize = 8;
        Grid = new MinesweeperGrid(gridSize, 0.1f);
        turn = 0;

        while(!GameOver)
        {
            PrintGrid();
            System.out.println("There are: " + Grid.numberOfBombs + " bombs on the grid...");
            int[] targetTile = InputParser.AskForCoordinates(input, "Enter your move: ", gridSize, "Invalid tile, enter again\n");
            //System.out.println("Selected tile: " + targetTile[0] + ", " + targetTile[1]);
            Grid.SelectTile(targetTile[0], targetTile[1]);

            if(turn == 0) Grid.ClearSurroundingBombs(targetTile[0], targetTile[1]);

            turn++;
        }
    }


    private void PrintGrid()
    {
        Grid.PrintGrid();
    }

}
