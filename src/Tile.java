import javax.swing.*;
import java.awt.*;

public class Tile extends JPanel {
    private static final int fontSize = 24;
    private static final Font font = new Font("TimesRoman", Font.PLAIN, fontSize);

    private static final Color[] colors = {
            Color.LIGHT_GRAY,
            Color.WHITE,
            new Color(250, 240, 200),
            new Color(255, 191, 102),
            new Color(255, 170, 51),
            new Color(255, 100, 100),
            new Color(255, 40, 40),
            new Color(255, 221, 51),
            new Color(255, 219, 38),
            new Color(255, 217, 25),
            new Color(255, 215, 13),
            new Color(255, 213, 0),
            Color.DARK_GRAY
    };

    JLabel label;
    int value;

    public Tile() {
        setLayout(new BorderLayout());
        label = new JLabel("", SwingConstants.CENTER);
        label.setFont(font);
        add(label, BorderLayout.CENTER);

        setValue(0);
    }

    public void setValue(int v) {
        value = v;
        if (v == 0) {
            setText("");
        } else {
            setText(String.valueOf(v));
        }
        setColour();
    }

    private void setText(String text) {
        label.setText(text);
    }

    private void setColour() {
        Color color = getColor(value);
        setBackground(color);
        if ((color.getRed() + color.getGreen() + color.getBlue()) / 3 < 255 / 2) {
            label.setForeground(Color.WHITE);
        } else {
            label.setForeground(Color.BLACK);
        }
    }

    private Color getColor(int v) {
        if (v == 0) {
            return colors[0];
        }
        int i = (int) (Math.log10(v) / Math.log10(2));
        if (i >= colors.length || i < 0) {
            return colors[colors.length - 1];
        } else {
            return colors[i];
        }
    }
}
