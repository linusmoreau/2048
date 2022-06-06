import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Game extends Menu {
    private static final int UNIT = 128;

    private int w;
    private int h;
    private ScoreKeeper s;
    private Tile[][] tiles;
    private JLabel scoreLabel, bestscoreLabel;
    private Grid grid;
    private JPanel gridPanel;
    private final JPanel sidePanel;

    public Game(int w, int h) {
        this.w = w;
        this.h = h;
        s = new ScoreKeeper();

        setTitle("2048");
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        grid = new Grid(w, h, s, this);
        grid.begin();
        gridPanel = makeGridPanel();
        add(gridPanel);
        sidePanel = makeSidePanel();
        add(sidePanel);
        display(grid.getGrid());

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
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel makeGridPanel() {
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(h, w));
        tiles = new Tile[h][w];
        Tile tile;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                tile = new Tile(UNIT);
                tiles[i][j] = tile;
                gridPanel.add(tile);
            }
        }
        return gridPanel;
    }

    private JPanel makeSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setPreferredSize(new Dimension(UNIT, h * UNIT));

        sidePanel.add(Box.createRigidArea(new Dimension(UNIT, 0)));

        JLabel lst = new JLabel("Score\n", JLabel.CENTER);
        scoreLabel = new JLabel(String.valueOf(s.getScore()), JLabel.CENTER);
        sidePanel.add(Box.createVerticalGlue());
        addToBox(sidePanel, lst);
        addToBox(sidePanel, scoreLabel);

        JLabel lbst = new JLabel("Best Score", JLabel.CENTER);
        bestscoreLabel = new JLabel(String.valueOf(s.getBestScore()), JLabel.CENTER);
        sidePanel.add(Box.createVerticalGlue());
        addToBox(sidePanel, lbst);
        addToBox(sidePanel, bestscoreLabel);

        JButton gb = makeNewGameButton();
        gb.setFocusable(false);

        JButton rb = new JButton("Restart");
        rb.addActionListener(e -> restart());
        rb.setFocusable(false);

        JButton sb = new JButton("Save");
        sb.addActionListener(e -> save(grid.getGrid()));
        sb.setFocusable(false);

        JButton lb = new JButton("Load");
        lb.addActionListener(e -> gameLoad());
        lb.setFocusable(false);

        sidePanel.add(Box.createVerticalGlue());
        addToBox(sidePanel, gb);
        sidePanel.add(Box.createVerticalGlue());
        addToBox(sidePanel, rb);
        sidePanel.add(Box.createVerticalGlue());
        addToBox(sidePanel, sb);
        sidePanel.add(Box.createVerticalGlue());
        addToBox(sidePanel, lb);
        sidePanel.add(Box.createVerticalGlue());

        return sidePanel;
    }

    private void addToBox(JPanel box, JComponent c) {
        box.add(Box.createRigidArea(new Dimension(0, 4)));
        c.setAlignmentX(Component.CENTER_ALIGNMENT);
        box.add(c);
    }

    private void restart() {
        s = new ScoreKeeper();
        grid = new Grid(w, h, s, this);
        grid.begin();
        display(grid.getGrid());
    }

    private void reloadGUI() {
        remove(gridPanel);
        remove(sidePanel);
        gridPanel = makeGridPanel();
        add(gridPanel);
        add(sidePanel);
        pack();
        setLocationRelativeTo(null);
    }

    public void setScores(int score, int bestscore) {
        s.setScore(score);
        s.setBestScore(bestscore);
    }

    public void setGrid(int w, int h, int[][] g) {
        grid = new Grid(w, h, s, this);
        this.w = w;
        this.h = h;
        grid.setGrid(g);
        reloadGUI();
        display(grid.getGrid());
    }

    private void gameLoad() {
        int[][] data = load();
        setScores(data[0][0], data[0][1]);
        setGrid(data[1][0], data[1][1], getGrid(data[1][0], data[1][1], data));
    }

    private void save(int[][] grid) {
        try {
            File file = new File("save.txt");
            if (file.createNewFile()) { // file didn't exist and has been created
                System.out.println("New save file created");
            } else {                    // file already exists
                System.out.println("Save file overwritten");
            }
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
        scoreLabel.setText(String.valueOf(s.getScore()));
        bestscoreLabel.setText(String.valueOf(s.getBestScore()));
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                tiles[i][j].setValue(grid[i][j]);
            }
        }
    }
}
