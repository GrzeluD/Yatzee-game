package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Functional interface defining a strategy for processing a list of integer
 * results.
 */
interface ResultProcessor {

    /**
     * Processes the given list of integer results according to the specified
     * strategy.
     *
     * @param results The list of results to be processed.
     * @return The processed list of integer results.
     */
    List<Integer> processResults(List<Integer> results);
}

/**
 * Class representing the model of a dice game. Encapsulates logic for
 * processing input arguments, validation, and storing results of a dice game.
 *
 * @author grzelu
 * @version 2.0
 */
public class DiceModel {

    /**
     * Checks if the given integer is within the valid dice face range.
     *
     * @param parsedInt The integer to check.
     * @return True if the integer is within the range (1-6), false otherwise.
     */
    private boolean isArgumentInRange(int parsedInt) {
        return (parsedInt > 0 && parsedInt < 7);
    }

    /**
     * Checks if the list has exactly five elements.
     *
     * @param args The list to check.
     * @return True if the list has five elements, false otherwise.
     */
    private boolean hasFiveElements(List<Integer> args) {
        return args.size() == 5;
    }

    /**
     * Processes the input arguments of a dice game. Validates the arguments and
     * sorts them.
     *
     * @param args The list of input arguments.
     * @return A sorted list of validated arguments.
     * @throws WrongResultsException If validation fails.
     */
    public List<Integer> processGameResults(List<Integer> args) throws WrongResultsException {
        if (!hasFiveElements(args) || args.isEmpty()) {
            throw new WrongResultsException("Too many or not enough arguments");
        }

        List<Integer> currentResults = new ArrayList<>();

        for (Integer arg : args) {
            if (!isArgumentInRange(arg)) {
                throw new WrongResultsException("Argument out of range");
            }

            currentResults.add(arg);
        }

        ResultProcessor resultProcessor = (List<Integer> results) -> {
            Collections.sort(results);
            return results;
        };

        return resultProcessor.processResults(currentResults);
    }

    /**
     * Determines the game result message based on the provided results.
     *
     * @param results The list of sorted dice values.
     * @return A message describing the result of the game.
     */
    public String getGameResultMessage(List<Integer> results) {
        if (isFiveOfAKind(results)) {
            return "Five of a kind!";
        } else if (isFourOfAKind(results)) {
            return "Four of a kind!";
        } else if (isFullHouse(results)) {
            return "Full house!";
        } else if (isThreeOfAKind(results)) {
            return "Three of a kind!";
        } else if (isTwoPairs(results)) {
            return "Two pairs!";
        } else if (isOnePair(results)) {
            return "One pair!";
        } else {
            return "No special combination.";
        }
    }

    /**
     * Checks if the provided results contain five identical values.
     *
     * @param results The sorted list of dice values.
     * @return True if all values are the same, false otherwise.
     */
    private boolean isFiveOfAKind(List<Integer> results) {
        return results.stream().distinct().count() == 1;
    }

    /**
     * Checks if the provided results contain four identical values.
     *
     * @param results The sorted list of dice values.
     * @return True if at least four values are the same, false otherwise.
     */
    private boolean isFourOfAKind(List<Integer> results) {
        return results.stream().anyMatch(value -> Collections.frequency(results, value) >= 4);
    }

    /**
     * Checks if the provided results form a full house combination.
     *
     * @param results The sorted list of dice values.
     * @return True if the results form a full house, false otherwise.
     */
    private boolean isFullHouse(List<Integer> results) {
        return (Objects.equals(results.get(0), results.get(1)) && Objects.equals(results.get(3), results.get(4))
                && (Objects.equals(results.get(2), results.get(0)) || Objects.equals(results.get(2), results.get(4))));
    }

    /**
     * Checks if the provided results contain three identical values.
     *
     * @param results The sorted list of dice values.
     * @return True if at least three values are the same, false otherwise.
     */
    private boolean isThreeOfAKind(List<Integer> results) {
        return results.stream().anyMatch(value -> Collections.frequency(results, value) >= 3);
    }

    /**
     * Checks if the provided results contain two pairs of identical values.
     *
     * @param results The sorted list of dice values.
     * @return True if the results contain two pairs, false otherwise.
     */
    private boolean isTwoPairs(List<Integer> results) {
        int countPairs = 0;
        for (int i = 0; i < results.size() - 1; i++) {
            if (results.get(i).equals(results.get(i + 1))) {
                countPairs++;
                i++;
            }
        }
        return countPairs == 2;
    }

    /**
     * Checks if the provided results contain one pair of identical values.
     *
     * @param results The sorted list of dice values.
     * @return True if the results contain one pair, false otherwise.
     */
    private boolean isOnePair(List<Integer> results) {
        return results.stream().anyMatch(value -> Collections.frequency(results, value) == 2);
    }
}
