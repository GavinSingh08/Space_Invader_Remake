# Space Invader Remake 

A console-based Space Invader–style game built in **Java**. This project demonstrates multithreading, file I/O, atomic variables, and basic game logic.

---

## Gameplay

- Control a player (`Δ`) at the bottom of a 9x9 grid.
- Aliens (enemies) spawn at the top and gradually move downward.
- Shoot bullets (`|`) to destroy aliens before they reach the bottom row.
- The game ends if any alien reaches the bottom.
- Your score is tracked and saved to `highscore.txt`.

---

## Controls

| Key | Action         |
|-----|----------------|
| `a` | Move left      |
| `d` | Move right     |

---

## Technical Highlights

### 1. Multithreading
- Separate threads for **player input** and **game loop**.
- Ensures smooth input handling and continuous game updates.

### 2. Thread Safety
- `AtomicInteger` and `AtomicBoolean` for shared counters and flags.
- `volatile` variable for real-time input tracking.

### 3. Game Logic
- Collision detection for bullets and enemies.
- Enemies move quicker with every wave beaten.
- Score increment and enemy reset when waves are cleared.

### 4. File I/O
- Reads and writes high scores using `BufferedReader` and `FileWriter`.
- Persists player progress between sessions.

### 5. Console Rendering
- 9x9 grid updated each frame.
- Clears and redraws the console for smooth display.

---
## Potential Improvements

Since this was my first medium-scaled Java project, there are many improvements I could implement.

- Grid size, sleep duration, and enemy positions could be constants.
- **Separation of concerns:** consider splitting into `GameManager`, `ScoreManager`, and `InputHandler` for cleaner design.
- **Thread cleanup:** allow input thread to exit gracefully using the `endGame` flag.
- **Readability:** extract helper methods (`handleCollisions()`, `updateGrid()`, `checkGameOver()`) from `main()` for clarity.
- Could implement a graphical interface, instead of choppy, console based screening.

