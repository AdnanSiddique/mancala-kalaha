package com.mancalakalaha.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mancalakalaha.constant.GameStatus;
import com.mancalakalaha.constant.Player;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
    public Game(int stoneCount, int pitsCount) {
        // making pits for 2 players and including 2 house pit for both players

        pits = new int[(pitsCount * 2) + 2];
        Arrays.fill(pits, 0, pitsCount, stoneCount);
        Arrays.fill(pits, pitsCount + 1, pitsCount * 2 + 1, stoneCount);

        currentPlayer = Player.A;
        gameStatus = GameStatus.STARTED;
        createDateTime = LocalDateTime.now();
        moves = new ArrayList<>();
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

