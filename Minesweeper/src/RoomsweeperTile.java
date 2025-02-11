public class RoomsweeperTile extends MinesweeperTile{
    private boolean isWall;
    public boolean IsWall() { return isWall; }
    public void SetWall(boolean t) {
        if(IsBomb()) return;
        isWall = t;
    }
}
