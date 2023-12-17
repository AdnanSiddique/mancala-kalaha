package com.mancalakalaha.config;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties("manacala-kalaha.game")
public class GameConfig {

    @NotNull
    private int pitCount;

    @NotNull
    private int stoneCount;

    public int getLeftHouse() {
        return Math.addExact(Math.multiplyExact(pitCount, 2), 1);
    }

    public int getRightHouse() {
        return pitCount;
    }

    public int getTotalPit() {
        return Math.addExact(Math.multiplyExact(pitCount, 2), 1);
    }
}
