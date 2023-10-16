package com.sterdes.pickanumbergame.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PickNumberResult {

    private UUID playerId;
    private PickNumberResultStatus status;
    private BigDecimal amount;
    private Integer playerSelectedNumber;
    private Integer computerSelectedNumber;
}
