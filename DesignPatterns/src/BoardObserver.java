import java.util.ArrayList;

public class BoardObserver {
    private final ArrayList<GamePiece> pieces;

    public BoardObserver(ArrayList<GamePiece> playerOnePieces, ArrayList<GamePiece> playerTwoPieces) {
        this.pieces = new ArrayList<>();
        pieces.addAll(playerOnePieces);
        pieces.addAll(playerTwoPieces);
    }

    public void notifyOfMove() {
        boolean isTakePossible = false;
        for (GamePiece piece: pieces) {
            if (piece.updateTakes(pieces)) {
                isTakePossible = true;
            }
        }
        if (!isTakePossible) {
            for (GamePiece piece: pieces) {
                piece.updateMoves(pieces);
            }
        }
    }

    public void unsubscribe(GamePiece piece) {
        pieces.remove(piece);
    }
}
