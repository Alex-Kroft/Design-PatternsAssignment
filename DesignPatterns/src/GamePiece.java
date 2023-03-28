import java.util.Vector;

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

    public Vector<Integer> getPosition();

    public void setPosition(Vector<Integer> position);

    public Enum<PieceColor> getColor();
}
