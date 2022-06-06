import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class Menu extends JFrame {

    protected JButton makeNewGameButton() {
        JButton gameButton = new JButton("New Game");
        gameButton.addActionListener(e -> makeGame());
        return gameButton;
    }

    protected void makeGame() {
        JOptionDimension option = new JOptionDimension();
        if (option.isConfirmed()) {
            new Game(option.getGWidth(), option.getGHeight());
            dispose();
        }
    }

    protected void loadGame() {
        int[][] data = load();
        int score = data[0][0];
        int bestscore = data[0][1];
        int w = data[1][0];
        int h = data[1][1];
        Game game = new Game(w, h);
        game.setScores(score, bestscore);
        game.setGrid(w, h, getGrid(w, h, data));

        dispose();
    }

    protected int[][] getGrid(int w, int h, int[][] data) {
        int[][] g = new int[h][w];
        for (int i = 0; i < h; i++) {
            System.arraycopy(data[i + 2], 0, g[i], 0, w);
        }
        return g;
    }

    protected int[][] load() {
        try {
            File file = new File("save.txt");
            Scanner scanner = new Scanner(file);
            String[] split;
            String sline = scanner.nextLine();
            String dline = scanner.nextLine();
            split = dline.split(",");
            int w = Integer.parseInt(split[0]);
            int h = Integer.parseInt(split[1]);
            int[][] g = new int[h + 2][w];
            g[1][0] = w;
            g[1][1] = h;
            split = sline.split(",");
            g[0][0] = Integer.parseInt(split[0]);
            g[0][1] = Integer.parseInt(split[1]);
            for (int i = 2; i < h + 2; i++) {
                sline = scanner.nextLine();
                split = sline.split(",");
                for (int j = 0; j < w; j++) {
                    g[i][j] = Integer.parseInt(split[j]);
                }
            }
            scanner.close();
            return g;
        } catch (FileNotFoundException ignored) {
            return null;
        }
    }
}
