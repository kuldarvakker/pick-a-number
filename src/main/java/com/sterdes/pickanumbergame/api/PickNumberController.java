package com.sterdes.pickanumbergame.api;

import com.sterdes.pickanumbergame.command.PlayPickNumberCommand;
import com.sterdes.pickanumbergame.api.model.PickNumberResult;
import com.sterdes.pickanumbergame.api.model.PlayPickNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/pick-a-number")
@RequiredArgsConstructor
public class PickNumberController {

    private final PlayPickNumberCommand playPickNumberCommand;

    @PostMapping("/play")
    public ResponseEntity<PickNumberResult> playPickNumberGame(@RequestBody @Valid PlayPickNumber playPickNumber) {
        var result = playPickNumberCommand.execute(playPickNumber);

        return new ResponseEntity<PickNumberResult>(result, HttpStatus.OK);
    }
}
