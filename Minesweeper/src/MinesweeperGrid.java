import java.util.ArrayList;

public class MinesweeperGrid {
    protected MinesweeperTile[][] Tiles;
    protected int numberOfBombs;


    private final String SIDE_BAR = "▓ ";
    private final String BOTTOM_BAR = "▗▄▖";
    private final String BOMB_ICON = "\033[0;40m" + "\033[1;31m" + " ◉ " + ANSIcolors.ANSI_RESET;
    private final String FLAG_ICON = " \uD83E\uDC37 ";
    // ▗▄▖
    // ▝▀▘
    // 🏳 🚩 🢁A🡻⮟🠷


    //Section - ANSI Colors

    public final String DEFAULT_TILE_BG_1 = "\u001B[47m";
    public final String DEFAULT_TILE_BG_2 = "\u001B[100m";
    public String DEFAULT_UNKNOWN_BG_1 = "\u001B[103m";
    public String DEFAULT_UNKNOWN_BG_2 = "\u001B[43m";
    public String DEFAULT_FLAG_COLOR = "\033[1;91m";


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

    public MinesweeperGrid() {this(8, 0.1f);}
    public MinesweeperGrid(int size, float BombRatio)
    {
        SetupGrid(size);

        PlaceBombs(BombRatio);

        SetSurroundingTiles();
    }

    protected void SetupGrid(int size)
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

    protected void PlaceBombs(float BombRatio)
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

    public boolean FlagTile(int x, int y) { Tiles[x][y].Flag(); return false; }

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

    protected MinesweeperTile[] GetSurroundingTiles(int x, int y)
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
        if(Tiles[x][y].GetSurroundingBombs() != 0 ) return;

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
        int m = ExtraMath.GetDigitsIn(j);

        for(int p = Tiles.length - 1 ; p >= 0 ; p--) {
            j--;
            System.out.print(ANSIcolors.CYAN_TEXT + String.format("%" + m + "s", j) + " " + ANSIcolors.ANSI_RESET);
            for(int i = 0 ; i < Tiles[p].length ; i++)
            {
                System.out.print(i==0 ? SIDE_BAR : "");
                System.out.print(PrintTile(Tiles[p][i], i+j));
            }
            System.out.println();
        }
        System.out.print(String.format("%" + (m+3) + "s", " █▖"));

        for(int i = 0 ; i < Tiles.length; i++)
        {
            System.out.print(BOTTOM_BAR);
        }

        System.out.println(ANSIcolors.CYAN_TEXT);
        System.out.print(String.format("%" + (m+3) + "s", "   "));
        for(int i = 0 ; i < Tiles.length; i++)
        {
            System.out.print(" " + ((char)(i+97)) + " ");
        }
        System.out.println("\n" + ANSIcolors.ANSI_RESET);
    }

    //Ooooh boy
    public String PrintTile(MinesweeperTile tile, int x) {
        //Set background
        String Output = "";
        if(tile.IsRevealed()) Output += (x % 2 == 0 ? DEFAULT_TILE_BG_1 : DEFAULT_TILE_BG_2);
        else if (!tile.IsRevealed() && tile.IsFlagged()) {
            Output += (x % 2 == 0 ? DEFAULT_UNKNOWN_BG_1 : DEFAULT_UNKNOWN_BG_2) + DEFAULT_FLAG_COLOR + FLAG_ICON + ANSIcolors.ANSI_RESET;
            return Output;
        }
        else {
            Output += (x % 2 == 0 ? DEFAULT_UNKNOWN_BG_1 : DEFAULT_UNKNOWN_BG_2) + "   " + ANSIcolors.ANSI_RESET;
            return Output;
        }

        //Set tile contents
        if (tile.IsBomb()) Output += BOMB_ICON;
        else {
            Output +=  " " + (tile.GetSurroundingBombs() == 0 ? " " : (TileColors[tile.GetSurroundingBombs()] + tile.GetSurroundingBombs()));
            Output +=  " " + ANSIcolors.ANSI_RESET;
        }

        return Output;
    }

    public int GetTotalBombs() { return numberOfBombs; }
    public MinesweeperTile GetTileAt(int x, int y)
    {
        if(x >= Tiles.length || y >= Tiles.length || x < 0 || y < 0) return null;
        return Tiles[x][y];
    }
    public boolean CheckForWin()
    {
        return GetRemainingUnrevealedTiles() <= numberOfBombs;
    }

    public float GetUnrevealedTilesRatio(){
        return 1f - ((float) (GetRemainingUnrevealedTiles()-numberOfBombs) / (Tiles.length * Tiles.length));
    }

    private int GetRemainingUnrevealedTiles(){
        int RemainingUnknownTiles = 0;
        for (MinesweeperTile[] tile : Tiles) {
            for (int y = 0; y < Tiles.length; y++) {
                //Counts remaining tiles
                RemainingUnknownTiles += !tile[y].IsRevealed() ? 1 : 0;
            }
        }
        return RemainingUnknownTiles;
    }

    public void SetColorScheme(String grid1, String grid2, String flagColor)
    {
        DEFAULT_UNKNOWN_BG_1 = grid1;
        DEFAULT_UNKNOWN_BG_2 = grid2;
        DEFAULT_FLAG_COLOR = flagColor;
    }
}
