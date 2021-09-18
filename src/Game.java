import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Game extends Frame {
    static final int unit = 150;

    int w;
    int h;
    ScoreKeeper s;
    Tile[][] tiles;
    Label scoreLabel, bestscoreLabel;
    Grid grid;

    public Game(int w, int h) {
        this.w = w;
        this.h = h;
        s = new ScoreKeeper();
        this.setTitle("2048");

        tiles = new Tile[h][w];
        Tile tile;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                tile = new Tile(j * unit, i * unit, unit, unit);
                add(tile.label);
                tiles[i][j] = tile;
            }
        }

        Label lst = new Label("Score");
        lst.setBounds(w * unit, unit / 4, unit, unit / 3);
        lst.setAlignment(Label.CENTER);
        add(lst);

        Label lbst = new Label("Best Score");
        lbst.setBounds(w * unit, unit * 3 / 4, unit, unit / 3);
        lbst.setAlignment(Label.CENTER);
        add(lbst);

        scoreLabel = new Label(String.valueOf(s.getScore()));
        scoreLabel.setBounds(w * unit, unit / 2, unit, unit / 3);
        scoreLabel.setAlignment(Label.CENTER);
        add(scoreLabel);

        bestscoreLabel = new Label(String.valueOf(s.getBestScore()));
        bestscoreLabel.setBounds(w * unit, unit, unit, unit / 3);
        bestscoreLabel.setAlignment(Label.CENTER);
        add(bestscoreLabel);

        Button rb = new Button("Restart");
        rb.setBounds(w * unit + unit / 8, unit * 3 / 2, unit * 3 / 4, unit / 3);
        rb.addActionListener(e -> restart());
        rb.setFocusable(false);
        add(rb);

        Button sb = new Button("Save");
        sb.setBounds(w * unit + unit / 8, unit * 2, unit * 3 / 4, unit / 3);
        sb.addActionListener(e -> save(grid.getGrid()));
        sb.setFocusable(false);
        add(sb);

        Button lb = new Button("Load");
        lb.setBounds(w * unit + unit / 8, unit * 5 / 2, unit * 3 / 4, unit / 3);
        lb.addActionListener(e -> load());
        lb.setFocusable(false);
        add(lb);

        load();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                save(grid.getGrid());
                System.exit(0);
            }
        });
        addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                char c = e.getKeyChar();
                int k = e.getKeyCode();
                if (k == 37) {
                    c = 'a';
                } else if (k == 38) {
                    c = 'w';
                } else if (k == 39) {
                    c = 'd';
                } else if (k == 40) {
                    c = 's';
                }
                if (c == 'w' || c == 'a' || c == 's' || c == 'd') {
                    grid.doTurn(c);
                }
            }

            public void keyReleased(KeyEvent e) {
            }
        });
        setSize((w + 1) * unit, h * unit);
        setLayout(null);
        setVisible(true);
    }

    private void restart() {
        s = new ScoreKeeper();
        grid = new Grid(w, h, s, this);
        grid.begin();
        display(grid.getGrid());
    }

    private void load() {
        try {
            File file = new File("save.txt");
            Scanner scanner = new Scanner(file);
            String sline;
            String[] split;
            if (scanner.hasNextLine()) {
                sline = scanner.nextLine();
                split = sline.split(",");
                s.setScore(Integer.parseInt(split[0]));
                s.setBestScore(Integer.parseInt(split[1]));
            }
            if (scanner.hasNextLine()) {
                sline = scanner.nextLine();
                split = sline.split(",");
                w = Integer.parseInt(split[0]);
                h = Integer.parseInt(split[1]);
                int[][] g = new int[h][w];
                for (int i = 0; i < h; i++) {
                    sline = scanner.nextLine();
                    split = sline.split(",");
                    for (int j = 0; j < w; j++) {
                        g[i][j] = Integer.parseInt(split[j]);
                    }
                }
                grid = new Grid(w, h, s, this);
                grid.setGrid(g);
            }
            scanner.close();
        } catch (FileNotFoundException ignored) {
            grid = new Grid(w, h, s, this);
            grid.begin();
        }
        display(grid.getGrid());
    }

    private void save(int[][] grid) {
        try {
            File file = new File("save.txt");
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter writer = new FileWriter("save.txt");
            writer.write(s.getScore() + "," + s.getBestScore() + "\n");
            writer.write(w + "," + h + "\n");
            for (int[] ints : grid) {
                for (int j = 0; j < ints.length; j++) {
                    if (j > 0) {
                        writer.write(",");
                    }
                    writer.write(Integer.toString(ints[j]));
                }
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void display(int[][] grid) {
        StringBuilder text;
        scoreLabel.setText(String.valueOf(s.getScore()));
        bestscoreLabel.setText(String.valueOf(s.getBestScore()));
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                text = new StringBuilder(String.valueOf(grid[i][j]));
                if (grid[i][j] == 0) {
                    tiles[i][j].setText("");
                } else {
                    tiles[i][j].setText(text.toString());
                }
            }
        }
    }
}
