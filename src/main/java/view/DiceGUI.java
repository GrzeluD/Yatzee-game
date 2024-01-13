package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * The graphical user interface class for the Dice Game Simulator.
 *
 * @author Grzegorz Dziedzic
 * @version 1.3
 */
public class DiceGUI extends JFrame {

    private JLabel headingLabel;
    private JTextField userInputField;
    private JButton calculateButton;
    private DefaultListModel<String> historyListModel;
    private JList<String> historyList;
    private JTextArea resultText;
    private int historyCounter = 0;

    /**
     * Constructs a new DiceGUI instance.
     */
    public DiceGUI() {
        initializeComponents();
        setLayout();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
    }

    /**
     * Initializes GUI components and sets their properties.
     */
    private void initializeComponents() {
        headingLabel = new JLabel("Roll five dices and enter results separated by spaces:");
        userInputField = new JTextField(20);
        calculateButton = new JButton("Calculate");
        historyListModel = new DefaultListModel<>();
        historyList = new JList(historyListModel);
        resultText = new JTextArea();
        resultText.setEditable(false);

        // Set tooltips for components.
        calculateButton.setToolTipText("Click here to Calculate results of your throws");
        userInputField.setToolTipText("Enter results separated by spaces.");
        historyList.setToolTipText("List of results");
        resultText.setToolTipText("Calculated results of your throws");

        // Set mnemonic for the "Calculate" button.
        calculateButton.setMnemonic(KeyEvent.VK_C);

        // Set key binding for focusing on the userInputField using Alt + U.
        userInputField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.ALT_DOWN_MASK),
                "focusUserInputField");

        userInputField.getActionMap().put("focusUserInputField", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userInputField.requestFocusInWindow();
            }
        });
    }

    /**
     * Fills the userInputField with values from command-line arguments.
     *
     * @param args Command-line arguments containing dice values.
     */
    public void fillUserInputFromArgs(String[] args) {
        StringBuilder userInputBuilder = new StringBuilder();
        for (String arg : args) {
            userInputBuilder.append(arg).append(" ");
        }
        userInputField.setText(userInputBuilder.toString().trim());
    }

    /**
     * Sets the layout of the GUI components.
     */
    private void setLayout() {
        JPanel panel = new JPanel(new BorderLayout());

        panel.add(headingLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.add(userInputField);
        inputPanel.add(calculateButton);
        panel.add(inputPanel, BorderLayout.CENTER);

        JScrollPane historyScrollPane = new JScrollPane(historyList);
        historyScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        historyScrollPane.setPreferredSize(new Dimension(400, 200));  // Preferred width

        panel.add(historyScrollPane, BorderLayout.SOUTH);

        setContentPane(panel);
    }

    /**
     * Adds a calculated result to the history list with a roll counter.
     *
     * @param resultItem The result to be added to the history list.
     */
    public void addResultToHistoryList(String resultItem) {
        historyListModel.addElement(addCounterToHistoryElement() + " " + resultItem);
    }

    /**
     * Retrieves user input from the userInputField.
     *
     * @return The user input as a string.
     */
    public String getUserInput() {
        return userInputField.getText();
    }

    /**
     * Sets an ActionListener for the Calculate button.
     *
     * @param listener The ActionListener to be set for the Calculate button.
     */
    public void setCalculateButtonListener(ActionListener listener) {
        calculateButton.addActionListener(listener);
    }

    /**
     * Displays the results in a formatted string.
     *
     * @param results The list of integer results.
     * @return A formatted string displaying the dice game results.
     */
    public String displayResults(List<Integer> results) {
        StringBuilder textResult = new StringBuilder("Dice game results: ");
        for (Integer result : results) {
            textResult.append(result).append(" ");
        }
        return textResult.toString();
    }

    /**
     * Adds a counter to the history element, indicating the roll number.
     *
     * @return The string containing the roll counter.
     */
    private String addCounterToHistoryElement() {
        historyCounter++;
        return "Roll " + historyCounter + ":";
    }

    /**
     * Displays an informational message using a JOptionPane.
     *
     * @param message The message to be displayed.
     */
    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Results:", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Displays an error message using a JOptionPane.
     *
     * @param message The error message to be displayed.
     */
    public void displayError(String message) {
        JOptionPane.showMessageDialog(this, message, "Something went wrong...", JOptionPane.ERROR_MESSAGE);
    }
}
