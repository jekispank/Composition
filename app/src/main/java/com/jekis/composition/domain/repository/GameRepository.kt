package com.jekis.composition.domain.repository

import com.jekis.composition.domain.entity.GameSetting
import com.jekis.composition.domain.entity.Level
import com.jekis.composition.domain.entity.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSetting(level: Level): GameSetting
}