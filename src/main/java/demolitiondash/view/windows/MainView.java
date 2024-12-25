package demolitiondash.view.windows;
import demolitiondash.res.ResourceLoader;
import demolitiondash.view.components.GameStore;

import java.io.InputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * This class represents the main view of the game.
 * It provides methods for setting up the main view and displaying it.
 */
public class MainView{
    private final JFrame frame;
    private JPanel panel;
    public static MainView currentInstance;
    public final GameStore gameStore;

    /**
     * Sets the availability of power-ups for all players.
     */
    public MainView(){
        frame = new JFrame("Demolition Dash");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setupPanel();
        gameStore = GameStore.getInstance();

        frame.pack();
        frame.setSize(1200, 825);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().add(panel);
        frame.setFocusable(true);
        frame.requestFocus();
        frame.requestFocusInWindow();

        currentInstance = this;
    }

    /**
     * Resets the view by removing the current panel and setting up a new one.
     */
    public void resetView() {
        frame.getContentPane().remove(panel);
        setupPanel();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Sets up the panel for the main view.
     * This includes setting up the layout, fonts, buttons, and action listeners.
     */
    public void setupPanel(){
        try{

            InputStream titleFontStream = getClass().getResourceAsStream("/fonts/INVASION2000.TTF");
            Font titleFont = Font.createFont(Font.TRUETYPE_FONT, titleFontStream);
            InputStream buttonFontStream = getClass().getResourceAsStream("/fonts/Retro Gaming.ttf");
            Font buttonFont = Font.createFont(Font.TRUETYPE_FONT, buttonFontStream);

            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            GlobalSettingsView globalSettingsView = new GlobalSettingsView(frame);
            GameSettingsView gameSettingsView = new GameSettingsView(frame);

            Image backgroundImage = ResourceLoader.loadImage("imgs/menuBackground.jpg");

            panel = new JPanel(new GridBagLayout()){
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage, 0, 0, this);
                }
            };
            panel.setVisible(true);

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.insets = new Insets(3,250,3,0);
            JLabel mainTitlePart1 = new JLabel("DEMOLITION");
            mainTitlePart1.setPreferredSize(new Dimension(800, 75));
            mainTitlePart1.setFont(titleFont.deriveFont(Font.BOLD | Font.ITALIC, 80));
            mainTitlePart1.setForeground(Color.WHITE);
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            panel.add(mainTitlePart1, constraints);
            constraints.insets = new Insets(3,0,3,0);

            JLabel mainTitlePart2 = new JLabel("\tDASH");
            mainTitlePart2.setPreferredSize(new Dimension(500, 75));
            mainTitlePart2.setFont(titleFont.deriveFont(Font.BOLD | Font.ITALIC, 80));
            mainTitlePart2.setForeground(Color.WHITE);
            constraints.gridx = 1;
            constraints.gridy = 1;
            panel.add(mainTitlePart2, constraints);

            JButton playButton = new JButton("PLAY");
            playButton.setFont(buttonFont.deriveFont(Font.PLAIN, 42));

            playButton.setPreferredSize(new Dimension(300,100));
            playButton.setBackground(Color.lightGray);
            playButton.setBorderPainted(false);
            playButton.setOpaque(true);

            JButton inputSettingsButton = new JButton("SETTINGS");
            inputSettingsButton.setFont(buttonFont.deriveFont(Font.PLAIN, 40));

            inputSettingsButton.setPreferredSize(new Dimension(300,100));
            inputSettingsButton.setBackground(Color.lightGray);
            inputSettingsButton.setBorderPainted(false);
            inputSettingsButton.setOpaque(true);

            constraints.insets = new Insets(75,150,-75,0);
            constraints.gridx = -1;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
            panel.add(playButton, constraints);

            constraints.insets = new Insets(75,0,-75,0);
            constraints.gridx = 1;
            constraints.gridy = 2;
            panel.add(inputSettingsButton, constraints);


            inputSettingsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.getContentPane().remove(panel);
                    panel = globalSettingsView.getPanel();
                    frame.getContentPane().add(panel);
                    frame.revalidate();
                    frame.repaint();
                    System.out.println("Settings");
                }
            });

            playButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.getContentPane().remove(panel);
                    GameSettingsView.setPanel();
                    frame.getContentPane().add(gameSettingsView.getPanel());
                    frame.revalidate();
                    frame.repaint();
                    System.out.println("Play");
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Makes the main view visible.
     */
    public void show(){
        frame.setVisible(true);
    }

    /**
     * Returns the main panel of the main view.
     * @return the main panel
     */
    public JPanel getPanel(){
        return panel;
    }

}