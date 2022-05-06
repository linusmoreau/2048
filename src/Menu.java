import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Menu extends JFrame {

    public Menu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JButton gameButton = new JButton("New Game");
        gameButton.addActionListener(e -> makeGame());
        gameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(gameButton);
        JButton loadButton = new JButton("Load Save");
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
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

    private void makeGame() {
        JOptionDimension option = new JOptionDimension();
        if (option.isConfirmed()) {
            new Game(option.getGWidth(), option.getGHeight());
            dispose();
        }
    }
}
