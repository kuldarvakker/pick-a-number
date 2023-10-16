package com.sterdes.pickanumbergame.command;

import com.sterdes.pickanumbergame.api.model.PickNumberResult;
import com.sterdes.pickanumbergame.api.model.PickNumberResultStatus;
import com.sterdes.pickanumbergame.api.model.PlayPickNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class PlayPickNumberCommand {

    private final SecureRandom secureRandom;

    public PickNumberResult execute(PlayPickNumber playPickNumber) {
        var serverPickedNumber = secureRandom.nextInt(101);
        var isPlayerWinner = serverPickedNumber < playPickNumber.getPlayerSelectedNumber();

        if (!isPlayerWinner) {
            return new PickNumberResult(
                    playPickNumber.getPlayerId(),
                    PickNumberResultStatus.LOSE,
                    playPickNumber.getPlayerSelectedBet(),
                    playPickNumber.getPlayerSelectedNumber(),
                    serverPickedNumber
            );
        }

        var winAmount = CalculatePickNumberChance.execute(
                playPickNumber.getPlayerSelectedBet(),
                playPickNumber.getPlayerSelectedNumber()
        );

        return new PickNumberResult(
                playPickNumber.getPlayerId(),
                PickNumberResultStatus.WIN,
                winAmount,
                playPickNumber.getPlayerSelectedNumber(),
                serverPickedNumber
        );
    }
}
