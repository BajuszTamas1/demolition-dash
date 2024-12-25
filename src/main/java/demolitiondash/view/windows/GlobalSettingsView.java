package demolitiondash.view.windows;

import demolitiondash.res.ResourceLoader;
import demolitiondash.util.player.PlayerStore;
import demolitiondash.view.control.inputmap.InputStore;
import demolitiondash.view.control.inputmap.InputStoreEntry;

import java.awt.event.*;
import java.io.InputStream;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the global settings view.
 * It provides methods for customizing player controls and saving the changes.
 */
public class GlobalSettingsView {
    private JPanel panel;
    public static GlobalSettingsView currentInstance;
    private static ArrayList<String> players = new ArrayList<>();
    private final List<JButton> inputButtons = new ArrayList<>();
    private final static List<List<String>> playerInputs = new ArrayList<>();
    private int currentPlayer = 0;

    /**
     * Constructor for the GlobalSettingsView class.
     * Initializes the global settings view with a given JFrame and sets up the panel.
     * @param frame the parent JFrame
     */
    public GlobalSettingsView(JFrame frame) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            InputStream titleFontStream = getClass().getResourceAsStream("/fonts/INVASION2000.TTF");
            Font titleFont = Font.createFont(Font.TRUETYPE_FONT, titleFontStream);
            InputStream buttonFontStream = getClass().getResourceAsStream("/fonts/Retro Gaming.ttf");
            Font buttonFont = Font.createFont(Font.TRUETYPE_FONT, buttonFontStream);

            Image backgroundImage = ResourceLoader.loadImage("imgs/menuBackground.jpg");

            InputStore inputStore = InputStore.getInstance();
            ArrayList<InputStoreEntry> inputs = inputStore.getControls();
            PlayerStore playerStore = PlayerStore.getInstance();
            players = playerStore.getPlayers();


