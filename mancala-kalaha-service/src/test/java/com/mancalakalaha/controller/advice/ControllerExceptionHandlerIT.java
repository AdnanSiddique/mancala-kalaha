package com.mancalakalaha.controller.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mancalakalaha.BaseMongoContainerIT;
import com.mancalakalaha.constant.Player;
import com.mancalakalaha.dto.GameDto;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ControllerExceptionHandlerIT extends BaseMongoContainerIT {

    private TestHelper testHelper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        testHelper = TestHelper.getInstance();
    }

    @Test
    void handleGameNotFoundException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/game/end?gameId=".concat(testHelper.TEST_GAME_ID)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Game not found with id ".concat(testHelper.TEST_GAME_ID)))
                .andExpect(jsonPath("$.statusCode").value("BAD_REQUEST"))
                .andDo(print());
    }

    @Test
    void handleInvalidMoveException() throws Exception {
        // Given: Start a new game
        MvcResult createdResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/game/start"))
                .andDo(print())
                .andReturn();

        GameDto gameDto = objectMapper.readValue(createdResult.getResponse().getContentAsString(), GameDto.class);

        // When: Attempt an invalid move
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/game/move")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testHelper.getNextMove(gameDto.getId(), 8, Player.A))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value(String.format("Invalid move for Player %s for pit %s", Player.A.name(), 8)))
                .andExpect(jsonPath("$.statusCode").value("BAD_REQUEST"))
                .andDo(print());
    }
}
