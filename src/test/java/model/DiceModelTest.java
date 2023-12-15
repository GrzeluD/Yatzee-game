package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Tests for the {@link DiceModel} class, covering input processing and result
 * messages.
 *
 * @author grzelu
 * @version 2.0
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

    @ParameterizedTest
    @MethodSource("validInputValues")
    public void testProcessGameResultsValid(List<Integer> inputValues) throws WrongResultsException {
        List<Integer> processedResults = diceModel.processGameResults(inputValues);
        Assertions.assertEquals(inputValues.size(), processedResults.size());
        Assertions.assertTrue(processedResults.get(0) <= processedResults.get(processedResults.size() - 1));
    }

    @ParameterizedTest
    @MethodSource("invalidSizeInputValues")
    public void testProcessGameResultsInvalidSize(List<Integer> inputValues) {
        Assertions.assertThrows(WrongResultsException.class, () -> diceModel.processGameResults(inputValues));
    }

    @ParameterizedTest
    @MethodSource("invalidRangeInputValues")
    public void testProcessGameResultsInvalidRange(List<Integer> inputValues) {
        Assertions.assertThrows(WrongResultsException.class, () -> diceModel.processGameResults(inputValues));
    }

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
    public void testGetGameResultMessage(int value1, int value2, int value3, int value4, int value5, String expectedMessage) {
        List<Integer> inputValues = Arrays.asList(value1, value2, value3, value4, value5);
        String actualMessage = diceModel.getGameResultMessage(inputValues);
        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