            panel = new JPanel(new GridBagLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage, 0, 0, this);
                }
            };
            GridBagConstraints constraints = new GridBagConstraints();

            constraints.insets = new Insets(3, 3, 3, 3);
            JLabel inputSettingsTitle = new JLabel("Input Settings");
            inputSettingsTitle.setFont(titleFont.deriveFont(Font.BOLD | Font.ITALIC, 35));
            inputSettingsTitle.setForeground(Color.WHITE);
            constraints.gridx = 1;
            constraints.gridy = 0;
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            panel.add(inputSettingsTitle, constraints);

            JPanel playerPanel = new JPanel(new FlowLayout());

            JButton previousPlayer = new JButton("<-");
            previousPlayer.setFont(titleFont.deriveFont(Font.PLAIN, 35));
            previousPlayer.setForeground(Color.WHITE);
            previousPlayer.setOpaque(false);
            previousPlayer.setContentAreaFilled(false);
            previousPlayer.setBorderPainted(false);
            playerPanel.add(previousPlayer);


            for (int i = 0; i < 3; i++) {
                List<String> playerInput = new ArrayList<>();
                int j = 0;
                for (InputStoreEntry input : inputs) {
                    if (input.getPlayerNumber() == i + 1) {
                        playerInput.add(input.getKeyLabel());
                        j++;
                    }
                }
                playerInputs.add(playerInput);
            }

            JLabel playerLabel = new JLabel(players.get(currentPlayer));
            playerLabel.setFont(titleFont.deriveFont(Font.PLAIN, 35));
            playerLabel.setForeground(Color.WHITE);
            playerPanel.add(playerLabel);

            JButton nextPlayer = new JButton("->");
            nextPlayer.setFont(titleFont.deriveFont(Font.PLAIN, 35));
            nextPlayer.setForeground(Color.WHITE);
            nextPlayer.setOpaque(false);
            nextPlayer.setContentAreaFilled(false);
            nextPlayer.setBorderPainted(false);
            playerPanel.add(nextPlayer);

            playerPanel.setBackground(new Color(0, 0, 0, 0));
            playerPanel.setOpaque(false);

            constraints.gridx = 1;
            constraints.gridy = 1;
            panel.add(playerPanel, constraints);

            for (int i = 0; i < 6; i++) {
                Panel inputPanel = new Panel(new FlowLayout());

                JLabel inputLabel = new JLabel(i == 0 ? "Up" : i == 1 ? "Down" : i == 2 ? "Left" : i == 3 ? "Right" : i == 4 ? "Bomb": "Barrier");
                inputLabel.setFont(buttonFont.deriveFont(Font.PLAIN, 35));
                inputLabel.setForeground(Color.WHITE);
                constraints.gridx = 0;
                constraints.gridy = i + 2;
                constraints.anchor = GridBagConstraints.WEST;
                panel.add(inputLabel, constraints);

                JButton inputButton = new JButton(playerInputs.get(currentPlayer).get(i));
                inputButton.setPreferredSize(new Dimension(120, 60));
                inputButton.setFont(new Font("Roboto Slab", Font.PLAIN, 35));
                inputButton.setBackground(Color.lightGray);
                inputButton.setBorderPainted(false);
                inputButton.setOpaque(true);
                inputButtons.add(inputButton);
                constraints.gridx = 1;
                constraints.gridy = i + 2;
                constraints.anchor = GridBagConstraints.EAST;
                panel.add(inputButton, constraints);

                constraints.gridx = 1;
                constraints.gridy = i + 2;
                panel.add(inputPanel, constraints);
                final int index = i;
                inputButton.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        String newInput = KeyEvent.getKeyText(e.getKeyCode());
                        playerInputs.get(currentPlayer).set(index, newInput);
                        inputStore.setPlayerControl(currentPlayer + 1, e.getKeyCode(), newInput, inputs.get(index).getAction());
                        inputButton.setText(newInput);
                    }
                });
            }

            JButton saveButton = new JButton("Save");
            saveButton.setPreferredSize(new Dimension(175, 75));
            saveButton.setBackground(Color.lightGray);
            saveButton.setFont(buttonFont.deriveFont(Font.PLAIN, 42));
            saveButton.setBorderPainted(false);
            saveButton.setOpaque(true);
            constraints.gridx = 0;
            constraints.gridy = 8;
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            constraints.insets = new Insets(40, 0, 0, 200);
            constraints.anchor = GridBagConstraints.CENTER;
            panel.add(saveButton, constraints);


            JButton backButton = new JButton("Back");
            backButton.setPreferredSize(new Dimension(175, 75));
            backButton.setBackground(Color.lightGray);
            backButton.setFont(buttonFont.deriveFont(Font.PLAIN, 42));
            backButton.setBorderPainted(false);
            backButton.setOpaque(true);
            constraints.gridx = 0;
            constraints.gridy = 8;
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            constraints.insets = new Insets(40, 0, 0, -200);
            constraints.anchor = GridBagConstraints.CENTER;
            panel.add(backButton, constraints);

            JButton restoreButton = new JButton("Restore");
            restoreButton.setPreferredSize(new Dimension(250, 75));
            restoreButton.setBackground(Color.lightGray);
            restoreButton.setFont(buttonFont.deriveFont(Font.PLAIN, 40));
            restoreButton.setBorderPainted(false);
            restoreButton.setOpaque(true);
            constraints.gridx = 0;
            constraints.gridy = 9;
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            constraints.insets = new Insets(40, -275, 0, 0);
            constraints.anchor = GridBagConstraints.CENTER;
            panel.add(restoreButton, constraints);

            JButton customizeButton = new JButton("Customize");
            customizeButton.setPreferredSize(new Dimension(275, 75));
            customizeButton.setBackground(Color.lightGray);
            customizeButton.setFont(buttonFont.deriveFont(Font.PLAIN, 36));
            customizeButton.setBorderPainted(false);
            customizeButton.setOpaque(true);
            constraints.gridx = 0;
            constraints.gridy = 9;
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            constraints.insets = new Insets(40, 0, 0, -300);
            constraints.anchor = GridBagConstraints.CENTER;
            panel.add(customizeButton, constraints);


            saveButton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    playerStore.setPlayers(players);
                    inputStore.save();
                    playerStore.save();
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

            restoreButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    InputStore inputStore = InputStore.getInstance();
                    inputStore.resetToDefault();
                    ArrayList<InputStoreEntry> inputs = inputStore.getControls();
                    playerInputs.clear();
                    for (int i = 0; i < 3; i++) {
                        List<String> playerInput = new ArrayList<>();
                        int j = 0;
                        for (InputStoreEntry input : inputs) {
                            if (input.getPlayerNumber() == i + 1) {
                                playerInput.add(input.getKeyLabel());
                                j++;
                            }
                        }
                        playerInputs.add(playerInput);
                    }
                    for (int i = 0; i < 6; i++) {
                        inputButtons.get(i).setText(playerInputs.get(currentPlayer).get(i));
                    }
                    inputStore.save();
                }
            });

            customizeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.getContentPane().remove(panel);
                    CustomizeView customizeView = new CustomizeView(frame);
                    panel = customizeView.getPanel();
                    frame.getContentPane().add(panel);
                    frame.revalidate();
                    frame.repaint();
                }
            });


            previousPlayer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentPlayer = (currentPlayer - 1 + players.size()) % players.size();
                    playerLabel.setText(players.get(currentPlayer));
                    for (int i = 0; i < 6; i++) {
                        inputButtons.get(i).setText(playerInputs.get(currentPlayer).get(i));
                    }
                }
            });

            nextPlayer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentPlayer = (currentPlayer + 1) % players.size();
                    playerLabel.setText(players.get(currentPlayer));
                    for (int i = 0; i < 6; i++) {
                        inputButtons.get(i).setText(playerInputs.get(currentPlayer).get(i));
                    }
                }
            });

            playerLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    String newName = JOptionPane.showInputDialog("Enter new name");
                    if (newName != null && !newName.trim().isEmpty()) {
                        players.set(currentPlayer, newName);
                        playerLabel.setText(newName);
                    }
                }
            });
            currentInstance = this;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main panel of the global settings view.
     * @return the main panel
     */
    public JPanel getPanel(){
        return panel;
    }

    /**
     * Returns the players of the global settings view.
     * @return the players
     */
    public static List<String> getPlayers(){
        return List.copyOf(players);
    }
}