package com.mancalakalaha.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.mancalakalaha.constant.GameStatus;
import com.mancalakalaha.constant.Player;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@JacksonXmlRootElement(localName = "Game")
public class GameDto {
    private String id;
    private int[] pits;
    private Player currentPlayer;
    private GameStatus gameStatus;
    private List<MoveDto> moves;
}

