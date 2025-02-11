public class CrossGrid extends  MinesweeperGrid{

    @Override
    protected MinesweeperTile[] GetSurroundingTiles(int x, int y)
    {
        MinesweeperTile[] SurroundingTiles = new MinesweeperTile[8];

        if(x+1 < Tiles.length && y+1 < Tiles.length) SurroundingTiles[4] = Tiles[x+1][y+1];
        if(x-1 >= 0 && y-1 >= 0) SurroundingTiles[5] = Tiles[x-1][y-1];
        if(x+1 < Tiles.length && y-1 >= 0) SurroundingTiles[6] = Tiles[x+1][y-1];
        if(x-1 >= 0 && y+1 < Tiles.length) SurroundingTiles[7] = Tiles[x-1][y+1];

        return SurroundingTiles;
    }

    public CrossGrid(int size, float bombRatio)
    {
        super(size, bombRatio);
    }
}
