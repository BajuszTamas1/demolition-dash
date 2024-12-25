package demolitiondash;

import demolitiondash.view.windows.MainView;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView();
            mainView.show();
        });
    }
}