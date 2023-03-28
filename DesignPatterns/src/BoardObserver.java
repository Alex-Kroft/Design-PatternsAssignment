import java.util.ArrayList;

public class BoardObserver {
    private ArrayList<GamePiece> pieces;

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
    }
}
