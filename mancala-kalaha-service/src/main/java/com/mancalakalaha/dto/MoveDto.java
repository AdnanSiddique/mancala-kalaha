package com.mancalakalaha.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.mancalakalaha.constant.Player;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JacksonXmlRootElement(localName = "Move")
public class MoveDto {
    private String gameId;
    private Integer pitIndex;
    private Player player;
    private LocalDateTime createDateTime;
}
