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
        init();

        JPanel buttonPanel = new JPanel();
        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                init();
                board.repaint();
            }
        });
        buttonPanel.add(restartButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void init() {
        board.startGame();
    }
}
