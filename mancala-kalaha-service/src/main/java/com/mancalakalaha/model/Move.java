package com.mancalakalaha.model;

import com.mancalakalaha.constant.Player;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Move {
    private String id;
    private String gameId;
    private Integer pitIndex;
    private Player player;
    private LocalDateTime createDateTime;
}
