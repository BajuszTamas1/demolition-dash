package demolitiondash.view.windows;

import demolitiondash.model.*;
import demolitiondash.model.map.Map;
import demolitiondash.model.map.MapLayout;
import demolitiondash.model.map.MapLoader;
import demolitiondash.model.powerup.Powerup;
import demolitiondash.res.ResourceLoader;
import demolitiondash.view.components.BombG;
import demolitiondash.view.components.MapG;
import demolitiondash.view.control.Control;
import demolitiondash.model.Player;

import java.io.InputStream;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;

/**
 * This class represents the game view.
 * It provides methods for displaying the game view and updating the game state.
 */

public class GameView {
    private JPanel panel;

    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel bottomPanel;
    private JPanel scorePanel;

    private final JLabel player1BombCount = new JLabel();
    private final JLabel player2BombCount = new JLabel();
    private final JLabel player3BombCount = new JLabel();
    private final JLabel score = new JLabel();

    private List<Player> players;
    public static List<Integer> scores = new ArrayList<>();
    private final List<String> names;
    private final List<List<Powerup>> powerups = new ArrayList<>();

    private String[] playerNames;

    private JPanel[] playerPanels = new JPanel[GameSettingsView.playerCount];

    private JLabel[] bombCountLabels = {player1BombCount, player2BombCount, player3BombCount};

    private final ImageIcon blastRadius = new ImageIcon(ResourceLoader.loadImage("imgs/assets/Items/Pickups/blastradius.jpg"));
    private final ImageIcon bombUp = new ImageIcon(ResourceLoader.loadImage("imgs/assets/Items/Pickups/bombup.jpg"));
    private final ImageIcon rollerBlade = new ImageIcon(ResourceLoader.loadImage("imgs/assets/Items/Pickups/rollerblade.jpg"));
    private final ImageIcon invincibility = new ImageIcon(ResourceLoader.loadImage("imgs/assets/Items/Pickups/invincibility.jpg"));
    private final ImageIcon ghost = new ImageIcon(ResourceLoader.loadImage("imgs/assets/Items/Pickups/ghost.jpg"));
    private final ImageIcon detonator = new ImageIcon(ResourceLoader.loadImage("imgs/assets/Items/Pickups/detonator.jpg"));
    private final Image barricadeImage = ResourceLoader.loadImage("imgs/assets/Items/Pickups/barricade.png");
    private final Image resizedBarricadeImage = barricadeImage.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
    private final ImageIcon barricade = new ImageIcon(resizedBarricadeImage);

    private JLabel[] blastRadiusLabels = new JLabel[GameSettingsView.playerCount];
    private JLabel[] bombUpLabels = new JLabel[GameSettingsView.playerCount];
    private JLabel[] rollerBladeLabels = new JLabel[GameSettingsView.playerCount];
    private JLabel[] invincibilityLabels = new JLabel[GameSettingsView.playerCount];
    private JLabel[] ghostLabels = new JLabel[GameSettingsView.playerCount];
    private JLabel[] detonatorLabels = new JLabel[GameSettingsView.playerCount];
    private JLabel[] barricadeLabels = new JLabel[GameSettingsView.playerCount];

    private Timer timer;
    private Game game;
    private Map map;
    private MapG gameArea;
    private final JFrame parent;
    private Control ctr;

    /**
     * Constructor for the GameView class.
     * Initializes the game view with the given parent JFrame, map index, and rounds to win.
     *
     * @param parent      the parent JFrame
     * @param mapIndex    the index of the map to be loaded
     * @param roundsToWin the number of rounds to win the game
     */

