package com.sterdes.pickanumbergame.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PlayPickNumber {

    @NotNull
    private UUID playerId;
    @NotNull
    @Min(1)
    @Max(100)
    private Integer playerSelectedNumber;
    @NotNull
    @Positive
    @Digits(integer = 27, fraction = 2)
    private BigDecimal playerSelectedBet;
}
