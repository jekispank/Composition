package com.jekis.composition.data

import com.jekis.composition.domain.entity.GameSetting
import com.jekis.composition.domain.entity.Level
import com.jekis.composition.domain.entity.Question
import com.jekis.composition.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl: GameRepository {

    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1

    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val options = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        options.add(rightAnswer)
        val from = max(rightAnswer - countOfOptions, MIN_ANSWER_VALUE)
        val to = min(maxSumValue - 1, rightAnswer + countOfOptions)
        while (options.size < countOfOptions) {
            options.add(Random.nextInt(from, to))
        }
        return Question(sum, visibleNumber,options.toList())

    }

    override fun getGameSetting(level: Level): GameSetting {
        return when (level){
            Level.TEST -> {
                GameSetting(
                    10,
                    3,
                    50,
                    8
                )
            }
            Level.EASY -> {
                GameSetting(
                    10,
                    10,
                    70,
                    60
                )
            }
            Level.NORMAL -> {
                GameSetting(
                    20,
                    20,
                    80,
                    40
                )
            }
            Level.HARD -> {
                GameSetting(
                    30,
                    30,
                    90,
                    40
                )
            }
        }
    }
}