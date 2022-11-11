package com.jekis.composition.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jekis.composition.R
import com.jekis.composition.data.GameRepositoryImpl
import com.jekis.composition.domain.entity.GameResult
import com.jekis.composition.domain.entity.GameSetting
import com.jekis.composition.domain.entity.Level
import com.jekis.composition.domain.entity.Question
import com.jekis.composition.domain.interactor.GenerateQuestionInteractor
import com.jekis.composition.domain.interactor.GetGameSettingInteractor

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var gameSetting: GameSetting
    private lateinit var level: Level

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String> = _formattedTime

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question> = _question
    private val context = application

    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int> = _percentOfRightAnswers

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String> = _progressAnswers

    private val _enoughCount = MutableLiveData<Boolean>()
    val enoughCount: LiveData<Boolean> = _enoughCount

    private val _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean> = _enoughPercent

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int> = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult> = _gameResult


    private var timer: CountDownTimer? = null

    private val repository = GameRepositoryImpl

    private val generateQuestionInteractor = GenerateQuestionInteractor(repository)
    private val getGameSettingInteractor = GetGameSettingInteractor(repository)

    fun startGame(level: Level) {
        getGameSetting(level)
        startTimer()
        generateQuestion()
        updateProgress()

    }

    private fun getGameSetting(level: Level) {
        this.level = level
        this.gameSetting = getGameSettingInteractor(level)
        _minPercent.value = gameSetting.minPercentOfRightAnswers
    }

    private fun startTimer() {
        timer = object :
            CountDownTimer(gameSetting.gameTimeInSeconds * MILLIS_IN_SECOND, MILLIS_IN_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()

    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            enoughCount.value == true && enoughPercent.value == true,
            countOfRightAnswers,
            countOfQuestions,
            gameSetting
        )

    }

    private fun generateQuestion() {
        _question.value = generateQuestionInteractor(gameSetting.maxSumValue)

    }

    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent
        _progressAnswers.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfRightAnswers,
            gameSetting.minCountOfRightAnswers
        )
        _enoughCount.value = countOfRightAnswers >= gameSetting.minCountOfRightAnswers
        _enoughPercent.value = percent >= gameSetting.minPercentOfRightAnswers
    }

    private fun calculatePercentOfRightAnswers(): Int {
        if (countOfRightAnswers == 0) {
            return 0
        }
        return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }

    private fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLIS_IN_SECOND
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return "$minutes:$leftSeconds"
//        return String().format("%02d:%02d", minutes, leftSeconds)

    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        generateQuestion()
        updateProgress()
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    companion object {
        const val MILLIS_IN_SECOND = 1000L
        const val SECONDS_IN_MINUTES = 60
    }
}