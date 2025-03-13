import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SimonGame extends JFrame {
    private SimonLogic gameLogic;
    private JButton startButton;
    private JLabel currentScoreLabel;
    private JTextArea scoreHistoryArea;
    private JPanel buttonPanel;
    private List<JButton> colorButtons;
    private JTextField nameField;

    public SimonGame() {
        setTitle("Simon Game");
        setSize(500, 500); // Adjusted size for better layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        gameLogic = new SimonLogic(this);

        // Name Input Field
        JPanel namePanel = new JPanel();
        namePanel.add(new JLabel("Enter your name: "));
        nameField = new JTextField(15);
        namePanel.add(nameField);

        // Start Button
        startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = nameField.getText().trim();
                if (playerName.isEmpty()) {
                    JOptionPane.showMessageDialog(SimonGame.this, "Please enter your name!");
                } else {
                    gameLogic.startGame();
                }
            }
        });

        // Current Score Label
        currentScoreLabel = new JLabel("Current Score: 0", SwingConstants.CENTER);
        currentScoreLabel.setFont(new Font("Arial", Font.BOLD, 20));

        // Score History Area
        scoreHistoryArea = new JTextArea();
        scoreHistoryArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(scoreHistoryArea);

        // Color Buttons (smaller size)
        buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // Added gaps between buttons
        colorButtons = new ArrayList<>();
        String[] colors = {"Red", "Green", "Blue", "Yellow"};
        Color[] buttonColors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
        for (int i = 0; i < colors.length; i++) {
            JButton button = new JButton(colors[i]);
            button.setBackground(buttonColors[i]);
            button.setOpaque(true);
            button.setBorderPainted(false);
            button.setFont(new Font("Arial", Font.BOLD, 18)); // Smaller font for buttons
            button.setPreferredSize(new Dimension(100, 100)); // Smaller button size
            int finalI = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameLogic.checkUserInput(colors[finalI]);
                }
            });
            colorButtons.add(button);
            buttonPanel.add(button);
        }

        // Add components to the frame
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(namePanel, BorderLayout.NORTH);
        topPanel.add(startButton, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(currentScoreLabel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void updateScore(int score) {
        currentScoreLabel.setText("Current Score: " + score);
    }

    public void updateScoreHistory(int score) {
        String playerName = nameField.getText().trim();
        if (playerName.isEmpty()) {
            playerName = "Anonymous";
        }
        scoreHistoryArea.append(playerName + ": " + score + "\n");
    }

    public void disableButtons() {
        for (JButton button : colorButtons) {
            button.setEnabled(false);
        }
    }

    public void enableButtons() {
        for (JButton button : colorButtons) {
            button.setEnabled(true);
        }
    }

    public void highlightButton(String color) {
        for (JButton button : colorButtons) {
            if (button.getText().equals(color)) {
                button.setBackground(Color.WHITE); // Highlight by changing color
                Timer timer = new Timer(500, e -> {
                    button.setBackground(getButtonColor(color)); // Revert to original color
                });
                timer.setRepeats(false);
                timer.start();
                break;
            }
        }
    }

    private Color getButtonColor(String color) {
        switch (color) {
            case "Red": return Color.RED;
            case "Green": return Color.GREEN;
            case "Blue": return Color.BLUE;
            case "Yellow": return Color.YELLOW;
            default: return Color.BLACK;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimonGame());
    }
}