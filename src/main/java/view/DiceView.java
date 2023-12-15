package view;

import model.WrongResultsException;
import utils.ParserUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the view of a dice game. Provides methods for displaying results,
 * obtaining user input, and handling errors.
 *
 * @author grzelu
 * @version 2.0
 */
public class DiceView {

    /**
     * Displays the results of a dice game.
     *
     * @param results List of integers representing the game results.
     */
    public void displayResults(List<Integer> results) {
        System.out.println("Dice game results:");
        for (Integer result : results) {
            System.out.print(result + " ");
        }
        System.out.println();
    }

    /**
     * Displays a message to the user.
     *
     * @param message The message to be displayed.
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to be displayed.
     */
    public void displayErrorMessage(String message) {
        System.out.println("Error: " + message);
    }

    /**
     * Obtains user input for the dice game.
     *
     * @return List of integers representing user input.
     * @throws WrongResultsException If input validation fails.
     */
    public List<Integer> readUserInput() throws WrongResultsException {
        Scanner scanner = new Scanner(System.in);
        String[] inputArray;
        List<Integer> parsedIntegers;

        System.out.println("Roll five dice, then provide the results separated by spaces:");
        String userInput = scanner.nextLine().trim();
        inputArray = userInput.split("\\s");

        if (userInput.isEmpty()) {
            return new ArrayList<>();
        }

        try {
            ParserUtils parserUtils = new ParserUtils();
            parsedIntegers = parserUtils.parseStringArray(inputArray);
        } catch (NumberFormatException e) {
            throw new WrongResultsException("Argument is a non-number value. Please provide valid numbers.");
        } catch (WrongResultsException e) {
            System.out.println(e);
            throw e;
        }

        return parsedIntegers;
    }
}
