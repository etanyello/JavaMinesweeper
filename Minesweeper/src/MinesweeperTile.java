public class MinesweeperTile {
    private boolean isBomb;
    private boolean isRevealed = false;
    private boolean isFlag = false;

    private int surroundingBombs;
    private int xCord, yCord;

    public boolean IsBomb() { return isBomb; }
    public void SetBomb(boolean t) { isBomb = t; }

    public boolean IsRevealed() { return isRevealed; }
    public void Reveal() { isRevealed = true; }

    public boolean IsFlagged() {return isFlag;}
    public void Flag() {isFlag = !isFlag;}

    public void SetCoords(int i, int j) {xCord = i ; yCord = j;}
    public int GetXCoord() {return xCord;}
    public int GetYCoord() {return yCord;}

    public int GetSurroundingBombs() { return surroundingBombs; }
    public void SetSurroundingBombs(MinesweeperTile[] SurroundingTiles)
    {
        surroundingBombs = 0;
        for(MinesweeperTile t : SurroundingTiles)
        {
            if(t==null || !t.isBomb) continue;
            surroundingBombs++;
        }
    }
}
