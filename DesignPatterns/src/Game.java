import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game {
    private JFrame frame;
    private JPanel panel;
    private JButton[][] squares;
    private Game.Piece[][] pieces;
    private boolean turnWhite;

    public void init() {
        frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(520, 520);

        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawCheckerboard(g);
                drawPieces(g);
            }
        };

        panel.setLayout(new GridLayout(8, 8));
        squares = new JButton[8][8];
        pieces = new Game.Piece[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squares[row][col] = new JButton();
                squares[row][col].addActionListener(new Game.ButtonListener());
                squares[row][col].setBorder(new RoundBtn(100));
                panel.add(squares[row][col]);
            }
        }

        turnWhite = true;
        frame.add(panel);
        frame.setVisible(true);
    }

    public void drawCheckerboard(Graphics g) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (row % 2 == col % 2) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(row * 60, col * 60, 60, 60);
            }
        }
    }

    public void drawPieces(Graphics g){
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 8; col++) {
                if (row % 2 == col % 2) {
                    pieces[row][col] = new Game.Piece(true);
                }
            }
        }
        for (int row = 5; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (row % 2 == col % 2) {
                    pieces[row][col] = new Game.Piece(false);
                }
            }
        }
    }

    public void makeMove(int row1, int col1, int row2, int col2) {
        if (pieces[row1][col1] != null) {
            if (isValidMove(row1, col1, row2, col2)) {
                pieces[row2][col2] = pieces[row1][col1];
                pieces[row1][col1] = null;
                if (row2 == 0 && pieces[row2][col2].isWhite()) {
                    pieces[row2][col2].setKing(true);
                } else if (row2 == 7 && !pieces[row2][col2].isWhite()) {
                    pieces[row2][col2].setKing(true);
                }
                if (Math.abs(row1 - row2) > 1) {
                    int captureRow = (row1 + row2) / 2;
                    int captureCol = (col1 + col2) / 2;
                    pieces[captureRow][captureCol] = null;
                    if (row2 + 2 < 8) {
                        if (pieces[row2 + 2][col2] != null && !pieces[row2 + 2][col2].isWhite() && pieces[row2][col2].isWhite()) {
                            if (pieces[row2 + 2][col2].isKing()) {
                                if (row2 + 4 < 8) {
                                    if (pieces[row2 + 4][col2] != null && pieces[row2 + 4][col2].isWhite()) {
                                        pieces[row2 + 2][col2] = null;
                                    }
                                }
                            } else {
                                pieces[row2 + 2][col2] = null;
                            }
                        }
                    }
                    if (row2 - 2 >= 0) {
                        if (pieces[row2 - 2][col2] != null && !pieces[row2 - 2][col2].isWhite() && pieces[row2][col2].isWhite()) {
                            if (pieces[row2 - 2][col2].isKing()) {
                                if (row2 - 4 >= 0) {
                                    if (pieces[row2 - 4][col2] != null && pieces[row2 - 4][col2].isWhite()) {
                                        pieces[row2 - 2][col2] = null;
                                    }
                                }
                            } else {
                                pieces[row2 - 2][col2] = null;
                            }
                        }
                    }
                    if (col2 + 2 < 8) {
                        if (pieces[row2][col2 + 2] != null && !pieces[row2][col2 + 2].isWhite() && pieces[row2][col2].isWhite()) {
                            if (pieces[row2][col2 + 2].isKing()) {
                                if (col2 + 4 < 8) {
                                    if (pieces[row2][col2 + 4] != null && pieces[row2][col2 + 4].isWhite()) {
                                        pieces[row2][col2 + 2] = null;
                                    }
                                }
                            } else {
                                pieces[row2][col2 + 2] = null;
                            }
                        }
                    }
                    if (col2 - 2 >= 0) {
                        if (pieces[row2][col2 - 2] != null && !pieces[row2][col2 - 2].isWhite() && pieces[row2][col2].isWhite()) {
                            if (pieces[row2][col2 - 2].isKing()) {
                                if (col2 - 4 >= 0) {
                                    if (pieces[row2][col2 - 4] != null && pieces[row2][col2 - 4].isWhite()) {
                                        pieces[row2][col2 - 2] = null;
                                    }
                                }
                            } else {
                                pieces[row2][col2 - 2] = null;
                            }
                        }
                    }
                }
                turnWhite = !turnWhite;
                if (checkWin()) {
                    JOptionPane.showMessageDialog(null, (turnWhite ? "White" : "Black") + " has won the game!");
                    frame.dispose();
                }
            }
        }
    }

    public boolean isValidMove(int row1, int col1, int row2, int col2) {
        if (row2 < 0 || row2 > 7 || col2 < 0 || col2 > 7) {
            return false;
        }
        if (pieces[row2][col2] != null) {
            return false;
        }
        if (pieces[row1][col1].isWhite() != turnWhite) {
            return false;
        }
        if (Math.abs(row1 - row2) == 1) {
            if (pieces[row1][col1].isKing()) {
                return Math.abs(col1 - col2) == 1;
            } else {
                if (turnWhite && row2 - row1 == 1) {
                    return Math.abs(col1 - col2) == 1;
                } else if (!turnWhite && row2 - row1 == -1) {
                    return Math.abs(col1 - col2) == 1;
                }
            }
        } else if (Math.abs(row1 - row2) == 2) {
            if (pieces[row1][col1].isKing()) {
                if (Math.abs(col1 - col2) == 2) {
                    int captureRow = (row1 + row2) / 2;
                    int captureCol = (col1 + col2) / 2;
                    return pieces[captureRow][captureCol] != null
                            && pieces[captureRow][captureCol].isWhite() != turnWhite;
                }
            } else {
                if (turnWhite && row2 - row1 == 2) {
                    if (Math.abs(col1 - col2) == 2) {
                        int captureRow = (row1 + row2) / 2;
                        int captureCol = (col1 + col2) / 2;
                        return pieces[captureRow][captureCol] != null
                                && pieces[captureRow][captureCol].isWhite() != turnWhite;
                    }
                } else if (!turnWhite && row2 - row1 == -2) {
                    if (Math.abs(col1 - col2) == 2) {
                        int captureRow = (row1 + row2) / 2;
                        int captureCol = (col1 + col2) / 2;
                        return pieces[captureRow][captureCol] != null
                                && pieces[captureRow][captureCol].isWhite() != turnWhite;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkWin() {
        int white = 0;
        int black = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (pieces[row][col] != null) {
                    if (pieces[row][col].isWhite()) {
                        white++;
                    } else {
                        black++;
                    }
                }
            }
        }
        return white == 0 || black == 0;
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            if (btn.getBackground() == Color.GRAY) {
                if (turnWhite) {
                    btn.setBackground(Color.WHITE);
                    String[] pos = btn.getName().split("-");
                    makeMove(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]), Integer.parseInt(pos[2]), Integer.parseInt(pos[3]));
                } else {
                    btn.setBackground(Color.BLACK);
                    String[] pos = btn.getName().split("-");
                    makeMove(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]), Integer.parseInt(pos[2]), Integer.parseInt(pos[3]));
                }
            } else {
                int row = 0;
                int col = 0;
                mainLoop:
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (btn.equals(squares[i][j])) {
                            row = i;
                            col = j;
                            break mainLoop;
                        }
                    }
                }
                if (pieces[row][col] != null && pieces[row][col].isWhite() == turnWhite) {
                    if (pieces[row][col].isKing()) {
                        if (row + 2 < 8) {
                            if (pieces[row + 2][col] == null && pieces[row + 1][col] != null && pieces[row + 1][col].isWhite() != turnWhite) {
                                squares[row][col].setBackground(Color.GRAY);
                                squares[row + 2][col].setName(row + "-" + col + "-" + (row + 2) + "-" + col);
                            }
                        }
                        if (row - 2 >= 0) {
                            if (pieces[row - 2][col] == null && pieces[row - 1][col] != null && pieces[row - 1][col].isWhite() != turnWhite) {
                                squares[row][col].setBackground(Color.GRAY);
                                squares[row - 2][col].setName(row + "-" + col + "-" + (row - 2) + "-" + col);
                            }
                        }
                        if (col + 2 < 8) {
                            if (pieces[row][col + 2] == null && pieces[row][col + 1] != null && pieces[row][col + 1].isWhite() != turnWhite) {
                                squares[row][col].setBackground(Color.GRAY);
                                squares[row][col + 2].setName(row + "-" + col + "-" + row + "-" + (col + 2));
                            }
                        }
                        if (col - 2 >= 0) {
                            if (pieces[row][col - 2] == null && pieces[row][col - 1] != null && pieces[row][col - 1].isWhite() != turnWhite) {
                                squares[row][col].setBackground(Color.GRAY);
                                squares[row][col - 2].setName(row + "-" + col + "-" + row + "-" + (col - 2));
                            }
                        }
                    } else {
                        if (turnWhite && row + 2 < 8) {
                            if (pieces[row + 2][col] == null && pieces[row + 1][col] != null && pieces[row + 1][col].isWhite() != turnWhite) {
                                squares[row][col].setBackground(Color.GRAY);
                                squares[row + 2][col].setName(row + "-" + col + "-" + (row + 2) + "-" + col);
                            }
                        } else if (!turnWhite && row - 2 >= 0) {
                            if (pieces[row - 2][col] == null && pieces[row - 1][col] != null
                                    && pieces[row - 1][col].isWhite() != turnWhite) {
                                squares[row][col].setBackground(Color.GRAY);
                                squares[row - 2][col].setName(row + "-" + col + "-" + (row - 2) + "-" + col);
                            }

                        } else {
                            if (pieces[row][col] != null && pieces[row][col].isWhite() != turnWhite) {
                                if (pieces[row][col].isKing()) {
                                    if (row + 2 < 8) {
                                        if (pieces[row + 2][col] == null && pieces[row + 1][col] != null
                                                && pieces[row + 1][col].isWhite() == turnWhite) {
                                            squares[row][col].setBackground(Color.GRAY);
                                            squares[row + 2][col].setName(row + "-" + col + "-" + (row + 2) + "-" + col);
                                        }
                                    }
                                    if (row - 2 >= 0) {
                                        if (pieces[row - 2][col] == null && pieces[row - 1][col] != null
                                                && pieces[row - 1][col].isWhite() == turnWhite) {
                                            squares[row][col].setBackground(Color.GRAY);
                                            squares[row - 2][col].setName(row + "-" + col + "-" + (row - 2) + "-" + col);
                                        }
                                    }
                                    if (col + 2 < 8) {
                                        if (pieces[row][col + 2] == null && pieces[row][col + 1] != null
                                                && pieces[row][col + 1].isWhite() == turnWhite) {
                                            squares[row][col].setBackground(Color.GRAY);
                                            squares[row][col + 2].setName(row + "-" + col + "-" + row + "-" + (col + 2));
                                        }
                                    }
                                    if (col - 2 >= 0) {
                                        if (pieces[row][col - 2] == null && pieces[row][col - 1] != null
                                                && pieces[row][col - 1].isWhite() == turnWhite) {
                                            squares[row][col].setBackground(Color.GRAY);
                                            squares[row][col - 2].setName(row + "-" + col + "-" + row + "-" + (col - 2));
                                        }
                                    }
                                } else {
                                    if (turnWhite && row + 2 < 8) {
                                        if (pieces[row + 2][col] == null && pieces[row + 1][col] != null
                                                && pieces[row + 1][col].isWhite() == turnWhite) {
                                            squares[row][col].setBackground(Color.GRAY);
                                            squares[row + 2][col].setName(row + "-" + col + "-" + (row + 2) + "-" + col);
                                        }
                                    } else if (!turnWhite && row - 2 >= 0) {
                                        if (pieces[row - 2][col] == null && pieces[row - 1][col] != null
                                                && pieces[row - 1][col].isWhite() == turnWhite) {
                                            squares[row][col].setBackground(Color.GRAY);
                                            squares[row - 2][col].setName(row + "-" + col + "-" + (row - 2) + "-" + col);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    panel.repaint();
                }
            }
        }
    }

    public static class Piece {
        private final boolean white;
        private boolean king;

        public Piece(boolean white) {
            this.white = white;
            this.king = false;
        }

        public boolean isWhite() {
            return white;
        }

        public boolean isKing() {
            return king;
        }

        public void setKing(boolean king) {
            this.king = king;
        }
    }

    class RoundBtn implements Border
    {
        private int r;
        RoundBtn(int r) {
            this.r = r;
        }
        public Insets getBorderInsets(Component c) {
            return new Insets(this.r+1, this.r+1, this.r+2, this.r);
        }
        public boolean isBorderOpaque() {
            return true;
        }
        public void paintBorder(Component c, Graphics g, int x, int y,
                                int width, int height) {
            g.drawRoundRect(x, y, width-1, height-1, r, r);
        }
    }
}
