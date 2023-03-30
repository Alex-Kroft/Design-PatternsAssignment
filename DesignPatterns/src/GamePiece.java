import java.util.ArrayList;
import java.util.Vector;

public interface GamePiece {

    void updateLegalNonTakeMoves(ArrayList<GamePiece> pieces);

    void updateLegalTakeMoves(ArrayList<GamePiece> pieces);

    void move(Vector<Integer> target);

    void updateTakes(ArrayList<GamePiece> pieces);

    void updateMoves(ArrayList<GamePiece> pieces);

    Vector<Integer> getPosition();

    void setPosition(Vector<Integer> position);

    PieceColor getColor();

    void setColor(PieceColor color);

    default public boolean isKing() {
        return false;
    }

    ArrayList<Vector<Integer>> getLegalNonTakeMoves();

    ArrayList<Vector<Integer>> getLegalTakeMoves();
}
