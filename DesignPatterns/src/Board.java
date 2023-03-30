import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

public class Board extends JPanel implements MouseListener {
    private ArrayList<GamePiece> playerOnePieces;
    private ArrayList<GamePiece> playerTwoPieces;
    private PiecePool pool;
    private BoardObserver boardObserver;
    private final int squareSize = 50;
    private final int boardSize = 8;
    private boolean playerTurn;
    private boolean isGameOver;
    private GamePiece selectedPiece;
    private ArrayList<Vector<Integer>> selectedPieceAvailableMoves;

    public Board() {
        pool = new PiecePool();
        boardObserver = null;
        addMouseListener(this);
        playerOnePieces = new ArrayList<>();
        playerTwoPieces = new ArrayList<>();
    }

    public void startGame() {
        playerOnePieces.clear();
        playerTwoPieces.clear();
        acquirePieces();
        if (boardObserver == null) {
            boardObserver = new BoardObserver(playerOnePieces, playerTwoPieces);
        } else {
            boardObserver.subscribePlayers(playerOnePieces, playerTwoPieces);
        }
        setPiecesForStartOfGame();
        selectedPiece = null;
        setPlayerTurn(true);
        setGameOver(false);
        notifyObserver();
    }

    public ArrayList<GamePiece> getPlayerOnePieces() {
        return playerOnePieces;
    }

    public void setPlayerOnePieces(ArrayList<GamePiece> playerOnePieces) {
        this.playerOnePieces = playerOnePieces;
    }

    public ArrayList<GamePiece> getPlayerTwoPieces() {
        return playerTwoPieces;
    }

    public void setPlayerTwoPieces(ArrayList<GamePiece> playerTwoPieces) {
        this.playerTwoPieces = playerTwoPieces;
    }

    public PiecePool getPool() {
        return pool;
    }

    public void setPool(PiecePool pool) {
        this.pool = pool;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public void movePiece(GamePiece piece, Vector<Integer> target) {
        Vector<Integer> piecePosition = piece.getPosition();
        piece.move(target);
        removePieces(piece, piecePosition, target);

        if (piece.getColor().equals(PieceColor.BLACK)) {
            if (target.get(1) == 7) {
                playerTwoPieces.remove(piece);
                piece = new KingPiece((BasePiece) piece);
                playerTwoPieces.add(piece);

                notifyObserver();
                if (!piece.getLegalTakeMoves().isEmpty()) {
                    changePlayerTurn();
                }
            }
        } else if (piece.getColor().equals(PieceColor.RED)) {
            if (target.get(1) == 0) {
                playerOnePieces.remove(piece);
                piece = new KingPiece((BasePiece) piece);
                playerOnePieces.add(piece);

                notifyObserver();
                if (!piece.getLegalTakeMoves().isEmpty()) {
                    changePlayerTurn();
                }
            }
        }
    }

    private void removePieces(GamePiece pieceToMove, Vector<Integer> start, Vector<Integer> finish) {
        int startX = start.get(0);
        int startY = start.get(1);
        int finishX = finish.get(0);
        int finishY = finish.get(1);

        int deltaX = (finishX > startX) ? 1 : -1;
        int deltaY = (finishY > startY) ? 1 : -1;

        int x = startX + deltaX;
        int y = startY + deltaY;

        ArrayList<GamePiece> piecesToRemove = new ArrayList<>();

        while (x != finishX && y != finishY) {
            Vector<Integer> remove = new Vector<>();
            remove.add(x);
            remove.add(y);

            for (GamePiece piece: playerOnePieces) {
                if (piece.getPosition().equals(remove)) {
                    piecesToRemove.add(piece);
                }
            }

            for (GamePiece piece: playerTwoPieces) {
                if (piece.getPosition().equals(remove)) {
                    piecesToRemove.add(piece);
                }
            }

            x += deltaX;
            y += deltaY;
        }

        if (!piecesToRemove.isEmpty()) {
            for (GamePiece piece: piecesToRemove) {
                playerOnePieces.remove(piece);
                playerTwoPieces.remove(piece);
                pool.checkIn(piece);
                repaint();
            }
            ArrayList<GamePiece> allPieces = new ArrayList<>();
            allPieces.addAll(playerOnePieces);
            allPieces.addAll(playerTwoPieces);
            pieceToMove.updateTakes(allPieces);

            if (pieceToMove.getLegalTakeMoves().isEmpty()) {
                changePlayerTurn();
                notifyObserver();
            } else {
                if (playerOnePieces.contains(pieceToMove)) {
                    selectedPiece = pieceToMove;
                    selectedPieceAvailableMoves = pieceToMove.getLegalTakeMoves();
                }
            }

        } else {
            changePlayerTurn();
            notifyObserver();
        }

    }

    public void changePlayerTurn() {
        setPlayerTurn(!playerTurn);
    }

    public void acquirePieces() {
        playerOnePieces.addAll(pool.acquirePlayerOnePieces());
        playerTwoPieces.addAll(pool.acquirePlayerTwoPieces());
    }

    private void setPiecesForStartOfGame() {
        int placingIndex = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                int x1 = j*2 + (1 - (i %2));
                int y1 = i;
                Vector<Integer> position1 = new Vector<>();
                position1.add(x1);
                position1.add(y1);

                int x2 = boardSize - 1 - x1;
                int y2 = boardSize - 1 - y1;
                Vector<Integer> position2 = new Vector<>();
                position2.add(x2);
                position2.add(y2);

                playerTwoPieces.get(placingIndex).setPosition(position1);
                playerOnePieces.get(placingIndex).setPosition(position2);
                playerTwoPieces.get(placingIndex).setColor(PieceColor.BLACK);
                playerOnePieces.get(placingIndex).setColor(PieceColor.RED);

                placingIndex++;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the checkerboard squares
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                // Calculate the x and y coordinates of the square
                int x = i * squareSize;
                int y = j * squareSize;

                // Fill the square with the appropriate color
                if ((i + j) % 2 == 0) {
                    g.setColor(Color.LIGHT_GRAY);
                } else {
                    g.setColor(Color.DARK_GRAY);
                }
                g.fillRect(x, y, squareSize, squareSize);
            }
        }

