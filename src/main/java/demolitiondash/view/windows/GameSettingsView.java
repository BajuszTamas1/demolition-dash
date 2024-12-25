package demolitiondash.view.windows;

import demolitiondash.model.map.Map;
import demolitiondash.model.map.MapLoader;
import demolitiondash.res.ResourceLoader;
import demolitiondash.view.components.MapG;
import demolitiondash.view.components.GameStore;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This class represents the game settings view.
 * It provides methods for setting up the game settings and starting the game.
 */
public class GameSettingsView {

    private static JPanel panel;
    public static int playerCount = 2;
    private static int mapNumber = 0;
    private static int roundsToWin = 1;
    private ArrayList<Integer> visibleMaps = new ArrayList<>();
    private static GameSettingsView instance = null;

    /**
     * Constructor for the GameSettingsView class.
     * Initializes the game settings view with a given JFrame and sets up the panel.
     * @param frame the parent JFrame
     */
    public GameSettingsView(JFrame frame) {
        try {
            InputStream titleFontStream = getClass().getResourceAsStream("/fonts/INVASION2000.TTF");
            Font titleFont = Font.createFont(Font.TRUETYPE_FONT, titleFontStream);
            InputStream buttonFontStream = getClass().getResourceAsStream("/fonts/Retro Gaming.ttf");
            Font buttonFont = Font.createFont(Font.TRUETYPE_FONT, buttonFontStream);

            Image backgroundImage = ResourceLoader.loadImage("imgs/menuBackground.jpg");

            panel = new JPanel(new GridBagLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage, 0, 0, this);
                }
            };
            GridBagConstraints constraints = new GridBagConstraints();

            constraints.insets = new Insets(-150, 3, 50, 3);

            constraints.weightx = 1.0;


            visibleMaps.add(0);
            visibleMaps.add(1);
            visibleMaps.add(2);

