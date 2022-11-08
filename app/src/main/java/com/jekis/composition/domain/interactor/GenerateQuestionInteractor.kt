package com.jekis.composition.domain.interactor

import com.jekis.composition.domain.entity.Question
import com.jekis.composition.domain.repository.GameRepository

class GenerateQuestionInteractor(private val repository: GameRepository) {
    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object {
        private const val COUNT_OF_OPTIONS = 6
    }
}