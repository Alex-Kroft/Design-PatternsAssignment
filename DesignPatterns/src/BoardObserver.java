import java.util.ArrayList;

public class BoardObserver {
    private final ArrayList<GamePiece> pieces;

    public BoardObserver(ArrayList<GamePiece> playerOnePieces, ArrayList<GamePiece> playerTwoPieces) {
        this.pieces = new ArrayList<>();
        pieces.addAll(playerOnePieces);
        pieces.addAll(playerTwoPieces);
    }

    public void notifyOfMove() {
        for (GamePiece piece: pieces) {
            piece.updateMoves(pieces);
            piece.updateTakes(pieces);
        }

    }

    public void unsubscribe(GamePiece piece) {
        pieces.remove(piece);
    }
}
