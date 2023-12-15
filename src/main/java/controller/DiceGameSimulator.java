package controller;

import view.DiceView;
import model.DiceModel;
import model.WrongResultsException;
import utils.ParserUtils;
import java.util.List;

/**
 * Controller in the MVC architecture for the dice game simulation. Manages
 * interaction between the user interface and the game logic.
 *
 * @author grzelu
 * @version 2.0
 */
public class DiceGameSimulator {

    /**
     * Main method for the dice game simulator. Entry point for the program,
     * coordinates user input and game logic.
     *
     * @param args Command-line arguments provided to the program.
     * @throws WrongResultsException If an exception occurs during the game
     * simulation.
     */
    public static void main(String[] args) throws WrongResultsException {
        DiceView view = new DiceView();
        ParserUtils parserUtils = new ParserUtils();
        List<Integer> parsedArgs;

        try {
            // Parse command-line arguments or prompt user for input
            parsedArgs = parserUtils.parseStringArray(args);
            if (parsedArgs.isEmpty()) {
                parsedArgs = view.readUserInput();
            }

            DiceModel diceSet = new DiceModel();

            // Process game results and display them
            List<Integer> totalResults = diceSet.processGameResults(parsedArgs);
            view.displayResults(totalResults);

            // Get and display the game result message
            String resultMessage = diceSet.getGameResultMessage(totalResults);
            view.displayMessage(resultMessage);
        } catch (NumberFormatException | WrongResultsException e) {
            // Display error message in case of an exception
            view.displayMessage(e.getMessage());
        }
    }
}
