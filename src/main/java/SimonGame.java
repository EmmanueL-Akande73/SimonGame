import javax.swing.*; // Import Swing library for GUI components
import java.awt.*; // Import AWT library for basic window and graphics
import java.awt.event.ActionEvent; // Import ActionEvent for handling button clicks
import java.awt.event.ActionListener; // Import ActionListener for button click events
import java.util.ArrayList; // Import ArrayList for dynamic list of buttons
import java.util.List; // Import List interface for list operations

public class SimonGame extends JFrame { // Main class for the Simon game, extending JFrame for the window
    private SimonLogic gameLogic; // Instance of the game logic class
    private JButton startButton; // Button to start the game
    private JLabel currentScoreLabel; // Label to display the current score
    private JTextArea scoreHistoryArea; // Text area to display score history
    private JPanel buttonPanel; // Panel to hold the color buttons
    private List<JButton> colorButtons; // List to store the color buttons
    private JTextField nameField; // Text field for the player to enter their name

    public SimonGame() { // Constructor for the SimonGame class
        setTitle("Simon Game"); // Set the title of the window
        setSize(500, 500); // Set the size of the window (width, height)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the application when the window is closed
        setLayout(new BorderLayout()); // Use BorderLayout for the main layout

        gameLogic = new SimonLogic(this); // Initialize the game logic with a reference to this GUI

        // Name Input Field
        JPanel namePanel = new JPanel(); // Create a panel for the name input
        namePanel.add(new JLabel("Enter your name: ")); // Add a label to the panel
        nameField = new JTextField(15); // Create a text field for the player's name
        namePanel.add(nameField); // Add the text field to the panel

        // Start Button
        startButton = new JButton("Start Game"); // Create the start button
        startButton.addActionListener(new ActionListener() { // Add an action listener to the button
            @Override
            public void actionPerformed(ActionEvent e) { // Define what happens when the button is clicked
                String playerName = nameField.getText().trim(); // Get the player's name from the text field
                if (playerName.isEmpty()) { // Check if the name is empty
                    JOptionPane.showMessageDialog(SimonGame.this, "Please enter your name!"); // Show an error message
                } else {
                    gameLogic.startGame(); // Start the game if the name is provided
                }
            }
        });

        // Current Score Label
        currentScoreLabel = new JLabel("Current Score: 0", SwingConstants.CENTER); // Create a label for the current score
        currentScoreLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set the font for the label

        // Score History Area
        scoreHistoryArea = new JTextArea(); // Create a text area for the score history
        scoreHistoryArea.setEditable(false); // Make the text area read-only
        JScrollPane scrollPane = new JScrollPane(scoreHistoryArea); // Add a scroll pane to the text area

        // Color Buttons (smaller size)
        buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // Create a panel with a 2x2 grid layout and gaps
        colorButtons = new ArrayList<>(); // Initialize the list of color buttons
        String[] colors = {"Red", "Green", "Blue", "Yellow"}; // Array of button colors
        Color[] buttonColors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW}; // Array of corresponding colors
        for (int i = 0; i < colors.length; i++) { // Loop through the colors
            JButton button = new JButton(colors[i]); // Create a button for each color
            button.setBackground(buttonColors[i]); // Set the button's background color
            button.setOpaque(true); // Make the button opaque
            button.setBorderPainted(false); // Remove the button border
            button.setFont(new Font("Arial", Font.BOLD, 18)); // Set the font for the button
            button.setPreferredSize(new Dimension(100, 100)); // Set the preferred size of the button
            int finalI = i; // Store the current index for the action listener
            button.addActionListener(new ActionListener() { // Add an action listener to the button
                @Override
                public void actionPerformed(ActionEvent e) { // Define what happens when the button is clicked
                    gameLogic.checkUserInput(colors[finalI]); // Check the user's input against the game logic
                }
            });
            colorButtons.add(button); // Add the button to the list
            buttonPanel.add(button); // Add the button to the panel
        }

        // Add components to the frame
        JPanel topPanel = new JPanel(new BorderLayout()); // Create a panel for the top section
        topPanel.add(namePanel, BorderLayout.NORTH); // Add the name panel to the top
        topPanel.add(startButton, BorderLayout.SOUTH); // Add the start button below the name panel

        JPanel centerPanel = new JPanel(new BorderLayout()); // Create a panel for the center section
        centerPanel.add(currentScoreLabel, BorderLayout.NORTH); // Add the score label to the top
        centerPanel.add(buttonPanel, BorderLayout.CENTER); // Add the button panel to the center

        add(topPanel, BorderLayout.NORTH); // Add the top panel to the frame
        add(centerPanel, BorderLayout.CENTER); // Add the center panel to the frame
        add(scrollPane, BorderLayout.SOUTH); // Add the score history area to the bottom

        setVisible(true); // Make the window visible
    }

    public void updateScore(int score) { // Method to update the current score
        currentScoreLabel.setText("Current Score: " + score); // Update the score label
    }

    public void updateScoreHistory(int score) { // Method to update the score history
        String playerName = nameField.getText().trim(); // Get the player's name
        if (playerName.isEmpty()) { // If the name is empty, use "Anonymous"
            playerName = "Anonymous";
        }
        scoreHistoryArea.append(playerName + ": " + score + "\n"); // Append the score to the history
    }

    public void disableButtons() { // Method to disable all color buttons
        for (JButton button : colorButtons) { // Loop through the buttons
            button.setEnabled(false); // Disable each button
        }
    }

    public void enableButtons() { // Method to enable all color buttons
        for (JButton button : colorButtons) { // Loop through the buttons
            button.setEnabled(true); // Enable each button
        }
    }

    public void highlightButton(String color) { // Method to highlight a button
        for (JButton button : colorButtons) { // Loop through the buttons
            if (button.getText().equals(color)) { // Find the button with the matching color
                button.setBackground(Color.WHITE); // Highlight the button by changing its color
                Timer timer = new Timer(500, e -> { // Create a timer to revert the color
                    button.setBackground(getButtonColor(color)); // Revert to the original color
                });
                timer.setRepeats(false); // Ensure the timer only runs once
                timer.start(); // Start the timer
                break; // Exit the loop
            }
        }
    }

    private Color getButtonColor(String color) { // Method to get the original color of a button
        switch (color) { // Switch based on the color name
            case "Red": return Color.RED; // Return red for "Red"
            case "Green": return Color.GREEN; // Return green for "Green"
            case "Blue": return Color.BLUE; // Return blue for "Blue"
            case "Yellow": return Color.YELLOW; // Return yellow for "Yellow"
            default: return Color.BLACK; // Default to black (should not happen)
        }
    }

    public static void main(String[] args) { // Main method to start the application
        SwingUtilities.invokeLater(() -> new SimonGame()); // Create the SimonGame instance on the event dispatch thread
    }
}