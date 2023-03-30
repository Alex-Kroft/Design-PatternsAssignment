import java.util.ArrayList;
import java.util.Vector;

public class BoardObserver {
    private ArrayList<GamePiece> playerOnePieces;
    private ArrayList<GamePiece> playerTwoPieces;

    public BoardObserver(ArrayList<GamePiece> playerOnePieces, ArrayList<GamePiece> playerTwoPieces) {
        this.playerOnePieces = playerOnePieces;
        this.playerTwoPieces = playerTwoPieces;
    }

    public void notifyOfMove() {
        ArrayList<GamePiece> pieces = new ArrayList<>();
        pieces.addAll(playerOnePieces);
        pieces.addAll(playerTwoPieces);

        findTakes(pieces, playerOnePieces);
        findTakes(pieces, playerTwoPieces);

    }

    private void findTakes(ArrayList<GamePiece> allPieces, ArrayList<GamePiece> playerPieces) {
        ArrayList<Vector<Integer>> takes = new ArrayList<>();
        for (GamePiece piece : playerPieces) {
            piece.updateTakes(allPieces);
            if (!piece.getLegalTakeMoves().isEmpty()) {
                takes.addAll(piece.getLegalTakeMoves());
            }
        }
        if (takes.isEmpty()) {
            for (GamePiece piece : playerPieces) {
                piece.updateMoves(allPieces);
            }
        }

    }

    public void subscribePlayers(ArrayList<GamePiece> playerOnePieces, ArrayList<GamePiece> playerTwoPieces) {
        this.playerOnePieces = playerOnePieces;
        this.playerTwoPieces = playerTwoPieces;
    }
}
