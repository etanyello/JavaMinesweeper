public class CardinalGrid extends MinesweeperGrid{

    @Override
    protected MinesweeperTile[] GetSurroundingTiles(int x, int y)
    {
        MinesweeperTile[] SurroundingTiles = new MinesweeperTile[8];

        if(x+1 < Tiles.length) SurroundingTiles[0] = Tiles[x+1][y];
        if(x-1 >= 0) SurroundingTiles[1] = Tiles[x-1][y];
        if(y+1 < Tiles.length) SurroundingTiles[2] = Tiles[x][y+1];
        if(y-1 >= 0) SurroundingTiles[3] = Tiles[x][y-1];


        return SurroundingTiles;
    }

    public CardinalGrid(int size, float bombRatio)
    {
        super(size, bombRatio);
    }
}
