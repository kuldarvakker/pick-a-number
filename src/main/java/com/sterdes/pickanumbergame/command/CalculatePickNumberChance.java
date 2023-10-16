package com.sterdes.pickanumbergame.command;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CalculatePickNumberChance {

    public static BigDecimal execute(BigDecimal betAmount, Integer playerPickedNumber) {
        // chance: = bet * (99 / (100 - number))
        return betAmount
                .multiply(new BigDecimal("99")
                        .divide(
                                new BigDecimal("100")
                                        .subtract(
                                                BigDecimal.valueOf(playerPickedNumber)
                                        ),
                                RoundingMode.HALF_UP
                        )
                );
    }
}
