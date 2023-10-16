package com.sterdes.pickanumbergame.command;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("unit")
class CalculatePickNumberWinChanceTest {

    @Test
    public void itSuccessfullyCalculatesSimpleCase() {
        // player selected the number 50 and bet 40.5, the win would be 80.19
        var playerSelectedNumber = 50;
        var playerPlacedBet = new BigDecimal("40.5");

        var winAmount = CalculatePickNumberChance.execute(playerPlacedBet, playerSelectedNumber);

        assertEquals(0, new BigDecimal("80.19").compareTo(winAmount));
    }

    @ParameterizedTest
    @MethodSource("validRangeOfPlayerSelectedNumber")
    public void itDoesNotThrowOnValidPlayerPickedNumber(Integer playerSelectedNumber) {
        var playerPlacedBet = new BigDecimal("40.5");

        assertDoesNotThrow(() -> CalculatePickNumberChance.execute(playerPlacedBet, playerSelectedNumber));
    }

    @ParameterizedTest
    @MethodSource("validPlayerPlacedBetAmounts")
    public void itDoesNotThrowOnBigBetAmount(BigDecimal playerPlacedBet) {
        var playerSelectedNumber = 50;

        assertDoesNotThrow(() -> CalculatePickNumberChance.execute(playerPlacedBet, playerSelectedNumber));
    }

    private static IntStream validRangeOfPlayerSelectedNumber() {
        // 1 to 100
        return IntStream.range(1, 101);
    }

    private static Stream<BigDecimal> validPlayerPlacedBetAmounts() {
        return Stream.of(
                BigDecimal.ONE,
                BigDecimal.TEN,
                new BigDecimal("123.11"),
                new BigDecimal("1234.12"),
                new BigDecimal("12345.23"),
                new BigDecimal("123456.94"),
                new BigDecimal("1234567.85"),
                new BigDecimal("1000000.26"),
                new BigDecimal("123456789.27"),
                new BigDecimal("123456789123456789.28"),
                new BigDecimal("123456789123456789123456789.29")
        );
    }

}