public interface GamePiece {

    public void checkIfCanMove();

    public void checkIfCanTake();

    public void move(int x, int y);

    public default void update() {
        checkIfCanMove();
        checkIfCanTake();
    }

}
