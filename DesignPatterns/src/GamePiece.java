public interface GamePiece {

    public void updateLegalNonTakeMoves();

    public boolean updateLegalTakeMoves();

    public void move(int x, int y);

    public default void updateTake() {
        updateLegalTakeMoves();
    }

    public default void updateMove() {
        updateLegalNonTakeMoves();
    }

}
