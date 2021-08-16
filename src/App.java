import java.util.Scanner;

public class App {
    static void display(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j]);
                System.out.print(' ');
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

    public static void main(String[] args) {
        int width, height;
        int [][] grid;
        String input;
        Scanner scanner;
        width = 4;
        height = 4;
        grid = new int[height][width];
        scanner = new Scanner(System.in);
        generate(grid);
        while (generate(grid)) {
            display(grid);
            input = (String)scanner.nextLine();
            if (input.equals("quit")) {
                break;
            } else {
                while (! input.equals("w") && ! input.equals("a") && ! input.equals("s") && ! input.equals("d")) {
                    input = scanner.nextLine();
                }
                int [][] tgrid;
                if (input.equals("a")) {
                    tgrid = grid;
                } else if (input.equals("s")) {
                    tgrid = rotate(grid);
                } else if (input.equals("d")) {
                    tgrid = rotate(rotate(grid));
                } else {
                    tgrid = rotate(rotate(rotate(grid)));
                }
                fall(tgrid);
                merge(tgrid);
                fall(tgrid);
                if (input.equals("a")) {
                    grid = tgrid;
                } else if (input.equals("s")) {
                    grid = rotate(rotate(rotate(tgrid)));
                } else if (input.equals("d")) {
                    grid = rotate(rotate(tgrid));
                } else {
                    grid = rotate(tgrid);
                }
            }
            System.out.println();
        }
        System.out.println("Game Over");
        scanner.close();
    }
}