        if (selectedPiece != null) {
            if (!selectedPieceAvailableMoves.isEmpty()) {
                // Highlight available moves
                g.setColor(new Color(255, 255, 0, 128));
                for (Vector<Integer> move : selectedPieceAvailableMoves) {
                    int x = move.get(0) * squareSize;
                    int y = move.get(1) * squareSize;
                    g.fillRect(x, y, squareSize, squareSize);
                }
            }
        }

        // Draw the pieces on the board
        drawEachGamePiece(g);
    }

    private void drawEachGamePiece(Graphics g) {
        ArrayList<GamePiece> playerPieces = new ArrayList<>();
        playerPieces.addAll(playerOnePieces);
        playerPieces.addAll(playerTwoPieces);
        for (GamePiece piece : playerPieces) {
            Vector<Integer> position = piece.getPosition();
            int x = position.get(0) * squareSize;
            int y = position.get(1) * squareSize;
            Enum<PieceColor> color = piece.getColor();
            if (color == PieceColor.BLACK) {
                g.setColor(Color.BLACK);
            }
            if (color == PieceColor.DARK_GRAY) {
                g.setColor(new Color(10, 0, 25, 128));
            }
            if (color == PieceColor.RED) {
                g.setColor(Color.RED);
            }
            if (color == PieceColor.DARK_RED) {
                g.setColor(new Color(200, 0, 0));
            }
            g.fillOval(x, y, squareSize, squareSize);
        }
    }

    public void notifyObserver() {
        boardObserver.notifyOfMove();
    }

    public void computerMove() {
        HashMap<GamePiece, ArrayList<Vector<Integer>>> possibleMoves = new HashMap<>();
        for (GamePiece piece: playerTwoPieces) {
            if (!piece.getLegalTakeMoves().isEmpty()) {
                possibleMoves.put(piece, piece.getLegalTakeMoves());
            }
        }
        if (possibleMoves.isEmpty()) {
            for (GamePiece piece: playerTwoPieces) {
                if (!piece.getLegalNonTakeMoves().isEmpty()) {
                    possibleMoves.put(piece, piece.getLegalNonTakeMoves());
                }
            }
        }
        if (possibleMoves.isEmpty()) {
            System.out.println("Wow, guess you won");
            setGameOver(true);
        } else {

            int chosenPieceIndex = new Random().nextInt(possibleMoves.size());
            GamePiece chosenPiece = (GamePiece) possibleMoves.keySet().toArray()[chosenPieceIndex];
            ArrayList<Vector<Integer>> chosenPiecePossibleMoves = possibleMoves.get(chosenPiece);
            Vector<Integer> chosenMove = chosenPiecePossibleMoves.get(new Random().nextInt(chosenPiecePossibleMoves.size()));
            movePiece(chosenPiece, chosenMove);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (playerTurn) {
            // Get the x and y coordinates of the mouse click
            int x = e.getX();
            int y = e.getY();

            // Calculate the square that was clicked on
            int row = y / squareSize;
            int col = x / squareSize;

            Vector<Integer> clickPosition = new Vector<>();
            clickPosition.add(col);
            clickPosition.add(row);

            if (selectedPiece != null) {
                if (selectedPieceAvailableMoves.contains(clickPosition)) {
                    movePiece(selectedPiece, clickPosition);
                }
                selectedPiece = null;
                selectedPieceAvailableMoves = null;

            } else {
                // Check if a piece was clicked
                for (GamePiece piece : playerOnePieces) {
                    if (piece.getPosition().equals(clickPosition)) {
                        selectedPiece = piece;
                        selectedPieceAvailableMoves = new ArrayList<>();
                        selectedPieceAvailableMoves.addAll(selectedPiece.getLegalNonTakeMoves());
                        selectedPieceAvailableMoves.addAll(selectedPiece.getLegalTakeMoves());
                        break;
                    }
                }
            }
            repaint();
        } else {
            computerMove();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
