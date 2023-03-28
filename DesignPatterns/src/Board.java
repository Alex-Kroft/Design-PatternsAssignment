import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

public class Board extends JPanel implements MouseListener {
    private ArrayList<GamePiece> playerOnePieces;
    private ArrayList<GamePiece> playerTwoPieces;
    private PiecePool pool;
    private BoardObserver boardObserver;
    private final int squareSize = 50;
    private final int boardSize = 8;
    private GamePiece selectedPiece;
    private ArrayList<Vector<Integer>> selectedPieceAvailableMoves;

    public Board() {
        pool = new PiecePool();
        acquirePieces();
        boardObserver = new BoardObserver(playerOnePieces, playerTwoPieces);
        givePiecesStartingPositions();
        addMouseListener(this);
        selectedPiece = null;
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

    public void movePiece(GamePiece piece, Vector<Integer> target) {
        piece.move(target);
        notifyObserver();
    }

    private void removePiece(GamePiece piece) {
        boardObserver.unsubscribe(piece);
        pool.checkIn(piece);
    }

    public void turnPieceIntoKing(GamePiece piece) {

    }

    public void acquirePieces() {
        playerOnePieces = pool.acquirePlayerOnePieces();
        playerTwoPieces = pool.acquirePlayerTwoPieces();
    }

    private void givePiecesStartingPositions() {
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
            } else {
                g.setColor(Color.RED);
            }
            g.fillOval(x, y, squareSize, squareSize);
        }
    }

    public void notifyObserver() {
        boardObserver.notifyOfMove();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Get the x and y coordinates of the mouse click
        int x = e.getX();
        int y = e.getY();

        // Calculate the square that was clicked on
        int row = y / squareSize;
        int col = x / squareSize;

        Vector<Integer> clickPosition = new Vector<>();
        clickPosition.add(col);
        clickPosition.add(row);
        System.out.println("clicked");

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
