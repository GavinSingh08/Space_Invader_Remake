import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    // Shared variable to track user input ('a' for left, 'd' for right, etc.)
    volatile static char input = ' ';

    public static void main(String[] args) {
        // Initialize the game grid
        Grid grid = new Grid(9, 9);

        // Initialize player starting in the middle-bottom of the grid
        Player player = new Player(4, 8);

        // Initialize a bullet starting above the player
        Bullet b1 = new Bullet(player.getX(), player.getY() - 1);

        // Initialize enemies at specific positions
        Enemy e1 = new Enemy(0, 1);
        Enemy e2 = new Enemy(4, 1);
        Enemy e3 = new Enemy(8, 1);

        // Atomic variables to safely share data between threads
        AtomicInteger score = new AtomicInteger(0);       // Player score
        AtomicInteger timeCount = new AtomicInteger();    // Counter for enemy leveling
        AtomicBoolean endGame = new AtomicBoolean(false); // Flag to indicate game over
        boolean writeNewScore = false;                    // Flag to track if high score needs updating

        // Thread to continuously read player input from console
        Thread inputThread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    int key = System.in.read();
                    if (key != -1 && key != '\n') {
                        input = (char) key; // Store last key pressed
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException();
            }
        });

        // Main game loop running in its own thread
        Thread gameLoop = new Thread(() -> {
            while (true) {
                // Reset grid for each frame
                grid.setGrid();

                // Display current score
                System.out.println("Score: " + score.get());

                // Every 5 "ticks", level up alive enemies
                if (timeCount.get() == 5) {
                    if (e1.isAlive()) e1.levelUp();
                    if (e2.isAlive()) e2.levelUp();
                    if (e3.isAlive()) e3.levelUp();
                    timeCount.set(0);
                }

                // Check if all enemies are defeated, increment score and reset for next wave
                if (e1.getTotalEnemies() == 0) {
                    score.incrementAndGet();
                    e1.resetEnemy();
                    e2.resetEnemy();
                    e3.resetEnemy();
                    e1.levelComplete(1);
                    e2.levelComplete(1);
                    e3.levelComplete(1);
                }

                // Check bullet collisions with enemies
                if (e1.collide(b1.getX(), b1.getY())) {
                    e1.destroy();
                    grid.updateGrid(e1.getX(), e1.getY(), ".");
                } else if (e2.collide(b1.getX(), b1.getY())) {
                    e2.destroy();
                    grid.updateGrid(e2.getX(), e2.getY(), ".");
                } else if (e3.collide(b1.getX(), b1.getY())) {
                    e3.destroy();
                    grid.updateGrid(e3.getX(), e3.getY(), ".");
                }

                // Move bullet up
                b1.moveUp();

                // Reset bullet if it goes off the top
                if (b1.getY() <= 0) {
                    b1.updatePos(player.getX(), player.getY() - 1);
                }

                // Move player based on input
                if (input == 'a' && player.getX() != 0) {
                    player.moveLeft();
                } else if (input == 'd' && player.getX() != 8) {
                    player.moveRight();
                }

                // Reset input after processing
                input = ' ';

                // Update grid with player, bullet, and alive enemies
                grid.updateGrid(player.getX(), player.getY(), player.getSymbol());
                grid.updateGrid(b1.getX(), b1.getY(), b1.getSymbol());
                if (e1.isAlive()) grid.updateGrid(e1.getX(), e1.getY(), e1.getSymbol());
                if (e2.isAlive()) grid.updateGrid(e2.getX(), e2.getY(), e2.getSymbol());
                if (e3.isAlive()) grid.updateGrid(e3.getX(), e3.getY(), e3.getSymbol());

                // Draw the grid to the console
                grid.drawGrid();

                // Delay for next frame (~600ms)
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // Increment time counter
                timeCount.incrementAndGet();

                // Clear screen (console) by printing empty lines
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");

                // Check if any enemy reached the bottom (game over)
                if (e1.getY() == 8 || e2.getY() == 8 || e3.getY() == 8) {
                    endGame.set(true);
                    break;
                }
            }
        });

        // Start input and game loop threads
        inputThread.start();
        gameLoop.start();

        // Wait for the game loop to finish
        try {
            gameLoop.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Stop the input thread
        inputThread.interrupt();

        // High score file
        File file = new File("highscore.txt");

        // Display game over messages
        System.out.println("You died, the aliens got you");
        System.out.println("Your score was: " + score.get());

        // Check existing high score
        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String highScore = reader.readLine();
                System.out.println("Last High Score was: " + highScore);
                if (Integer.parseInt(highScore) < score.get()) {
                    writeNewScore = true;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Update high score if needed
        if (writeNewScore || !file.exists()) {
            try {
                FileWriter writer = new FileWriter(file);
                writer.write(String.valueOf(score.get()));
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Exit program
        System.exit(0);
    }
}