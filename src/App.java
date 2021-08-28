import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;

public class App {
    static int score, bestscore, width, height;
    static int [][] grid, tgrid;
    static Frame f;
    static Label[][] labels;
    static Label ls, lbs;
    static int unit = 100;

    static void display(int[][] grid) {
        String text;
        ls.setText(String.valueOf(score));
        lbs.setText(String.valueOf(bestscore));
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                text = String.valueOf(grid[i][j]);
                if (grid[i][j] == 0) {
                    labels[i][j].setText("");
                } else {
                    labels[i][j].setText(text);
                }
                for (int k = text.length(); k < 8; k++) {
                    text += " ";
                }
            }
        }
    }

    static int generate_num() {
        if (Math.random() > 0.9) {
            return 4;
        }
        else {
            return 2;
        }
    }

    static boolean generate(int[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) { 
                    count += 1;
                }
            }
        }
        if (count == 0) {
            return false;
        }
        int choice = (int)(Math.random() * count);
        count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    if (count == choice) {
                        grid[i][j] = generate_num();
                        count += 1;
                        break;
                    }
                    count += 1;
                }
            }
            if (count > choice) {
                break;
            }
        }
        return true;
    }

    static void merge(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length - 1; j++) {
                if (grid[i][j] == grid[i][j + 1]) {
                    grid[i][j] = grid[i][j] + grid[i][j + 1];
                    grid[i][j + 1] = 0;
                    score += grid[i][j];
                }
            }
        }
        if (score > bestscore) {
            bestscore = score;
        }
    }

    static void fall(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 1; j < grid[i].length; j++) {
                for (int k = 0; k < j; k++) {
                    if (grid[i][k] == 0) {
                        grid[i][k] = grid[i][j];
                        grid[i][j] = 0;
                    }
                }
            }
        }
    }

    static int[][] rotate(int[][] grid) {
        int[][] ngrid = new int[grid[0].length][grid.length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                ngrid[j][grid.length - i - 1] = grid[i][j];
            }
        }
        return ngrid;
    }

    static int[][] clone(int[][] grid) {
        int[][] ngrid = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            ngrid[i] = grid[i].clone();
        }
        return ngrid;
    }

    static boolean isdifferent(int[][] grid1, int[][]grid2) {
        for (int i = 0; i < grid1.length; i++) {
            for (int j = 0; j < grid1[i].length; j++) {
                if (grid1[i][j] != grid2[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    static void save(int[][] grid) {
        try {
            File file = new File("save.txt");
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter writer = new FileWriter("save.txt");
            writer.write(String.valueOf(score) + "," +  String.valueOf(bestscore) + "\n");
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (j > 0) {
                        writer.write(",");
                    }
                    writer.write(Integer.toString(grid[i][j]));
                }
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static int[][] load(int[][] grid) {
        try {
            File file = new File("save.txt");
            Scanner scanner = new Scanner(file);
            String sline;
            String split[];
            if (scanner.hasNextLine()) {
                sline = scanner.nextLine();
                split = sline.split(",");
                score = Integer.parseInt(split[0]);
                bestscore = Integer.parseInt(split[1]);
            }
            int i = 0;
            while (scanner.hasNextLine()) {
                sline = scanner.nextLine();
                split = sline.split(",");
                if (i == 0) {
                    grid = new int[split.length][split.length];
                }
                for (int j = 0; j < split.length; j++) {
                    grid[i][j] = Integer.parseInt(split[j]);
                }
                i += 1;
                if (i > split.length) {
                    break;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
        }
        display(grid);
        return grid;
    }

    static void restart() {
        grid = new int[height][width];
        tgrid = new int[height][width];
        generate(grid);
        generate(grid);
        score = 0;
        display(grid);
    }

    static void doTurn(char c) {
        if (c == 'a') {
            tgrid = clone(grid);
        } else if (c == 's') {
            tgrid = rotate(grid);
        } else if (c == 'd') {
            tgrid = rotate(rotate(grid));
        } else if (c == 'w') {
            tgrid = rotate(rotate(rotate(grid)));
        }
        fall(tgrid);
        merge(tgrid);
        fall(tgrid);
        if (c == 's') {
            tgrid = rotate(rotate(rotate(tgrid)));
        } else if (c == 'd') {
            tgrid = rotate(rotate(tgrid));
        } else if (c == 'w') {
            tgrid = rotate(tgrid);
        } else {
            ;
        }
        if (isdifferent(grid, tgrid)) {
            grid = tgrid;
            generate(grid);
            display(grid);
        }
    }

    static void initialize() {
        f = new Frame("2048");
        width = 4;
        height = 4;
        score = 0;
        bestscore = 0;
        grid = new int[height][width];
        labels = new Label[height][width];
        Label label;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                label = new Label();
                label.setBounds(unit / 2 + j * unit, unit / 4 + i * unit, unit / 2, unit / 2);
                f.add(label);
                labels[i][j] = label;
            }
        }

        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                save(grid);
                System.exit(0);
            }
         });
        f.addKeyListener(new KeyListener() {
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
                    doTurn(c);
                }
            }

            public void keyReleased(KeyEvent e) {
            }
        });
        Label lst = new Label("Score");
        lst.setBounds(width * unit, unit / 4, unit, unit / 3);
        lst.setAlignment(1);
        f.add(lst);

        Label lbst = new Label("Best Score");
        lbst.setBounds(width * unit, unit * 3 / 4, unit, unit / 3);
        lbst.setAlignment(1);
        f.add(lbst);

        ls = new Label(String.valueOf(score));
        ls.setBounds(width * unit, unit / 2, unit, unit / 3);
        ls.setAlignment(1);
        f.add(ls);

        lbs = new Label(String.valueOf(bestscore));
        lbs.setBounds(width * unit, unit, unit, unit / 3);
        lbs.setAlignment(1);
        f.add(lbs);

        Button rb = new Button("Restart");
        rb.setBounds(width * unit + unit / 8, unit * 3 / 2, unit * 3 / 4, unit / 3);
        rb.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                restart();
            }
        });
        rb.setFocusable(false);
        f.add(rb);

        Button sb = new Button("Save");
        sb.setBounds(width * unit + unit / 8, unit * 2, unit * 3 / 4, unit / 3);
        sb.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                save(grid);
            }
        });
        sb.setFocusable(false);
        f.add(sb);

        Button lb = new Button("Load");
        lb.setBounds(width * unit + unit / 8, unit * 5 / 2, unit * 3 / 4, unit / 3);
        lb.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                grid = load(grid);
            }
        });
        lb.setFocusable(false);
        f.add(lb);

        grid = load(grid);
        generate(grid);
        generate(grid);
        display(grid);

        f.setSize((width + 1) * unit, height * unit);
        f.setLayout(null);
        f.setVisible(true);
    }

    public static void main(String[] a) {
        initialize();
     }
}