import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class SimonLogicTest {
    private SimonLogic simonLogic;
    private SimonGame simonGame;

    @BeforeEach
    void setUp() {
        simonGame = new SimonGame(); // Create a mock SimonGame
        simonLogic = new SimonLogic(simonGame);
    }

    @Test
    void testSequenceGeneration() {
        // Start the game to generate a sequence
        simonLogic.startGame();

        // Get the generated sequence
        List<String> sequence = simonLogic.getSequence();

        // Verify that the sequence is not empty
        assertFalse(sequence.isEmpty(), "Sequence should not be empty");

        // Verify that the sequence contains valid colors
        for (String color : sequence) {
            assertTrue(isValidColor(color), "Sequence contains invalid color: " + color);
        }
    }

    @Test
    void testStartGameAndUserInput() {
        // Start the game to generate a sequence
        simonLogic.startGame();

        // Get the first color in the sequence
        String firstColor = simonLogic.getSequence().get(0);

        // Simulate correct user input
        simonLogic.checkUserInput(firstColor);

        // Verify that the score increases after correct input
        assertEquals(1, simonLogic.getScore(), "Score should increase after correct input");

        // Simulate incorrect user input
        simonLogic.checkUserInput("InvalidColor");

        // Verify that the game is over (score should reset to 0)
        assertEquals(0, simonLogic.getScore(), "Score should reset to 0 after incorrect input");
    }

    // Integration Test 1: Simulate a full round of gameplay
    @Test
    void testFullRoundOfGameplay() {
        // Start the game to generate a sequence
        simonLogic.startGame();

        // Get the sequence
        List<String> sequence = simonLogic.getSequence();


        // Add another color to the sequence and simulate one incorrect input
        simonLogic.addToSequence();
        simonLogic.checkUserInput("InvalidColor");

        // Verify that the game resets after incorrect input
        assertEquals(0, simonLogic.getScore(), "Score should reset to 0 after incorrect input");
    }

    // Integration Test 2: Simulate multiple rounds with increasing difficulty
    @Test
    void testMultipleRoundsWithIncreasingDifficulty() {
        // Start the game to generate a sequence
        simonLogic.startGame();

        // Simulate multiple rounds of gameplay
        int rounds = 5; // Number of rounds to simulate
        for (int i = 0; i < rounds; i++) {
            // Get the sequence
            List<String> sequence = simonLogic.getSequence();

            // Simulate correct user inputs for the entire sequence
        }

        // Simulate an incorrect input to end the game
        simonLogic.checkUserInput("InvalidColor");

        // Verify that the game resets after incorrect input
        assertEquals(0, simonLogic.getScore(), "Score should reset to 0 after incorrect input");
    }

    // Helper method to check if a color is valid
    private boolean isValidColor(String color) {
        return color.equals("Red") || color.equals("Green") || color.equals("Blue") || color.equals("Yellow");
    }
}