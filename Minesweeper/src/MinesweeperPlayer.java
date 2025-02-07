public class MinesweeperPlayer {
    private MinesweeperGrid Grid;

    public MinesweeperPlayer() {this(10, 0.15f);}
    public MinesweeperPlayer(int size, float BombRatio)
    {
        Grid = new MinesweeperGrid(size, BombRatio);
    }

    public void PrintGrid()
    {
        Grid.PrintGrid();
    }

}
