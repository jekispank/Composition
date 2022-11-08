package com.jekis.composition.domain.interactor

import com.jekis.composition.domain.entity.GameSetting
import com.jekis.composition.domain.entity.Level
import com.jekis.composition.domain.repository.GameRepository

class GetGameSettingInteractor(private val repository: GameRepository) {

    operator fun invoke(level: Level): GameSetting {
        return repository.getGameSetting(level)
    }
}