import java.util.ArrayList;
import java.util.Vector;

public interface GamePiece {

    void updateLegalNonTakeMoves(ArrayList<GamePiece> pieces);

    boolean updateLegalTakeMoves(ArrayList<GamePiece> pieces);

    default void move(Vector<Integer> target) {
        setPosition(target);
    }

    default boolean updateTakes(ArrayList<GamePiece> pieces) {
        return false;
    }

    default void updateMoves(ArrayList<GamePiece> pieces) {

    }

    Vector<Integer> getPosition();

    void setPosition(Vector<Integer> position);

    Enum<PieceColor> getColor();

    ArrayList<Vector<Integer>> getLegalNonTakeMoves();

    ArrayList<Vector<Integer>> getLegalTakeMoves();
}
