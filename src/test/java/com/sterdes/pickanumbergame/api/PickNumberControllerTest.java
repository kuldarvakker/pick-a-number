package com.sterdes.pickanumbergame.api;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@SpringBootTest
@AutoConfigureMockMvc
class PickNumberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void itRequiresPlayerId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/pick-a-number/play")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                         
                                         "playerSelectedNumber": 12,
                                         "playerSelectedBet": 12
                                        }
                                        """
                        )
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {
                          "errors": [
                            {
                              "code": "NotNull.playPickNumber.playerId",
                              "arguments": []
                            }
                          ]
                        }
                        """));
    }

    @Test
    public void itRequiresPlayerSelectedNumber() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/pick-a-number/play")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                         "playerId": "0b2489a8-c02b-4e7b-9467-4029c43fbd3f",
                                         
                                         "playerSelectedBet": 12
                                        }
                                        """
                        )
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {
                          "errors": [
                            {
                              "code": "NotNull.playPickNumber.playerSelectedNumber",
                              "arguments": []
                            }
                          ]
                        }
                        """));
    }

    @Test
    public void itValidatesMinimumPlayerSelectedNumber() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/pick-a-number/play")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                         "playerId": "0b2489a8-c02b-4e7b-9467-4029c43fbd3f",
                                         "playerSelectedNumber": 0,
                                         "playerSelectedBet": 12
                                        }
                                        """
                        )
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {
                          "errors": [
                            {
                              "code": "Min.playPickNumber.playerSelectedNumber",
                              "arguments": ["1"]
                            }
                          ]
                        }
                        """));
    }

    @Test
    public void itValidatesMaximumPlayerSelectedNumber() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/pick-a-number/play")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                         "playerId": "0b2489a8-c02b-4e7b-9467-4029c43fbd3f",
                                         "playerSelectedNumber": 102,
                                         "playerSelectedBet": 12
                                        }
                                        """
                        )
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {
                          "errors": [
                            {
                              "code": "Max.playPickNumber.playerSelectedNumber",
                              "arguments": ["100"]
                            }
                          ]
                        }
                        """));
    }

    @Test
    public void itRequiresPlayerPlacedBet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/pick-a-number/play")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                         "playerId": "0b2489a8-c02b-4e7b-9467-4029c43fbd3f",
                                         "playerSelectedNumber": 100
                                         
                                        }
                                        """
                        )
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {
                          "errors": [
                            {
                              "code": "NotNull.playPickNumber.playerSelectedBet",
                              "arguments": []
                            }
                          ]
                        }
                        """));
    }

    @Test
    public void itRequiresPositivePlayerPlacedBet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/pick-a-number/play")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                          "playerId": "0b2489a8-c02b-4e7b-9467-4029c43fbd3f",
                                         "playerSelectedNumber": 100,
                                         "playerSelectedBet": -1
                                        }
                                        """
                        )
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {
                          "errors": [
                            {
                              "code": "Positive.playPickNumber.playerSelectedBet",
                              "arguments": []
                            }
                          ]
                        }
                        """));
    }

    @Test
    public void itValidatesIntegersCountOnPlayerPlacedBet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/pick-a-number/play")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                          "playerId": "0b2489a8-c02b-4e7b-9467-4029c43fbd3f",
                                         "playerSelectedNumber": 100,
                                         "playerSelectedBet": 1234567891234567891234567890
                                        }
                                        """
                        )
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {
                          "errors": [
                            {
                              "code": "Digits.playPickNumber.playerSelectedBet",
                              "arguments": ["2","27"]
                            }
                          ]
                        }
                        """));
    }

    @Test
    public void itValidatesFractionCountOnPlayerPlacedBet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/pick-a-number/play")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                          "playerId": "0b2489a8-c02b-4e7b-9467-4029c43fbd3f",
                                         "playerSelectedNumber": 100,
                                         "playerSelectedBet": 123456789123456789123456789.200
                                        }
                                        """
                        )
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {
                          "errors": [
                            {
                              "code": "Digits.playPickNumber.playerSelectedBet",
                              "arguments": ["2","27"]
                            }
                          ]
                        }
                        """));
    }

    @Test
    public void itSuccessfullyPlaysPickNumberGame() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/pick-a-number/play")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                          "playerId": "0b2489a8-c02b-4e7b-9467-4029c43fbd3f",
                                         "playerSelectedNumber": 50,
                                         "playerSelectedBet": 40.5
                                        }
                                        """
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerId").value("0b2489a8-c02b-4e7b-9467-4029c43fbd3f"))
                .andExpect(jsonPath("$.status").value(Matchers.oneOf("WIN", "LOSE")))
                .andExpect(jsonPath("$.amount").value(Matchers.oneOf(81.0, 40.5)))
                .andExpect(jsonPath("$.playerSelectedNumber").value(50))
                .andExpect(jsonPath("$.computerSelectedNumber").isNumber());
    }
}