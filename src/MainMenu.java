import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainMenu extends Menu {

    public MainMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JButton gameButton = makeNewGameButton();
        gameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(gameButton);
        JButton loadButton = new JButton("Load Save");
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton.addActionListener(e -> loadGame());
        panel.add(loadButton);

        add(panel);
        pack();
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setVisible(true);

    }
}
