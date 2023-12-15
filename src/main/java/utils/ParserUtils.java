package utils;

import model.WrongResultsException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for parsing and validating input arguments for the dice game.
 * Handles conversion from string array to a list of integers, validating each
 * element.
 *
 * @author grzelu
 * @version 2.0
 */
public class ParserUtils {

    /**
     * Parses a string array into a list of integers, validating each element.
     *
     * @param args The string array to be parsed.
     * @return A list of integers parsed from the input string array.
     * @throws NumberFormatException If any element in the array is not a valid
     * integer.
     * @throws WrongResultsException If validation of any element fails.
     */
    public List<Integer> parseStringArray(String[] args) throws NumberFormatException, WrongResultsException {
        List<Integer> integers = new ArrayList<>();

        if (args.length != 0) {
            for (String arg : args) {
                try {
                    integers.add(parseAndValidate(arg));
                } catch (WrongResultsException e) {
                    throw e; // Stop processing and propagate the error upward
                }
            }
        }

        return integers;
    }

    /**
     * Parses and validates an individual string argument.
     *
     * @param arg The string argument to be parsed and validated.
     * @return The integer value obtained from parsing the input string.
     * @throws WrongResultsException If validation of the argument fails.
     */
    private int parseAndValidate(String arg) throws WrongResultsException {
        return Integer.parseInt(arg);
    }
}
