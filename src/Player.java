public class Player {
    // Player's current position on the grid
    private int x, y;

    // Symbol representing the player on the grid
    private String symbol = "Δ";

    // Constructor: initialize player at (x, y)
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Move the player one cell to the left
    public void moveLeft() {
        this.x -= 1;
    }

    // Move the player one cell to the right
    public void moveRight() {
        this.x += 1;
    }

    // Getters for player position
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Getter for player symbol (used when updating the grid)
    public String getSymbol() {
        return symbol;
    }
}