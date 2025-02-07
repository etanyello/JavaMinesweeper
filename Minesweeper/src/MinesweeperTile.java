public class MinesweeperTile {
    private boolean isBomb;
    private boolean isRevealed = false;
    private int surroundingBombs;

    public boolean IsBomb() { return isBomb; }
    public void SetBomb(boolean t) { isBomb = t; }

    public boolean IsRevealed() { return isRevealed; }
    public void Reveal() { isRevealed = true; }

    public int GetSurroundingBombs() { return surroundingBombs; }
    public void SetSurroundingBombs(MinesweeperTile[] SurroundingTiles)
    {
        for(MinesweeperTile t : SurroundingTiles)
        {
            if(t==null || !t.isBomb) continue;
            surroundingBombs++;
        }
    }
}
