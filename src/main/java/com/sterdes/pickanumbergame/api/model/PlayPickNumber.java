package com.sterdes.pickanumbergame.api.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PlayPickNumber {

    private UUID playerId;
    private Integer playerSelectedNumber;
    private BigDecimal playerSelectedBet;
}
