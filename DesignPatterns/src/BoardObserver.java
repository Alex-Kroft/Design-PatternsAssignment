import java.util.ArrayList;
import java.util.Vector;

public class BoardObserver {
    private ArrayList<GamePiece> playerOnePieces;
    private ArrayList<GamePiece> playerTwoPieces;

    public BoardObserver(ArrayList<GamePiece> playerOnePieces, ArrayList<GamePiece> playerTwoPieces) {
        this.playerOnePieces = new ArrayList<>();
        this.playerOnePieces.addAll(playerOnePieces);
        this.playerTwoPieces = new ArrayList<>();
        this.playerTwoPieces.addAll(playerTwoPieces);
    }

    public void notifyOfMove(boolean playerTurn) {
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

    public void unsubscribe(GamePiece piece) {
        if (playerOnePieces.contains(piece)) {
            playerOnePieces.remove(piece);
        } else {
            playerTwoPieces.remove(piece);
        }
    }

    public void subscribePlayers(ArrayList<GamePiece> playerOnePieces, ArrayList<GamePiece> playerTwoPieces) {
        this.playerOnePieces.clear();
        this.playerOnePieces.addAll(playerOnePieces);
        this.playerTwoPieces.clear();
        this.playerTwoPieces.addAll(playerTwoPieces);
    }
}
