package com.mancalakalaha.controller.advice;

import com.mancalakalaha.Application;
import com.mancalakalaha.BaseMongoContainerIT;
import com.mancalakalaha.exception.GameNotFoundException;
import com.mancalakalaha.utils.TestHelper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@SpringBootTest
class ControllerExceptionHandlerIT extends BaseMongoContainerIT {

    private final TestHelper testHelper = TestHelper.getInstance();

    @Autowired
    private MockMvc mockMvc;


    @Test
    void handleGameNotFoundException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/game/end?gameId=".concat(testHelper.TEST_GAME_ID)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value("Game not found with id ".concat(testHelper.TEST_GAME_ID)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("BAD_REQUEST"))
                .andDo(print());
    }
}
