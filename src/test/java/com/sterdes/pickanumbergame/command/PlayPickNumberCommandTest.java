package com.sterdes.pickanumbergame.command;

import com.sterdes.pickanumbergame.api.model.PickNumberResultStatus;
import com.sterdes.pickanumbergame.api.model.PlayPickNumber;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

@Tag("unit")
class PlayPickNumberCommandTest {

    public SecureRandom secureRandom = mock(SecureRandom.class, withSettings().withoutAnnotations());
    private final PlayPickNumberCommand playPickNumberCommand = new PlayPickNumberCommand(secureRandom);

    @Test
    public void itResultsAsWinWhenPlayerPicksGreaterNumberThanComputer() {
        var playerId = UUID.randomUUID();
        var playerSelectedNumber = 50;
        var playerPlacedBet = new BigDecimal("40.5");
        var playPickNumber = new PlayPickNumber(
                playerId,
                playerSelectedNumber,
                playerPlacedBet
        );

        when(secureRandom.nextInt(101)).thenReturn(32);

        var result = playPickNumberCommand.execute(playPickNumber);

        assertEquals(PickNumberResultStatus.WIN, result.getStatus());
    }

    @Test
    public void itResultsAsLoseWhenComputerPicksGreaterNumberThanPlayer() {
        var playerId = UUID.randomUUID();
        var playerSelectedNumber = 50;
        var playerPlacedBet = new BigDecimal("40.5");
        var playPickNumber = new PlayPickNumber(
                playerId,
                playerSelectedNumber,
                playerPlacedBet
        );

        when(secureRandom.nextInt(101)).thenReturn(70);

        var result = playPickNumberCommand.execute(playPickNumber);

        assertEquals(PickNumberResultStatus.LOSE, result.getStatus());
    }

    @Test
    public void itResultsAsLoseWhenPlayerPicksSameNumberAsComputer() {
        var playerSelectedNumber = 50;
        var playerId = UUID.randomUUID();
        var playerPlacedBet = new BigDecimal("40.5");
        var playPickNumber = new PlayPickNumber(
                playerId,
                playerSelectedNumber,
                playerPlacedBet
        );

        when(secureRandom.nextInt(101)).thenReturn(playerSelectedNumber);

        var result = playPickNumberCommand.execute(playPickNumber);

        assertEquals(PickNumberResultStatus.LOSE, result.getStatus());
    }
}