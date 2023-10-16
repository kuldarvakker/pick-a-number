package com.sterdes.pickanumbergame.command;

import com.sterdes.pickanumbergame.api.model.PickNumberResult;
import com.sterdes.pickanumbergame.api.model.PickNumberResultStatus;
import com.sterdes.pickanumbergame.api.model.PlayPickNumber;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("unit")
@SpringBootTest
class PlayPickNumberCommandRTPTest {

    @Autowired
    private PlayPickNumberCommand playPickNumberCommand;

    ExecutorService executorService;

    @BeforeEach
    void beforeEach() {
        executorService = Executors.newFixedThreadPool(24);
    }

    @AfterEach
    void AfterEach() throws InterruptedException {
        executorService.shutdown();
    }

    @Test
    void itSuccessfullyHasCloseTo99PercentRTP() throws InterruptedException {
        var countOfGames = 1_000_000;

        var results = new ArrayList<PickNumberResult>();
        var playPickNumber = new PlayPickNumber(
                UUID.fromString("0b2489a8-c02b-4e7b-9467-4029c43fbd3f"),
                50,
                BigDecimal.ONE
        );
        List<Callable<Integer>> callables = new ArrayList<>();
        for (int i = 0; i < countOfGames; i++) {
            int callableIdentifier = i;
            callables.add(() -> {
                        var result = playPickNumberCommand.execute(playPickNumber);
                        synchronized (this) {
                            results.add(result);
                        }
                        return callableIdentifier;
                    }
            );
        }

        executorService.invokeAll(callables);

        var winAmount = results.stream()
                .filter(it -> it.getStatus() == PickNumberResultStatus.WIN)
                .map(PickNumberResult::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertEquals(countOfGames, results.size());
        assertThat(winAmount.doubleValue(), closeTo(990_000));
    }

    private Matcher<Double> closeTo(int value) {
        double precision = 1999;

        return Matchers.closeTo(value, precision);
    }
}