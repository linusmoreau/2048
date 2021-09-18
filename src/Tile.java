import java.awt.*;

public class Tile {
    static int fontSize = 24;
    static Font font = new Font("TimesRoman", Font.PLAIN, fontSize);
    Label label;
    Rectangle rect;

    public Tile(int x, int y, int w, int h) {
        label = new Label();
        label.setFont(font);
        label.setBounds(x, y, w, h);
        label.setAlignment(Label.CENTER);
        rect = new Rectangle(x, y, w, h);

    }

    public void setText(String text) {
        label.setText(text);
    }

    public void paint(Graphics g) {
        g.fillRect(50, 50, 50, 50);
        g.setColor(Color.BLUE);
    }
}
