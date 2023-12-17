package com.mancalakalaha.mapper;

import com.mancalakalaha.dto.GameDto;
import com.mancalakalaha.model.Game;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ModelMapper {

    GameDto gameDtoFrom(Game game);

}
