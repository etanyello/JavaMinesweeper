import java.util.ArrayList;

public class MinesweeperGrid {
    MinesweeperTile[][] Tiles;
    private int numberOfBombs;


    public final String ANSI_RESET = "\u001B[0m";
    private final String SIDE_BAR = "▓ ";
    private final String BOTTOM_BAR = "▗▄▖";
    private final String BOMB_ICON = "\033[1;91m" + "◉ " + ANSI_RESET;
    // ▗▄▖
    // ▝▀▘

    //Section - ANSI Colors

    public final String DEFAULT_TILE_BG_1 = "\u001B[47m";
    public final String DEFAULT_TILE_BG_2 = "\u001B[100m";

    public final String DEFAULT_UNKNOWN_BG_1 = "\033[1;103m";
    public final String DEFAULT_UNKNOWN_BG_2 = "\033[1;43m";


    public String[] TileColors = {
            "\033[1;97m",
            "\033[1;94m",
            "\033[1;92m",
            "\033[1;91m",
            "\033[1;95m",
            "\033[1;93m",
            "\033[1;96m",
            "\033[1;34m",
            "\033[1;37m"};

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
                Tiles[x][y].SetCoords(x,y);
            }
        }
    }

    private void PlaceBombs(float BombRatio)
    {
        numberOfBombs = (int)(Math.clamp(BombRatio,0f, 1f) * (Tiles.length* Tiles.length));
        int t_bomb = numberOfBombs;

        while(numberOfBombs > 0)
        {
            MinesweeperTile tile = Tiles[(int)(Math.random() * Tiles.length)][(int)(Math.random() * Tiles.length)];
            if(tile.IsBomb()) continue;
            tile.SetBomb(true);
            numberOfBombs--;
        }
        numberOfBombs = t_bomb;
    }

    public boolean SelectTile(int x, int y)
    {
        Tiles[x][y].Reveal();
        return false;
    }

    public void SetSurroundingTiles()
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

    public void ClearSurroundingBombs(int x, int y)
    {
        if(Tiles[x][y].IsBomb()) { Tiles[x][y].SetBomb(false); numberOfBombs--; }
        if(Tiles[x][y].GetSurroundingBombs() == 0) return;

        MinesweeperTile[] surroundingTiles = GetSurroundingTiles(x,y);
        for(MinesweeperTile t : surroundingTiles)
        {
            if(t==null || !t.IsBomb()) continue;
            numberOfBombs--;
            t.SetBomb(false);
        }
    }

    public void FloodFill(int x, int y)
    {
        ArrayList<MinesweeperTile> NeedToCheck = new ArrayList<>();

        //i call this stupid search
        for(MinesweeperTile t : GetSurroundingTiles(x,y))
        {
            if( t==null || t.IsRevealed()) continue;

            if(t.GetSurroundingBombs() != 0) { t.Reveal(); continue; }

            t.Reveal();
            NeedToCheck.add(t);
        }

        for(MinesweeperTile t : NeedToCheck)
        {
            FloodFill(t.GetXCoord(), t.GetYCoord());
        }
    }


    public void PrintGrid()
    {
        System.out.println();
        int j = Tiles.length+1;
        for(int p = Tiles.length - 1 ; p >= 0 ; p--) {
            j--;
            System.out.print(j + " ");
            for(int i = 0 ; i < Tiles[p].length ; i++)
            {
                System.out.print(i==0 ? SIDE_BAR : "");
                System.out.print(PrintTile(Tiles[p][i], i+j));
            }
            System.out.println();
        }
        System.out.print("  █▖");
        for(int i = 0 ; i < Tiles.length; i++)
        {
            System.out.print(BOTTOM_BAR);
        }

        System.out.print("\n    ");
        for(int i = 0 ; i < Tiles.length; i++)
        {
            System.out.print(" " + ((char)(i+97)) + " ");
        }
        System.out.println("\n");
    }

    //Ooooh boy
    public String PrintTile(MinesweeperTile tile, int x) {
        //Set background
        String Output = "";
        if(tile.IsRevealed()) Output += (x % 2 == 0 ? DEFAULT_TILE_BG_1 : DEFAULT_TILE_BG_2) + " ";
        else {Output += (x % 2 == 0 ? DEFAULT_UNKNOWN_BG_1 : DEFAULT_UNKNOWN_BG_2) + "   " + ANSI_RESET; return Output;}

        //Set tile contents
        if (tile.IsBomb()) Output += BOMB_ICON;
        else {
            Output += tile.GetSurroundingBombs() == 0 ? " " : (TileColors[tile.GetSurroundingBombs()] + tile.GetSurroundingBombs());
            Output +=  " " + ANSI_RESET;
        }

        return Output;
    }

    public int GetTotalBombs() { return numberOfBombs; }
    public MinesweeperTile GetTileAt(int x, int y)
    {
        if(x >= Tiles.length || y >= Tiles.length || x < 0 || y < 0) return null;
        return Tiles[x][y];
    }
}
