import java.util.ArrayList;
import java.util.NoSuchElementException;

public class PiecePool {
    private final ArrayList<GamePiece> playerOnePiecesAvailable = new ArrayList<>();
    private ArrayList<GamePiece> playerOnePiecesInUse = new ArrayList<>();
    private final ArrayList<GamePiece> playerTwoPiecesAvailable = new ArrayList<>();
    private ArrayList<GamePiece> playerTwoPiecesInUse = new ArrayList<>();


    public synchronized void checkIn(GamePiece piece) {
        if(playerOnePiecesInUse.contains(piece)) {
            playerOnePiecesInUse.remove(piece);
            playerOnePiecesAvailable.add(piece);
        } else if (playerTwoPiecesInUse.contains(piece)) {
            playerTwoPiecesInUse.remove(piece);
            playerTwoPiecesAvailable.add(piece);
        }
    }

    public synchronized ArrayList<GamePiece> acquirePlayerOnePieces() {
        System.out.println(playerOnePiecesInUse.size() + "---" + playerOnePiecesAvailable.size());
        if(playerOnePiecesAvailable.isEmpty() && playerOnePiecesInUse.isEmpty()) {
            for (int i = 0; i < 12; i++) {
                playerOnePiecesInUse.add(new KingPiece((BasePiece) PieceFactory.getGamePiece(PieceColor.RED)));
            }
        }
        playerOnePiecesInUse.addAll(playerOnePiecesAvailable);
        playerOnePiecesAvailable.clear();
        for (GamePiece piece: playerOnePiecesInUse) {
            System.out.print(piece.getPosition());
        }
        System.out.println();
        return playerOnePiecesInUse;
    }

    public synchronized ArrayList<GamePiece> acquirePlayerTwoPieces() {
        if(playerTwoPiecesAvailable.isEmpty() && playerTwoPiecesInUse.isEmpty()) {
            for (int i = 0; i < 12; i++) {
                playerTwoPiecesInUse.add(PieceFactory.getGamePiece(PieceColor.BLACK));
            }
        }
        playerTwoPiecesInUse.addAll(playerTwoPiecesAvailable);
        playerTwoPiecesAvailable.clear();
        return playerTwoPiecesInUse;
    }

}
