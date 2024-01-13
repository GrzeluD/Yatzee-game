package controller;

import view.DiceGUI;
import javax.swing.SwingUtilities;

import model.DiceModel;
import model.WrongResultsException;
import utils.ParserUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The main controller class for the Dice Game Simulator.
 *
 * @author Grzegorz Dziedzic
 * @version 1.3
 */
public class DiceGameSimulator {

    private DiceGUI gui;
    private DiceModel model;
    private ParserUtils parserUtils;

    /**
     * Constructor for the DiceGameSimulator.
     *
     * @param gui The graphical user interface.
     * @param model The model handling game logic.
     * @param parserUtils Utility class for parsing input.
     * @param args Command-line arguments for initial input.
     */
    public DiceGameSimulator(DiceGUI gui, DiceModel model, ParserUtils parserUtils, String[] args) {
        this.gui = gui;
        this.model = model;
        this.parserUtils = parserUtils;

        // Fill user input from command-line arguments if provided.
        if (args.length > 0) {
            this.gui.fillUserInputFromArgs(args);
        }

        // Set ActionListener for the "Calculate" button.
        gui.setCalculateButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateButtonClicked();
            }
        });
    }

    /**
     * Handles the event when the "Calculate" button is clicked.
     */
    private void calculateButtonClicked() {
        try {
            // Get user input from the GUI.
            String userInput = gui.getUserInput();

            // Validate input format using a regular expression.
            if (userInput.matches("\\d+(\\s\\d+)*")) {
                // Split the input and process the game results.
                String[] inputArray = userInput.split("\\s");
                List<Integer> parsedIntegers = model.processGameResults(parserUtils.parseStringArray(inputArray));

                // Evaluate the game result message and display results in the GUI.
                String resultMessage = model.evaluateGameResultMessage(parsedIntegers) + " " + gui.displayResults(parsedIntegers);
                gui.displayMessage(resultMessage);
                gui.addResultToHistoryList(resultMessage);

            } else {
                // Display an error message for invalid input.
                gui.displayError("Invalid input. Please enter valid numbers. Try again.");
            }

        } catch (WrongResultsException ex) {
            // Display an error message for wrong game results.
            gui.displayError(ex.getMessage() + " " + " Try again.");
        }
    }

    /**
     * The main method to start the Dice Game Simulator application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create instances of GUI, model, and utility classes.
            DiceGUI gui = new DiceGUI();
            DiceModel model = new DiceModel();
            ParserUtils parserUtils = new ParserUtils();

            // Create an instance of DiceGameSimulator and set up the GUI.
            DiceGameSimulator simulator = new DiceGameSimulator(gui, model, parserUtils, args);

            // Pack the GUI and make it visible.
            gui.pack();
            gui.setVisible(true);
        });
    }
}
