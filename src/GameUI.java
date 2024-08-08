import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameUI extends JFrame {
    private Game game;
    private JButton[][] playerButtons;
    private JButton[][] computerButtons;
    private int size = 10; // Traditional Battleship grid size

    public GameUI() {
        game = new Game(size);
        game.placeShips(game.getPlayerBoard());
        game.placeShips(game.getComputerBoard());

        setTitle("Bataille Navale");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));

        JPanel playerPanel = new JPanel(new GridLayout(size, size));
        JPanel computerPanel = new JPanel(new GridLayout(size, size));

        playerButtons = new JButton[size][size];
        computerButtons = new JButton[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                playerButtons[i][j] = new JButton("~");
                playerPanel.add(playerButtons[i][j]);

                computerButtons[i][j] = new JButton("~");
                computerButtons[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JButton button = (JButton) e.getSource();
                        int x = -1, y = -1;
                        for (int i = 0; i < size; i++) {
                            for (int j = 0; j < size; j++) {
                                if (computerButtons[i][j] == button) {
                                    x = i;
                                    y = j;
                                    break;
                                }
                            }
                        }

                        // Le joueur joue son coup
                        if (game.playerTurn(x, y)) {
                            button.setText("X");
                        } else {
                            button.setText("O");
                        }

                        if (game.isPlayerWin()) {
                            JOptionPane.showMessageDialog(null, "Vous avez gagné !");
                            System.exit(0);
                        }

                        // Forcer la mise à jour de l'interface graphique
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                // L'ordinateur joue son coup
                                if (game.computerTurn()) {
                                    int[] move = game.getLastComputerMove();
                                    if (game.getPlayerBoard().checkHit(move[0], move[1])) {
                                        playerButtons[move[0]][move[1]].setText("X");
                                    } else {
                                        playerButtons[move[0]][move[1]].setText("O");
                                    }
                                }

                                if (game.isComputerWin()) {
                                    JOptionPane.showMessageDialog(null, "L'ordinateur a gagné !");
                                    System.exit(0);
                                }
                            }
                        });
                    }
                });
                computerPanel.add(computerButtons[i][j]);
            }
        }

        add(playerPanel);
        add(computerPanel);
    }

}
