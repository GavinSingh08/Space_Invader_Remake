public class Enemy {
    // Enemy's position on the grid
    private int x, y;

    // Static counter to track total number of alive enemies
    private static int totalEnemies = 0;

    // Symbol used to represent the enemy on the grid
    private String symbol = "⧫";

    // Whether this enemy is alive
    private boolean alive = true;

    // Constructor: initialize enemy at (x, y) and increment totalEnemies
    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        totalEnemies++;
    }

    // Check if a coordinate collides with this enemy
    // Typically used to detect if a bullet hits the enemy
    public boolean collide(int dotX, int dotY) {
        return alive && dotX == x && dotY == y;
    }

    // Destroy the enemy (called when hit by bullet)
    // Decrements totalEnemies and sets alive to false
    public void destroy() {
        totalEnemies -= 1;
        alive = false;
    }

    // Reset the enemy to alive (used when starting a new wave/level)
    public void resetEnemy() {
        alive = true;
        totalEnemies++;
    }

    // Check if enemy is alive
    public boolean isAlive() {
        return alive;
    }

    // Move enemy down by 1 unit (called every few ticks for level-up)
    public void levelUp() {
        setY(y + 1);
    }

    // Reset enemy's Y position to a specific value (used when level completes)
    public void levelComplete(int y) {
        setY(y);
    }

    // Getters for position, symbol, and total enemies
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTotalEnemies() {
        return totalEnemies;
    }

    public String getSymbol() {
        return symbol;
    }

    // Private setter for Y-coordinate (used internally by levelUp / levelComplete)
    private void setY(int y) {
        this.y = y;
    }
}