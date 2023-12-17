package com.mancalakalaha.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mancalakalaha.BaseMongoContainerIT;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void testStartGame() throws Exception {
        // Perform the request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/game/start"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andReturn();
    }

    @Test
    public void testMakeMove() throws Exception {
        // Mock the service response

        MvcResult createdResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/game/start"))
                .andDo(print())
                .andReturn();

        GameDto gameDto = objectMapper.readValue(createdResult.getResponse().getContentAsString(), GameDto.class);


        MoveDto moveDto = testHelper.getMoveDto();
        moveDto.setGameId(gameDto.getId());

        // Perform the request and verify the response
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/game/move")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moveDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andReturn();

    }

    @Test
    public void testEndGame() throws Exception {
        // Perform the request and verify the response

        MvcResult createdResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/game/start"))
                .andDo(print())
                .andReturn();

        GameDto gameDto = objectMapper.readValue(createdResult.getResponse().getContentAsString(), GameDto.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/game/end")
                        .param("gameId", gameDto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(gameDto.getId()))
                .andDo(print());
    }

}
