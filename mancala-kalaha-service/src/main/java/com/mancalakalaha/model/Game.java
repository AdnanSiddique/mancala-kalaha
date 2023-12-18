package com.mancalakalaha.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mancalakalaha.constant.GameStatus;
import com.mancalakalaha.constant.Player;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    private String id;
    private int[] pits;
    private Player currentPlayer;
    private GameStatus gameStatus;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;
    private List<Move> moves;

    @JsonIgnore
    @Transient
    private int currentPitIndex;

    public Game(int[] pits, Player currentPlayer, GameStatus gameStatus, LocalDateTime createDateTime, List<Move> moves) {
        this.pits = pits;
        this.currentPlayer = currentPlayer;
        this.gameStatus = gameStatus;
        this.moves = moves;
        this.createDateTime = createDateTime;
    }

    @Transient
    public void setNextPlayer() {
        currentPlayer = (currentPlayer == Player.A ? Player.B : Player.A);
    }

    @Transient
    public void setIndividualMove(Move move) {
        this.moves.add(move);
    }
}

