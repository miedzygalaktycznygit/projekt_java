import screens.CreateQuestinsScreenGui;
import screens.TitleScreenGui;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //wyświetlanie startowego ekranu
                //new TitleScreenGui().setVisible(true);

                new CreateQuestinsScreenGui().setVisible(true);
            }
        });
    }
}
