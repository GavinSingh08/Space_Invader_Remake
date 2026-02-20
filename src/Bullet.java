public class Bullet {
    // Bullet's current position on the grid
    private int x, y;

    // Symbol representing the bullet on the grid
    private String symbol = "*";

    // Constructor: initializes bullet at (x, y)
    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Move the bullet up by 1 cell (typical for a shooting game)
    public void moveUp() {
        y -= 1;
    }

    // Getters for bullet position
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Private setters used internally for position updates
    private void setX(int x) {
        this.x = x;
    }

    private void setY(int y) {
        this.y = y;
    }

    // Getter for bullet symbol (used when updating the grid)
    public String getSymbol() {
        return symbol;
    }

    // Update bullet position (e.g., after reset above player)
    public void updatePos(int x, int y) {
        setX(x);
        setY(y);
    }
}