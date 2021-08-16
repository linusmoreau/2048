import java.util.Scanner;

public class App {
    static void display(int[][] grid) {
        String text;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                text = String.valueOf(grid[i][j]);
                for (int k = text.length(); k < 8; k++) {
                    text += " ";
                }
                System.out.print(text);
            }
            System.out.println();
        }
        System.out.println();
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
                }
            }
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

    public static void main(String[] args) {
        int width, height;
        int [][] grid, tgrid;
        String input;
        Scanner scanner;
        boolean end, moved;
        width = 4;
        height = 4;
        grid = new int[height][width];
        scanner = new Scanner(System.in);
        generate(grid);
        end = false;
        while (generate(grid)) {
            display(grid);
            moved = false;
            while (!moved) {
                input = scanner.nextLine();
                if (input.equals("quit")) {
                    end = true;
                    break;
                } else if (input.equals("a")) {
                    tgrid = clone(grid);
                } else if (input.equals("s")) {
                    tgrid = rotate(grid);
                } else if (input.equals("d")) {
                    tgrid = rotate(rotate(grid));
                } else if (input.equals("w")) {
                    tgrid = rotate(rotate(rotate(grid)));
                } else {
                    continue;
                }
                fall(tgrid);
                merge(tgrid);
                fall(tgrid);
                if (input.equals("s")) {
                    tgrid = rotate(rotate(rotate(tgrid)));
                } else if (input.equals("d")) {
                    tgrid = rotate(rotate(tgrid));
                } else if (input.equals("w")) {
                    tgrid = rotate(tgrid);
                } else {
                    ;
                }
                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid[i].length; j++) {
                        if (grid[i][j] != tgrid[i][j]) {
                            moved = true;
                            grid = tgrid;
                            break;
                        }
                    }
                    if (moved) {
                        break;
                    }
                }
            }
            if (end) {
                break;
            }
            System.out.println();
        }
        System.out.println("Game Over");
        scanner.close();
    }
}