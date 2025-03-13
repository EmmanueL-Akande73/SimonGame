import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class SimonLogic {
    private SimonGame gameUI;
    private List<String> sequence;
    private int userInputIndex;
    private int score;
    private Random random;
    private Timer timer;

    public SimonLogic(SimonGame gameUI) {
        this.gameUI = gameUI;
        sequence = new ArrayList<>();
        random = new Random();
        score = 0;
    }

    public void startGame() {
        sequence.clear();
        score = 0;
        userInputIndex = 0;
        gameUI.updateScore(score);
        gameUI.disableButtons();
        addToSequence();
        playSequence();
    }

    void addToSequence() {
        String[] colors = {"Red", "Green", "Blue", "Yellow"};
        sequence.add(colors[random.nextInt(colors.length)]);
    }

    private void playSequence() {
        timer = new Timer(1000, e -> {
            if (userInputIndex < sequence.size()) {
                gameUI.highlightButton(sequence.get(userInputIndex));
                userInputIndex++;
            } else {
                ((Timer) e.getSource()).stop();
                userInputIndex = 0;
                gameUI.enableButtons();
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    public void checkUserInput(String color) {
        if (color.equals(sequence.get(userInputIndex))) {
            userInputIndex++;
            if (userInputIndex == sequence.size()) {
                score++;
                gameUI.updateScore(score);
                userInputIndex = 0;
                gameUI.disableButtons();
                addToSequence();
                playSequence();
            }
        } else {
            gameOver();
        }
    }

    private void gameOver() {
        gameUI.updateScoreHistory(score);
        gameUI.disableButtons();
        JOptionPane.showMessageDialog(gameUI, "Game Over! Final Score: " + score);
        startGame(); // Restart the game
    }
    public List<String> getSequence() {
        return sequence;
    }

    public int getScore() {
        return score;
    }
}