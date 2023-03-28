import java.util.ArrayList;
import java.util.Vector;

public interface GamePiece {

    void updateLegalNonTakeMoves(ArrayList<GamePiece> pieces);

    boolean updateLegalTakeMoves(ArrayList<GamePiece> pieces);

    void move(int x, int y);

    default boolean updateTakes(ArrayList<GamePiece> pieces) {
        return updateLegalTakeMoves(pieces);
    }

    default void updateMoves(ArrayList<GamePiece> pieces) {
        updateLegalNonTakeMoves(pieces);
    }

    Vector<Integer> getPosition();

    void setPosition(Vector<Integer> position);

    Enum<PieceColor> getColor();

    ArrayList<Vector<Integer>> getLegalNonTakeMoves();

    ArrayList<Vector<Integer>> getLegalTakeMoves();
}
