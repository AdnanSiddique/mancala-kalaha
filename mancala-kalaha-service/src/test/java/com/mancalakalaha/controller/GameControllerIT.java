package com.mancalakalaha.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mancalakalaha.BaseMongoContainerIT;
import com.mancalakalaha.constant.GameStatus;
import com.mancalakalaha.constant.Player;
import com.mancalakalaha.dto.GameDto;
import com.mancalakalaha.dto.MoveDto;
import com.mancalakalaha.utils.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class GameControllerIT extends BaseMongoContainerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private TestHelper testHelper;

    @BeforeEach
    public void init() {
        testHelper = TestHelper.getInstance();
    }

    @Test
    public void givenNoGameStarted_whenStartGame_thenExpectNewGameCreated() throws Exception {
        // Perform the request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/game/start"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())  // Example: Assert that the response contains an "id" field
                .andDo(print())
                .andReturn();
    }

    @Test
    public void givenNewGame_whenMakeMove_thenExpectCreated() throws Exception {
        // Setup: Start a new game
        MvcResult createdResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/game/start"))
                .andDo(print())
                .andReturn();

        GameDto gameDto = objectMapper.readValue(createdResult.getResponse().getContentAsString(), GameDto.class);
        MoveDto moveDto = testHelper.getNextMove(gameDto.getId(), 1 , Player.A);

        // Perform the request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/game/move")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moveDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())  // Example: Assert that the response contains an "id" field
                .andDo(print())
                .andReturn();
    }


    @Test
    public void givenStartedGame_whenEndGame_thenExpectGameEndedSuccessfully() throws Exception {
        // Given: Start a new game
        MvcResult createdResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/game/start"))
                .andDo(print())
                .andReturn();

        GameDto gameDto = objectMapper.readValue(createdResult.getResponse().getContentAsString(), GameDto.class);

        // When: End the game
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/game/end")
                        .param("gameId", gameDto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(gameDto.getId()))
                .andDo(print());
    }

    @Test
    public void givenStartedGame_whenCompleteGame_thenGameStatusCompleted() throws Exception {
        // Given: Start a new game
        MvcResult createdResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/game/start"))
                .andDo(print())
                .andReturn();

        GameDto gameDto = objectMapper.readValue(createdResult.getResponse().getContentAsString(), GameDto.class);
        String gameId = gameDto.getId();
        // When: Perform a sequence of moves
        performMovesInSequence(
                testHelper.getNextMove(gameId, 0, Player.A),
                testHelper.getNextMove(gameId, 1, Player.A),
                testHelper.getNextMove(gameId, 5, Player.B),
                testHelper.getNextMove(gameId, 0, Player.A),
                testHelper.getNextMove(gameId, 8, Player.B),
                testHelper.getNextMove(gameId, 1, Player.A),
                testHelper.getNextMove(gameId, 6, Player.B),
                testHelper.getNextMove(gameId, 0, Player.A),
                testHelper.getNextMove(gameId, 8, Player.B),
                testHelper.getNextMove(gameId, 7, Player.B),
                testHelper.getNextMove(gameId, 1, Player.A));

        // Then: Verify that the game status is COMPLETED
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/game/move")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testHelper.getNextMove(gameId, 8, Player.B))))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.gameStatus").exists())
                .andExpect(jsonPath("$.gameStatus").value(GameStatus.COMPLETED.name()))
                .andDo(print())
                .andReturn();
    }

    private void performMovesInSequence(MoveDto... moves) throws Exception {
        for (MoveDto move : moves) {
            mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/game/move")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(move)));
        }
    }
}
