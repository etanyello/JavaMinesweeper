public class MinesweeperGrid {
    MinesweeperTile[][] Tiles;
    private final String SIDE_BAR = "▓ ";
    private final char SEPERATOR = '|';
    private final String BOTTOM_BAR = "▗▄▖";
    private final char BOMB_ICON = '◉';
    // ▗▄▖
    // ▝▀▘

    //Section - ANSI Colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String DEFAULT_TILE_BG_1 = "\u001B[47m";
    public static final String DEFAULT_TILE_BG_2 = "\u001B[100m";
    public static final String BOMB_TEXT = "\033[1;91m";

    public MinesweeperGrid(int size, float BombRatio)
    {
        SetupGrid(size);

        PlaceBombs(BombRatio);
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
                System.out.print(((i+j) % 2 == 0 ? DEFAULT_TILE_BG_1 : DEFAULT_TILE_BG_2)
                        + " " +  output + " " +
                        ANSI_RESET);
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
}
