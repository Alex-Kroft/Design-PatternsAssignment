import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Game extends JFrame{

    private Board board;
    public Game() {
        setLayout(new BorderLayout());
        this.board = new Board();
        add(board, BorderLayout.CENTER);
        board.startGame();
    }


}