    public GameView(JFrame parent, int mapIndex, int roundsToWin) {
        this.parent = parent;

        if (GameSettingsView.playerCount == 3) {
            names = GlobalSettingsView.getPlayers();
        } else {
            names = new ArrayList<>();
            names.add(GlobalSettingsView.getPlayers().get(0));
            names.add(GlobalSettingsView.getPlayers().get(1));
        }

        MapLayout layout = MapLoader.getInstance().getLayout(mapIndex);
        this.game = new Game(layout, names, roundsToWin);
        this.gameArea = initGameArea();

        try {
            InputStream titleFontStream = getClass().getResourceAsStream("/fonts/INVASION2000.TTF");
            Font titleFont = Font.createFont(Font.TRUETYPE_FONT, titleFontStream);

            Image backgroundImage = ResourceLoader.loadImage("imgs/menuBackground.jpg");

            Image bombImage = new BombG(null).getImage();

            Image scaledBombImage = bombImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            ImageIcon bombIcon = new ImageIcon(scaledBombImage);


            panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage, 0, 0, this);
                }
            };

            ctr = new Control(map);
            parent.addKeyListener(ctr);

            players = map.getPlayers();

            scorePanel = new JPanel(new GridBagLayout());
            scorePanel.setPreferredSize(new Dimension(1110, 75));
            scorePanel.setOpaque(false);

            JLabel wins = new JLabel("WINS");
            wins.setFont(titleFont.deriveFont(Font.PLAIN, 30));
            wins.setForeground(Color.WHITE);

            scores = game.getPlayerScores();

            score.setFont(titleFont.deriveFont(Font.PLAIN, 30));
            score.setForeground(Color.WHITE);

            GridBagConstraints gbc = new GridBagConstraints();

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            scorePanel.add(wins, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.CENTER;
            scorePanel.add(score, gbc);

            scorePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, -5, 0));

            playerNames = names.toArray(new String[0]);

            for (int i = 0; i < playerNames.length; i++) {
                blastRadiusLabels[i] = new JLabel();
                bombUpLabels[i] = new JLabel();
                rollerBladeLabels[i] = new JLabel();
                invincibilityLabels[i] = new JLabel();
                ghostLabels[i] = new JLabel();
                detonatorLabels[i] = new JLabel();
                barricadeLabels[i] = new JLabel();

                blastRadiusLabels[i].setIcon(blastRadius);
                bombUpLabels[i].setIcon(bombUp);
                rollerBladeLabels[i].setIcon(rollerBlade);
                invincibilityLabels[i].setIcon(invincibility);
                ghostLabels[i].setIcon(ghost);
                detonatorLabels[i].setIcon(detonator);
                barricadeLabels[i].setIcon(barricade);
            }

            for (int i = 0; i < playerNames.length; i++) {
                playerPanels[i] = new JPanel(new BorderLayout());
                playerPanels[i].setLayout(new BoxLayout(playerPanels[i], BoxLayout.Y_AXIS));

                JLabel playerLabel = new JLabel(playerNames[i]);
                playerLabel.setFont(titleFont.deriveFont(Font.PLAIN, 26));

                if (i == 2 && GameSettingsView.playerCount == 3) {
                    playerPanels[i].setPreferredSize(new Dimension(1100, 110));
                } else {
                    playerPanels[i].setPreferredSize(new Dimension(125, 600));
                }
                playerPanels[i].setBackground(Color.LIGHT_GRAY);

                bombCountLabels[i].setFont(titleFont.deriveFont(Font.PLAIN, 30));
                bombCountLabels[i].setForeground(Color.WHITE);
                bombCountLabels[i].setIcon(bombIcon);


                playerLabel.setForeground(Color.WHITE);
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.CENTER;

                if (GameSettingsView.playerCount == 3 && i == 2) {
                    JPanel playerIconPanel = new JPanel(new FlowLayout());
                    playerIconPanel.setBackground(Color.LIGHT_GRAY);
                    playerIconPanel.add(bombCountLabels[i]);
                    playerIconPanel.add(blastRadiusLabels[i]);
                    playerIconPanel.add(bombUpLabels[i]);
                    playerIconPanel.add(rollerBladeLabels[i]);
                    playerIconPanel.add(invincibilityLabels[i]);
                    playerIconPanel.add(ghostLabels[i]);
                    playerIconPanel.add(detonatorLabels[i]);
                    playerIconPanel.add(barricadeLabels[i]);

                    playerLabel.setBorder(new EmptyBorder(10, 0, 0, 0));
                    playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    playerPanels[i].add(playerLabel, BorderLayout.NORTH);
                    playerPanels[i].add(playerIconPanel, BorderLayout.SOUTH);
                } else {
                    playerPanels[i].add(playerLabel, gbc);
                    playerPanels[i].add(bombCountLabels[i], gbc);
                    playerPanels[i].add(blastRadiusLabels[i], gbc);
                    playerPanels[i].add(bombUpLabels[i], gbc);
                    playerPanels[i].add(rollerBladeLabels[i], gbc);
                    playerPanels[i].add(invincibilityLabels[i], gbc);
                    playerPanels[i].add(ghostLabels[i], gbc);
                    playerPanels[i].add(detonatorLabels[i], gbc);
                    playerPanels[i].add(barricadeLabels[i], gbc);


                }

                playerPanels[i].setBackground(Color.LIGHT_GRAY);

                bombCountLabels[i].setFont(titleFont.deriveFont(Font.PLAIN, 30));
                bombCountLabels[i].setForeground(Color.WHITE);
                bombCountLabels[i].setIcon(bombIcon);

                playerPanels[i].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            }

            leftPanel = playerPanels[0];
            rightPanel = playerPanels[1];
            if (GameSettingsView.playerCount == 3) {
                bottomPanel = playerPanels[2];
            }

            panel.add(scorePanel, BorderLayout.NORTH);
            panel.add(leftPanel, BorderLayout.WEST);
            panel.add(gameArea, BorderLayout.CENTER);
            panel.add(rightPanel, BorderLayout.EAST);
            if (GameSettingsView.playerCount == 3)
                panel.add(bottomPanel, BorderLayout.SOUTH);

            Action escapeAction = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int result = JOptionPane.showConfirmDialog(panel, "Do you want to return to the main menu?", "Confirm", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        returnToMainMenu();
                        timer.cancel();
                    }
                }
            };

            String key = "ESCAPE";
            KeyStroke keyStroke = KeyStroke.getKeyStroke(key);
            panel.setBorder(BorderFactory.createEmptyBorder(0, 0, -10, 0));
            panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, key);
            panel.getActionMap().put(key, escapeAction);
        } catch (Exception e) {
            e.printStackTrace();
        }
        startGame();
    }

    private void returnToMainMenu() {
        parent.getContentPane().remove(panel);
        MainView.currentInstance.resetView();
        panel = MainView.currentInstance.getPanel();
        parent.getContentPane().add(panel);
        parent.revalidate();
        parent.repaint();
    }

    /**
     * Returns the main panel of the game view.
     *
     * @return the main panel
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Updates the bomb count labels for all players.
     */
    public void updateBombCountLabels() {
        if (GameSettingsView.playerCount == 3) {
            player1BombCount.setText(map.getBombCount(1) + "/" + players.get(0).getMaxBombCount());
            player2BombCount.setText(map.getBombCount(2) + "/" + players.get(1).getMaxBombCount());
            player3BombCount.setText(map.getBombCount(3) + "/" + players.get(2).getMaxBombCount());
        } else {
            player1BombCount.setText(map.getBombCount(1) + "/" + players.get(0).getMaxBombCount());
            player2BombCount.setText(map.getBombCount(2) + "/" + players.get(1).getMaxBombCount());
        }
    }

    private MapG initGameArea() {
        game.initMap();
        this.map = game.getMap();

        MapG mapG = new MapG(map);
        mapG.setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
        mapG.setBackground(Color.RED);
        mapG.setSize(new Dimension(map.getWidth(), map.getHeight()));
        mapG.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 10));
        mapG.repaint();

        return mapG;
    }

    private void startGame() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                game.update();
                gameArea.update();
                gameArea.repaint();
                powerups.clear();
                updateBombCountLabels();
                setPowerUpAvability();
                var res = game.getRoundResult();
                res.ifPresent(gameResult -> roundOver(gameResult));
            }
        };
        timer.schedule(task, 0, game.UPDATE_INTERV);
    }

    private void roundOver(GameResult res) {
        timer.cancel();
        game.recordRoundResult(res);
        parent.removeKeyListener(ctr);

        if (!game.isGameOver()) {
            resetGameElements();

            ctr = new Control(game.getMap());
            parent.addKeyListener(ctr);

            startGame();
            return;
        }

        showGameOverPanel(parent);
    }

    private void resetGameElements() {
        panel.remove(gameArea);
        gameArea = initGameArea();
        players = map.getPlayers();
        setPanels(gameArea);

        updateBombCountLabels();
        updateScoreLabels();

        panel.revalidate();
    }

    /**
     * Sets the scores for all players.
     */
    public void updateScoreLabels() {
        if (GameSettingsView.playerCount == 3)
            score.setText(names.get(0) + ": " + scores.get(0) + "   " + names.get(1) + ": " + scores.get(1) + "   " + names.get(2) + ": " + scores.get(2));
        else
            score.setText(names.get(0) + ": " + scores.get(0) + "   " + names.get(1) + ": " + scores.get(1));
    }

    private void showGameOverPanel(JFrame frame) {
        frame.getContentPane().remove(panel);
        GameOverView gameOverView = new GameOverView(frame, names, game);
        panel = gameOverView.getPanel();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }

    private void setPanels(MapG gameArea) {
        panel.add(scorePanel, BorderLayout.NORTH);
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(gameArea, BorderLayout.CENTER);
        panel.add(rightPanel, BorderLayout.EAST);
        if (GameSettingsView.playerCount == 3)
            panel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setPowerUpAvability() {
        hidePowerUpIcons();
        for (Player player : players) {
            powerups.add(player.getPowerups());
        }
        for (int i = 0; i < powerups.size(); i++) {
            for (Powerup powerup : powerups.get(i)) {
                if (powerup instanceof demolitiondash.model.powerup.IncreaseBombRadiusPowerup) {
                    blastRadiusLabels[i].setVisible(true);
                } else if (powerup instanceof demolitiondash.model.powerup.ExtraBombPowerup) {
                    bombUpLabels[i].setVisible(true);
                } else if (powerup instanceof demolitiondash.model.powerup.SpeedIncreasePowerup) {
                    rollerBladeLabels[i].setVisible(true);
                } else if (powerup instanceof demolitiondash.model.powerup.InvincibilityPowerup) {
                    invincibilityLabels[i].setVisible(true);
                } else if (powerup instanceof demolitiondash.model.powerup.GhostPowerup) {
                    ghostLabels[i].setVisible(true);
                } else if (powerup instanceof demolitiondash.model.powerup.RemoteBombsPowerup) {
                    detonatorLabels[i].setVisible(true);
                } else if (powerup instanceof demolitiondash.model.powerup.BarricadePowerup) {
                    barricadeLabels[i].setVisible(true);
                }
            }
        }
    }

    private void hidePowerUpIcons() {
        for (int i = 0; i < playerNames.length; i++) {
            blastRadiusLabels[i].setVisible(false);
            bombUpLabels[i].setVisible(false);
            rollerBladeLabels[i].setVisible(false);
            invincibilityLabels[i].setVisible(false);
            ghostLabels[i].setVisible(false);
            detonatorLabels[i].setVisible(false);
            barricadeLabels[i].setVisible(false);
        }
    }
}