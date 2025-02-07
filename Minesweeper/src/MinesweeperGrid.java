public class MinesweeperGrid {
    MinesweeperTile[][] Tiles;
    private final String SIDE_BAR = "▓ ";
    private final char SEPERATOR = '|';
    private final String BOTTOM_BAR = "▗▄▖";
    private final char BOMB_ICON = '◉';
    // ▗▄▖
    // ▝▀▘

    //Section - ANSI Colors
    public final String ANSI_RESET = "\u001B[0m";
    public final String DEFAULT_TILE_BG_1 = "\u001B[47m";
    public final String DEFAULT_TILE_BG_2 = "\u001B[100m";
    public final String BOMB_TEXT = "\033[1;91m";

    public String[] TileColors = {"\033[1;97m", "\033[1;94m", "\033[1;92m", "\033[1;91m",  "\033[1;95m", "\033[1;93m", "\033[1;96m", "\033[1;34m","\033[1;37m"};

    public MinesweeperGrid(int size, float BombRatio)
    {
        SetupGrid(size);

        PlaceBombs(BombRatio);

        SetSurroundingTiles();
    }

    private void SetupGrid(int size)
    {
        size = Math.clamp(size, 0, 26);

        Tiles = new MinesweeperTile[size][size];

        for(int x = 0 ; x < Tiles.length ; x++)
        {
            for(int y = 0 ; y < Tiles.length ; y++)
            {
                Tiles[x][y] = new MinesweeperTile();
            }
        }
    }

    private void PlaceBombs(float BombRatio)
    {
        int numberOfBombs = (int)(Math.clamp(BombRatio,0f, 1f) * (Tiles.length* Tiles.length));

        while(numberOfBombs > 0)
        {
            MinesweeperTile tile = Tiles[(int)(Math.random() * Tiles.length)][(int)(Math.random() * Tiles.length)];
            if(tile.IsBomb()) continue;
            tile.SetBomb(true);
            numberOfBombs--;
        }
    }

    private void SetSurroundingTiles()
    {
        for(int x = 0 ; x < Tiles.length ; x++)
        {
            for(int y = 0 ; y < Tiles.length ; y++)
            {
                Tiles[x][y].SetSurroundingBombs(GetSurroundingTiles(x,y));
            }
        }
    }

    private MinesweeperTile[] GetSurroundingTiles(int x, int y)
    {
        MinesweeperTile[] SurroundingTiles = new MinesweeperTile[8];

        if(x+1 < Tiles.length) SurroundingTiles[0] = Tiles[x+1][y];
        if(x-1 >= 0) SurroundingTiles[1] = Tiles[x-1][y];
        if(y+1 < Tiles.length) SurroundingTiles[2] = Tiles[x][y+1];
        if(y-1 >= 0) SurroundingTiles[3] = Tiles[x][y-1];
        if(x+1 < Tiles.length && y+1 < Tiles.length) SurroundingTiles[4] = Tiles[x+1][y+1];
        if(x-1 >= 0 && y-1 >= 0) SurroundingTiles[5] = Tiles[x-1][y-1];
        if(x+1 < Tiles.length && y-1 >= 0) SurroundingTiles[6] = Tiles[x+1][y-1];
        if(x-1 >= 0 && y+1 < Tiles.length) SurroundingTiles[7] = Tiles[x-1][y+1];

        return SurroundingTiles;
    }



    public void PrintGrid()
    {
        int j = 0;
        for (MinesweeperTile[] tile : Tiles) {
            j++;
            System.out.print(j + " ");
            for(int i = 0 ; i < tile.length ; i++)
            {
                System.out.print(i==0 ? SIDE_BAR : "");
                String output = tile[i].IsBomb() ? BOMB_TEXT + BOMB_ICON :  String.valueOf(tile[i].GetSurroundingBombs());
                System.out.print(PrintTile(tile[i], i+j));
            }
            System.out.println();
        }
        System.out.print("    ");
        for(int i = 0 ; i < Tiles.length; i++)
        {
            System.out.print(BOTTOM_BAR);
        }

        System.out.print("\n    ");
        for(int i = 0 ; i < Tiles.length; i++)
        {
            System.out.print(" " + ((char)(i+97)) + " ");
        }
    }

    public String PrintTile(MinesweeperTile tile, int x) {
        String Output = (x % 2 == 0 ? DEFAULT_TILE_BG_1 : DEFAULT_TILE_BG_2) + " ";

        if (tile.IsBomb()) Output += (BOMB_TEXT + BOMB_ICON);
        else {
            Output += (TileColors[tile.GetSurroundingBombs()] + tile.GetSurroundingBombs());
        }
        Output +=  " " + ANSI_RESET;
        return Output;
    }
}
