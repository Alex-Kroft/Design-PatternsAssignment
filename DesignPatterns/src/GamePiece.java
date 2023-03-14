public interface GamePiece {

    public boolean checkIfCanMove();

    public boolean checkIfCanTake();

    public void move(int x, int y);

    public void update();

}
