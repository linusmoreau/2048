import java.util.Arrays;

public class Grid {
    int[][] grid;
    int[][] prevgrid;
    ScoreKeeper s;
    Game game;

    public Grid(int w, int h, ScoreKeeper sk, Game game) {
        grid = new int[h][w];
        s = sk;
        this.game = game;
    }

    public int[][] getGrid() {
        return grid;
    }

    static int generate_num() {
        if (Math.random() > 0.9) {
            return 4;
        } else {
            return 2;
        }
    }

    private int empty_places() {
        int count = 0;
        int i = 0;
        while (i < grid.length) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    count += 1;
                }
            }
            i++;
        }
        return count;
    }

    private void generate() {
        int count = empty_places();
        if (count == 0) {
            return;
        }
        int choice = (int) (Math.random() * count);
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
    }

    public void begin() {
        int i = 0;
        while (i < grid.length) {
            Arrays.fill(grid[i], 0);
            i++;
        }
        generate();
        generate();
    }

    private void merge() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length - 1; j++) {
                if (grid[i][j] == grid[i][j + 1]) {
                    grid[i][j] = grid[i][j] + grid[i][j + 1];
                    grid[i][j + 1] = 0;
                    s.addScore(grid[i][j]);
                }
            }
        }
    }

    private void fall() {
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

    private void rotate() {
        int[][] ngrid = new int[grid[0].length][grid.length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                ngrid[j][grid.length - i - 1] = grid[i][j];
            }
        }
        grid = ngrid;
    }

    private void preserve() {
        prevgrid = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            prevgrid[i] = grid[i].clone();
        }
    }

    private boolean changed() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != prevgrid[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    public void doTurn(char c) {
        preserve();
        if (c == 's') {
            rotate();
        } else if (c == 'd') {
            rotate();
            rotate();
        } else if (c == 'w') {
            rotate();
            rotate();
            rotate();
        }
        fall();
        merge();
        fall();
        if (c == 's') {
            rotate();
            rotate();
            rotate();
        } else if (c == 'd') {
            rotate();
            rotate();
        } else if (c == 'w') {
            rotate();
        }
        if (changed()) {
            generate();
            game.display(grid);
        }
    }
}
