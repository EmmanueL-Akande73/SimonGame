import java.util.ArrayList; // Importing ArrayList to store the sequence
import java.util.List; // Importing List interface for sequence handling
import java.util.Random; // Importing Random for generating random colors
import javax.swing.*; // Importing Swing components for UI interactions

public class SimonLogic {
    private SimonGame gameUI; // Reference to the game UI
    private List<String> sequence; // Stores the sequence of colors
    private int userInputIndex; // Tracks user input progress
    private int score; // Stores the player's score
    private Random random; // Random generator for color selection
    private Timer timer; // Timer for playing sequence with delays

    // Constructor to initialize variables and link the UI
    public SimonLogic(SimonGame gameUI) {
        this.gameUI = gameUI; // Assigns the UI reference
        sequence = new ArrayList<>(); // Initializes the sequence list
        random = new Random(); // Initializes the random object
        score = 0; // Sets initial score to 0
    }

    // Starts the game by resetting the sequence and score
    public void startGame() {
        sequence.clear(); // Clears any previous sequence
        score = 0; // Resets the score
        userInputIndex = 0; // Resets user input index
        gameUI.updateScore(score); // Updates the UI score display
        gameUI.disableButtons(); // Disables user input buttons
        addToSequence(); // Adds the first color to the sequence
        playSequence(); // Starts playing the sequence
    }

    // Adds a random color to the sequence
    void addToSequence() {
        String[] colors = {"Red", "Green", "Blue", "Yellow"}; // Array of possible colors
        sequence.add(colors[random.nextInt(colors.length)]); // Adds a random color
    }

    // Plays the sequence back to the user with a delay
    private void playSequence() {
        timer = new Timer(1000, e -> { // Creates a timer with 1-second intervals
            if (userInputIndex < sequence.size()) { // Checks if there are more colors to show
                gameUI.highlightButton(sequence.get(userInputIndex)); // Highlights the current color button
                userInputIndex++; // Moves to the next color in sequence
            } else {
                ((Timer) e.getSource()).stop(); // Stops the timer when sequence ends
                userInputIndex = 0; // Resets user input index
                gameUI.enableButtons(); // Enables user input buttons
            }
        });
        timer.setInitialDelay(0); // Starts the timer immediately
        timer.start(); // Begins playing the sequence
    }

    // Checks if the user's input matches the sequence
    public void checkUserInput(String color) {
        if (color.equals(sequence.get(userInputIndex))) { // Checks if input matches current sequence step
            userInputIndex++; // Moves to the next expected input
            if (userInputIndex == sequence.size()) { // Checks if the user completed the sequence
                score++; // Increments the score
                gameUI.updateScore(score); // Updates the UI score display
                userInputIndex = 0; // Resets user input index
                gameUI.disableButtons(); // Disables user buttons before next round
                addToSequence(); // Adds a new color to the sequence
                playSequence(); // Replays the updated sequence
            }
        } else {
            gameOver(); // Calls game over if input is incorrect
        }
    }

    // Handles the game-over scenario
    private void gameOver() {
        gameUI.updateScoreHistory(score); // Updates the score history in UI
        gameUI.disableButtons(); // Disables user buttons
        JOptionPane.showMessageDialog(gameUI, "Game Over! Final Score: " + score); // Displays game over message
        startGame(); // Restarts the game automatically
    }

    // Getter method to retrieve the sequence
    public List<String> getSequence() {
        return sequence;
    }

    // Getter method to retrieve the current score
    public int getScore() {
        return score;
    }
}
