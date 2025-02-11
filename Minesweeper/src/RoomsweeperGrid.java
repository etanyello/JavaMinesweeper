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
            int xPos = (int)(Math.random() * Tiles.length);
            int yPos = (int)(Math.random() * Tiles.length);

            RoomsweeperTile tile = (RoomsweeperTile)Tiles[xPos][yPos];
            //fill this tile
            if(tile.IsBomb() || tile.IsWall()) continue;
            tile.SetWall(true);
            tile.Reveal();
            numberOfWalls--;

            int firstTile = (int)(Math.random() * 3) - 1;
            int secondTile = (int)(Math.random() * 3) - 1;

            if(firstTile == 1) { PlaceWallAt(xPos + 1, yPos); }
            else if (firstTile == -1){ PlaceWallAt(xPos, yPos + 1); }

            if(secondTile == 1) { PlaceWallAt(xPos - 1, yPos); }
            else if (secondTile == -1){ PlaceWallAt(xPos, yPos - 1); }
        }
        numberOfWalls = t_bomb;
    }

    private void PlaceWallAt(int x, int y){
        if(x >= Tiles.length || y >= Tiles.length || x < 0 || y < 0 || numberOfWalls <= 0) return;
        RoomsweeperTile rt = (RoomsweeperTile)Tiles[x][y];
        if(rt.IsBomb() || rt.IsWall()) return;
        rt.SetWall(true);
        rt.Reveal();
        numberOfWalls--;
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
