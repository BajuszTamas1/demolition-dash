package demolitiondash.view.windows;

import demolitiondash.model.Game;
import demolitiondash.res.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.List;

/**
 * This class represents the game over view.
 * It provides methods for displaying the game over screen and handling user input.
 */
public class GameOverView {
    private Game game;
    private JPanel panel = new JPanel();

    /**
     * Constructor for the GameOverView class.
     * Initializes the game over view with a given JFrame, player names, and game instance.
     * @param frame the parent JFrame
     * @param playerNames the names of the players
     * @param game the game instance
     */
    public GameOverView(JFrame frame, List<String> playerNames, Game game) {
        try {
            InputStream titleFontStream = getClass().getResourceAsStream("/fonts/INVASION2000.TTF");
            Font titleFont = Font.createFont(Font.TRUETYPE_FONT, titleFontStream);
            InputStream buttonFontStream = getClass().getResourceAsStream("/fonts/Retro Gaming.ttf");
            Font buttonFont = Font.createFont(Font.TRUETYPE_FONT, buttonFontStream);

            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

            Image backgroundImage = ResourceLoader.loadImage("imgs/menuBackground.jpg");

            this.game = game;

            panel = new JPanel(new GridBagLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage, 0, 0, this);
                }
            };
            panel.setVisible(true);

            GridBagConstraints gbc = new GridBagConstraints();
            JLabel gameOverLabel = new JLabel("Game Over");
            gameOverLabel.setFont(titleFont.deriveFont(Font.BOLD | Font.ITALIC, 80));
            gameOverLabel.setForeground(Color.WHITE);
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.insets = new Insets(-50, 0, 100, 0);
            panel.add(gameOverLabel, gbc);

            gbc.insets = new Insets(0, 0, 0, 0);

            JLabel gameWinnerLabel = new JLabel(game.getWinner() + " won!");
            gameWinnerLabel.setFont(titleFont.deriveFont(Font.BOLD | Font.ITALIC, 50));
            gameWinnerLabel.setForeground(Color.WHITE);
            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.insets = new Insets(0, 0, 50, 0);
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            panel.add(gameWinnerLabel, gbc);


            JButton playAgain = new JButton("Play again");
            playAgain.setFont(buttonFont.deriveFont(Font.BOLD, 40));
            playAgain.setPreferredSize(new Dimension(350, 100));
            playAgain.setBackground(Color.lightGray);
            gbc.gridx = 1;
            gbc.gridy = playerNames.size() + 4;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.insets = new Insets(50, 0, 0, 0);
            panel.add(playAgain, gbc);
            panel.setOpaque(false);


            JButton backToMenu = new JButton("Back to menu");
            backToMenu.setFont(buttonFont.deriveFont(Font.BOLD, 36));
            backToMenu.setPreferredSize(new Dimension(350, 100));
            backToMenu.setBackground(Color.lightGray);
            gbc.gridx = 1;
            gbc.gridy = playerNames.size() + 5;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.insets = new Insets(50, 0, 0, 0);
            panel.add(backToMenu, gbc);

            playAgain.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    frame.getContentPane().remove(panel);
                    GameSettingsView gameSettingsView = new GameSettingsView(frame);
                    gameSettingsView.setDefaults();
                    panel = gameSettingsView.getPanel();
                    frame.getContentPane().add(panel);
                    frame.revalidate();
                    frame.repaint();
                }
            });

            backToMenu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.getContentPane().remove(panel);
                    MainView.currentInstance.resetView();
                    panel = MainView.currentInstance.getPanel();
                    frame.getContentPane().add(panel);
                    frame.revalidate();
                    frame.repaint();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main panel of the game over view.
     * @return the main panel
     */
    public JPanel getPanel() {
        return panel;
    }
}
