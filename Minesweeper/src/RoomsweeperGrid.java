public class RoomsweeperGrid extends MinesweeperGrid{

    int numberOfWalls = 0;
    float wallRatio = 0f;

    private final String WALL_COLOR = "\033[0;40m";

    public RoomsweeperGrid(int size, float bombRatio, float wallRatio)
    {
        super(size, bombRatio);
        this.wallRatio = wallRatio;
        PlaceWalls();
    }

    @Override
    protected void SetupGrid(int size)
    {
        size = Math.clamp(size, 0, 26);

        Tiles = new RoomsweeperTile[size][size];

        for(int x = 0 ; x < Tiles.length ; x++)
        {
            for(int y = 0 ; y < Tiles.length ; y++)
            {
                Tiles[x][y] = new RoomsweeperTile();
                Tiles[x][y].SetCoords(x,y);
            }
        }
    }

    private void PlaceWalls()
    {
        numberOfWalls = (int)(Math.clamp(wallRatio,0f, 1f) * (Tiles.length* Tiles.length));
        int t_bomb = numberOfWalls;

        while(numberOfWalls > 0)
        {
            RoomsweeperTile tile = (RoomsweeperTile)Tiles[(int)(Math.random() * Tiles.length)][(int)(Math.random() * Tiles.length)];
            if(tile.IsBomb() || tile.IsWall()) continue;
            tile.SetWall(true);
            tile.Reveal();
            numberOfWalls--;
        }
        numberOfWalls = t_bomb;
    }

    @Override
    public String PrintTile(MinesweeperTile tile, int x) {
        RoomsweeperTile roomTile = (RoomsweeperTile)tile;
        if(roomTile.IsWall()) {
            return WALL_COLOR + "   " + ANSIcolors.ANSI_RESET;
        }
        return super.PrintTile(tile, x);
    }
}