            ImageIcon[] mapIcons = new ImageIcon[3];
            for (int i = 0; i < mapIcons.length; i++) {
                MapG mapG = new MapG(new Map(MapLoader.getInstance().getLayout(visibleMaps.get(i))));
                BufferedImage image = new BufferedImage(mapG.getWidth(), mapG.getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = image.createGraphics();
                mapG.paint(g2d);
                g2d.dispose();
                mapIcons[i] = new ImageIcon(image);
                Image mapImage = mapIcons[i].getImage();
                Image newMapImage = mapImage.getScaledInstance(256, -1, Image.SCALE_SMOOTH);
                mapIcons[i] = new ImageIcon(newMapImage);
            }

            JButton[] mapButtons = new JButton[3];
            for (int i = 0; i < mapButtons.length; i++) {
                mapButtons[i] = new JButton("MAP " + (i + 1));
                mapButtons[i].setPreferredSize(new Dimension(mapIcons[i].getIconWidth(), mapIcons[i].getIconHeight()));
                mapButtons[i].setIcon(mapIcons[i]);
                mapButtons[i].setBorder(new EmptyBorder(0, 10, 0, 0));
                if (i == mapNumber)
                    mapButtons[i].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.RED, 5), new EmptyBorder(0, 10, 0, 0)));
                final int mapIndex = i;
                mapButtons[i].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        mapNumber = mapIndex;
                        removeBorders(mapButtons);
                        mapButtons[mapIndex].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.RED, 5), new EmptyBorder(0, 10, 0, 0)));
                    }
                });
                constraints.gridx = i;
                constraints.gridy = 0;
                panel.add(mapButtons[i], constraints);
            }


            JButton[] playerButtons = new JButton[2];
            String[] playerButtonNames = {"2 Player", "3 Player"};
            for (int i = 0; i < playerButtons.length; i++) {
                playerButtons[i] = new JButton(playerButtonNames[i]);
                playerButtons[i].setFont(buttonFont.deriveFont(Font.PLAIN, 30));
                playerButtons[i].setPreferredSize(new Dimension(210, 75));
                playerButtons[i].setBackground(Color.lightGray);
                playerButtons[i].setOpaque(true);
                if(i+2 == playerCount)
                    playerButtons[i].setBorder(BorderFactory.createLineBorder(Color.RED, 5));
                final int playerCount = i + 2;
                playerButtons[i].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GameSettingsView.playerCount = playerCount;
                        removeBorders(playerButtons);
                        playerButtons[playerCount - 2].setBorder(BorderFactory.createLineBorder(Color.RED, 5));
                    }
                });
                constraints.gridx = 1;
                constraints.gridy = 1;
                constraints.gridwidth = 3;
                constraints.insets = new Insets(0, -475 + (i * 325), 0, 325 - (i * 325));
                panel.add(playerButtons[i], constraints);
            }

            JLabel roundsLabel = new JLabel("Rounds to win");
            roundsLabel.setFont(titleFont.deriveFont(Font.BOLD | Font.ITALIC, 35));
            roundsLabel.setForeground(Color.WHITE);
            constraints.gridx = 0;
            constraints.gridy = 2;
            constraints.gridwidth = 3;
            constraints.insets = new Insets(50, 0, 0, 0);
            panel.add(roundsLabel, constraints);


            JLabel[] gamesToWinLabels = new JLabel[5];
            for (int i = 0; i < gamesToWinLabels.length; i++) {
                gamesToWinLabels[i] = new JLabel(String.valueOf(i + 1));
                gamesToWinLabels[i].setFont(buttonFont.deriveFont(Font.PLAIN, 35));
                gamesToWinLabels[i].setForeground(Color.BLACK);
                gamesToWinLabels[i].setBackground(Color.lightGray);
                gamesToWinLabels[i].setOpaque(true);
                gamesToWinLabels[i].setPreferredSize(new Dimension(50, 50));
                gamesToWinLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
                if(i+1 == roundsToWin)
                    gamesToWinLabels[i].setBorder(BorderFactory.createLineBorder(Color.RED, 5));

                final int rounds = i + 1;
                gamesToWinLabels[i].addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        roundsToWin = rounds;
                        removeBordersFromLabels(gamesToWinLabels);
                        gamesToWinLabels[rounds - 1].setBorder(BorderFactory.createLineBorder(Color.RED, 5));
                    }
                });
                constraints.gridx = 0;
                constraints.gridy = 2;
                constraints.gridwidth = 3;
                constraints.insets = new Insets(100, -300 + (i * 150), -100, 0);
                panel.add(gamesToWinLabels[i], constraints);
            }


            JButton startGameButton = new JButton("START GAME");
            startGameButton.setPreferredSize(new Dimension(275, 75));
            startGameButton.setBackground(Color.lightGray);
            startGameButton.setFont(buttonFont.deriveFont(Font.PLAIN, 30));
            startGameButton.setBorderPainted(false);
            startGameButton.setOpaque(true);
            constraints.gridx = 0;
            constraints.gridy = 3;
            constraints.gridwidth = 3;
            constraints.insets = new Insets(125, 0, -125, 0);
            panel.add(startGameButton, constraints);

            JButton backButton = new JButton("BACK");
            backButton.setPreferredSize(new Dimension(150, 50));
            backButton.setBackground(Color.lightGray);
            backButton.setFont(buttonFont.deriveFont(Font.PLAIN, 30));
            backButton.setBorderPainted(false);
            backButton.setOpaque(true);
            constraints.gridx = 0;
            constraints.gridy = 4;
            constraints.gridwidth = 3;
            constraints.insets = new Insets(150, 0, -150, 0);
            panel.add(backButton, constraints);

            startGameButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.getContentPane().remove(panel);
                    GameView gameView = new GameView(frame, visibleMaps.get(mapNumber), roundsToWin);
                    panel = gameView.getPanel();
                    frame.getContentPane().add(panel);
                    frame.revalidate();
                    frame.repaint();
                }
            });

            backButton.addActionListener(new ActionListener() {
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
            instance = this;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main panel of the game settings view.
     * @return the main panel
     */
    public JPanel getPanel() {
        return panel;
    }

    private void removeBorders(JButton... buttons){
        for (JButton button : buttons) {
            button.setBorder(null);
        }
    }

    private void removeBordersFromLabels(JLabel... labels){
        for (JLabel label : labels) {
            label.setBorder(null);
        }
    }

    /**
     * Returns the current instance of the game settings view.
     * @return the current instance
     */
    public static GameSettingsView getInstance() {
        return instance;
    }

    /**
     * Sets the panel of the game settings view.
     */
    public static void setPanel(){
        panel = GameStore.getPanel();
    }

    /**
     * Sets the default values for the game settings view.
     */
    public void setDefaults(){
        playerCount = 2;
        mapNumber = 0;
        roundsToWin = 1;
    }
}