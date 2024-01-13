package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * Tests for the {@link DiceModel} class, covering input processing and result
 * messages.
 *
 * @author Grzegorz Dziedzic
 * @version 1.3
 */
public class DiceModelTest {

    DiceModel diceModel = new DiceModel();

    /**
     * Generates valid input values for testing.
     *
     * @return A stream of lists representing valid input values.
     */
    private static Stream<List<Integer>> validInputValues() {
        return Stream.of(
                Arrays.asList(1, 2, 3, 4, 5),
                Arrays.asList(3, 1, 5, 4, 2),
                Arrays.asList(6, 6, 6, 6, 6)
        );
    }

    /**
     * Generates input values with invalid sizes for testing.
     *
     * @return A stream of lists representing input values with invalid sizes.
     */
    private static Stream<List<Integer>> invalidSizeInputValues() {
        return Stream.of(
                Arrays.asList(1, 2, 3, 4),
                Arrays.asList(3, 1, 5, 4),
                Arrays.asList(6, 6, 6, 6, 6, 6)
        );
    }

    /**
     * Generates input values with invalid ranges for testing.
     *
     * @return A stream of lists representing input values with invalid ranges.
     */
    private static Stream<List<Integer>> invalidRangeInputValues() {
        return Stream.of(
                Arrays.asList(1, 2, 3, 4, 8),
                Arrays.asList(0, 1, 5, 4, 2),
                Arrays.asList(7, 6, 6, 6, 6)
        );
    }

    /**
     * Generates input values with only null values for testing.
     *
     * @return A stream of lists containing only null values.
     */
    private static Stream<List<Integer>> onlyNullInputValue() {
        return Stream.of(
                Collections.singletonList(null)
        );
    }

    /**
     * Tests the processGameResults method with null input values.
     *
     * @param inputValues The input values to be tested.
     */
    @ParameterizedTest
    @MethodSource("onlyNullInputValue")
    public void testProcessGameResultsWithNull(List<Integer> inputValues) {
        Assertions.assertThrows(WrongResultsException.class, () -> diceModel.processGameResults(inputValues));
    }

    /**
     * Tests the processGameResults method with valid input values.
     *
     * @param inputValues The input values to be tested.
     * @throws WrongResultsException If there are wrong results.
     */
    @ParameterizedTest
    @MethodSource("validInputValues")
    public void testProcessGameResultsValid(List<Integer> inputValues) throws WrongResultsException {
        List<Integer> processedResults = diceModel.processGameResults(inputValues);
        Assertions.assertEquals(inputValues.size(), processedResults.size());
        Assertions.assertTrue(processedResults.get(0) <= processedResults.get(processedResults.size() - 1));
    }

    /**
     * Tests the processGameResults method with invalid input sizes.
     *
     * @param inputValues The input values to be tested.
     */
    @ParameterizedTest
    @MethodSource("invalidSizeInputValues")
    public void testProcessGameResultsInvalidSize(List<Integer> inputValues) {
        Assertions.assertThrows(WrongResultsException.class, () -> diceModel.processGameResults(inputValues));
    }

    /**
     * Tests the processGameResults method with invalid input ranges.
     *
     * @param inputValues The input values to be tested.
     */
    @ParameterizedTest
    @MethodSource("invalidRangeInputValues")
    public void testProcessGameResultsInvalidRange(List<Integer> inputValues) {
        Assertions.assertThrows(WrongResultsException.class, () -> diceModel.processGameResults(inputValues));
    }

    /**
     * Tests the evaluateGameResultMessage method with different input values
     * and expected messages.
     *
     * @param value1 The value of the first dice.
     * @param value2 The value of the second dice.
     * @param value3 The value of the third dice.
     * @param value4 The value of the fourth dice.
     * @param value5 The value of the fifth dice.
     * @param expectedMessage The expected message for the given input values.
     */
    @ParameterizedTest
    @CsvSource({
        "1, 2, 3, 4, 5, No special combination.",
        "3, 3, 3, 3, 3, Five of a kind!",
        "1, 1, 1, 1, 2, Four of a kind!",
        "1, 1, 3, 3, 3, Full house!",
        "1, 2, 3, 3, 3, Three of a kind!",
        "1, 1, 3, 3, 4, Two pairs!",
        "1, 1, 3, 4, 5, One pair!"
    })
    public void testEvaluateGameResultMessage(int value1, int value2, int value3, int value4, int value5, String expectedMessage) {
        List<Integer> inputValues = Arrays.asList(value1, value2, value3, value4, value5);
        String actualMessage = diceModel.evaluateGameResultMessage(inputValues);
        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
