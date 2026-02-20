public class Grid {
    // Dimensions of the grid
    private int height, width;

    // 2D array representing the grid's cells
    private String[][] gridMap;

    // Constructor: initializes the grid with given height and width
    public Grid(int height, int width) {
        this.height = height;
        this.width = width;
        this.gridMap = new String[height][width]; // allocate 2D array
        setGrid(); // fill grid with default empty symbol
    }

    // Fill the entire grid with default empty symbol
    public void setGrid() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                gridMap[y][x] = " . "; // empty cell representation
            }
        }
    }

    // Print the grid to the console
    public void drawGrid() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(gridMap[y][x]);
            }
            System.out.println(); // new line after each row
        }
    }

    // Update a specific cell in the grid with a symbol (player, enemy, bullet, etc.)
    public void updateGrid(int x, int y, String symbol) {
        gridMap[y][x] = " " + symbol + " ";
    }
}