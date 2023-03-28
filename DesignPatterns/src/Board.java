import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Board extends JPanel implements MouseListener {
    private ArrayList<GamePiece> playerOnePieces;
    private ArrayList<GamePiece> playerTwoPieces;
    private PiecePool pool;
    private final int squareSize = 50;
    private final int boardSize = 8;

    public Board() {
        pool = new PiecePool();
        acquirePieces();
        addMouseListener(this);
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

    public void movePiece(GamePiece piece, int posX, int posY) {

    }

    private void removePiece(GamePiece piece) {
        pool.checkIn(piece);
    }

    public void turnPieceIntoKing(GamePiece piece) {

    }

    public void acquirePieces() {
        playerOnePieces = pool.acquirePlayerOnePieces();
        playerTwoPieces = pool.acquirePlayerTwoPieces();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the checker board squares
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

        // Draw the pieces on the board
        /*
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int x = i * squareSize;
                int y = j * squareSize;

                // Draw the piece if there is one on this square
                if (boardState[i][j] == 1) {
                    g.setColor(Color.BLACK);
                    g.fillOval(x, y, squareSize, squareSize);
                } else if (boardState[i][j] == 2) {
                    g.setColor(Color.WHITE);
                    g.fillOval(x, y, squareSize, squareSize);
                }
            }
        }*/
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        // Get the x and y coordinates of the mouse click
        int x = e.getX();
        int y = e.getY();

        // Calculate the square that was clicked on
        int row = y / squareSize;
        int col = x / squareSize;

        System.out.print(row);
        System.out.print(" ");
        System.out.println(col);
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
