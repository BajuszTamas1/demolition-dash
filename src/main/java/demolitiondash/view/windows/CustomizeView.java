package demolitiondash.view.windows;

import demolitiondash.res.ResourceLoader;
import demolitiondash.util.player.PlayerStore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * This class represents the customization view.
 * It provides methods for customizing player colors and saving the changes.
 */
public class CustomizeView {

    private JPanel panel;
    private CustomizeView currentInstance;
    private ArrayList<String> players = new ArrayList<>();
    private ArrayList<Double> playerHues = new ArrayList<>();
    private int currentPlayer = 0;
    private Color color;
    private InputStream titleFontStream = getClass().getResourceAsStream("/fonts/INVASION2000.TTF");
    private InputStream buttonFontStream = getClass().getResourceAsStream("/fonts/Retro Gaming.ttf");
    private Image backgroundImage = ResourceLoader.loadImage("imgs/menuBackground.jpg");

    /**
     * Constructor for the CustomizeView class.
     * Initializes the customization view with a given JFrame and sets up the panel.
     * @param frame the parent JFrame
     */
    public CustomizeView(JFrame frame){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            Font titleFont = Font.createFont(Font.TRUETYPE_FONT, titleFontStream);
            Font buttonFont = Font.createFont(Font.TRUETYPE_FONT, buttonFontStream);

            PlayerStore playerStore = PlayerStore.getInstance();
            playerHues = playerStore.getPlayerHues();
            players = playerStore.getPlayers();

            panel = new JPanel(new GridBagLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage, 0, 0, this);
                }
            };

            playerHues = playerStore.getPlayerHues();


            GridBagConstraints gbc = new GridBagConstraints();

            JLabel CustomizeLabel = new JLabel("Customize your player");
            CustomizeLabel.setFont(titleFont.deriveFont(Font.BOLD | Font.ITALIC, 35));
            CustomizeLabel.setForeground(Color.WHITE);

            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            panel.add(CustomizeLabel, gbc);

            JPanel playerPanel = new JPanel(new GridBagLayout());

            JButton previousPlayer = new JButton("<-");
            previousPlayer.setFont(titleFont.deriveFont(Font.PLAIN, 35));
            previousPlayer.setForeground(Color.WHITE);
            previousPlayer.setOpaque(false);
            previousPlayer.setContentAreaFilled(false);
            previousPlayer.setBorderPainted(false);
            playerPanel.add(previousPlayer);


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
            playerPanel.setOpaque(false);

            gbc.gridx = 1;
            gbc.gridy = 1;
            panel.add(playerPanel, gbc);

            JLabel skinLabel = new JLabel("Player color");
            skinLabel.setFont(titleFont.deriveFont(Font.BOLD | Font.ITALIC, 35));
            skinLabel.setForeground(Color.WHITE);

            gbc.gridx = 1;
            gbc.gridy = 2;
            gbc.insets = new Insets(50, 0, 0, 0);
            panel.add(skinLabel, gbc);

            gbc.insets = new Insets(0, 0, 0, 0);

            JSlider playerColorSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
            playerColorSlider.setMajorTickSpacing(10);
            playerColorSlider.setMinorTickSpacing(2);
            playerColorSlider.setPaintTicks(true);
            playerColorSlider.setPaintLabels(true);
            playerColorSlider.setForeground(Color.WHITE);
            playerColorSlider.setPreferredSize(new Dimension(300, 50));
            playerColorSlider.setValue((int) (playerHues.get(currentPlayer) * 100));
            playerColorSlider.addChangeListener(e -> {
                playerHues.set(currentPlayer, playerColorSlider.getValue() / 100.0);
            });

            gbc.gridx = 1;
            gbc.gridy = 3;
            panel.add(playerColorSlider, gbc);

            JPanel skinPanel = new JPanel();
            skinPanel.setPreferredSize(new Dimension(100,100));
            color = Color.getHSBColor(playerHues.get(currentPlayer).floatValue(), 1.0f, 1.0f);
            skinPanel.setBackground(color);

            gbc.gridx = 1;
            gbc.gridy = 4;
            panel.add(skinPanel, gbc);

            JButton saveButton = new JButton("Save");
            saveButton.setFont(buttonFont.deriveFont(Font.PLAIN, 40));
            saveButton.setPreferredSize(new Dimension(175,75));
            saveButton.setBackground(Color.lightGray);
            saveButton.setBorderPainted(false);
            saveButton.setOpaque(true);
            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.insets = new Insets(75, -200, 0, 0);
            panel.add(saveButton, gbc);

            JButton backButton = new JButton("Back");
            backButton.setFont(buttonFont.deriveFont(Font.PLAIN, 40));
            backButton.setPreferredSize(new Dimension(175,75));
            backButton.setBackground(Color.lightGray);
            backButton.setBorderPainted(false);
            backButton.setOpaque(true);
            gbc.gridx = 2;
            gbc.gridy = 5;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.insets = new Insets(75, 0, 0, -200);
            panel.add(backButton, gbc);


            previousPlayer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentPlayer = (currentPlayer - 1 + players.size()) % players.size();
                    playerLabel.setText(players.get(currentPlayer));
                    playerColorSlider.setValue((int) (playerHues.get(currentPlayer) * 100));
                }
            });

            nextPlayer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentPlayer = (currentPlayer + 1) % players.size();
                    playerLabel.setText(players.get(currentPlayer));
                    for (int i = 0; i < 6; i++) {
                        playerColorSlider.setValue((int) (playerHues.get(currentPlayer) * 100));
                    }
                }
            });

            playerColorSlider.addChangeListener(e -> {
                playerHues.set(currentPlayer, (playerColorSlider.getValue() + 295) / 100.0);
                color = Color.getHSBColor(playerHues.get(currentPlayer).floatValue(), 1.0f, 1.0f);
                skinPanel.setBackground(color);
            });


            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.getContentPane().remove(panel);
                    GlobalSettingsView globalSettingsView = new GlobalSettingsView(frame);
                    panel = globalSettingsView.getPanel();
                    frame.getContentPane().add(panel);
                    frame.revalidate();
                    frame.repaint();
                }
            });

            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    playerStore.setPlayerHues(playerHues);
                    playerStore.save();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main panel of the customization view.
     * @return the main panel
     */
    public JPanel getPanel(){
    return panel;
}
}

