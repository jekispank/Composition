package com.jekis.composition.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import com.jekis.composition.R
import com.jekis.composition.databinding.FragmentGameFinishedBinding
import com.jekis.composition.domain.entity.GameResult
import com.jekis.composition.domain.entity.Level

class GameFinishedFragment : Fragment() {
    private lateinit var result: GameResult
    private lateinit var gameFinishedBinding: FragmentGameFinishedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        gameFinishedBinding = FragmentGameFinishedBinding.inflate(inflater)
        return gameFinishedBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseArgs()
        setParams()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    retryGame()
                }
            })

        gameFinishedBinding.btTryAgain.setOnClickListener {
            retryGame()
        }
    }

    private fun setParams() {
        gameFinishedBinding.apply {
            tvRequiredAnswers.text = context?.resources?.let {
                String.format(
                    it.getString(R.string.necessary_answers),
                    result.gameSetting.minCountOfRightAnswers
                )
            }
            tvScoreAnswers.text = context?.resources?.let {
                String.format(it.getString(R.string.your_count), result.countOfRightAnswers)
            }
            tvRequiredPercentage.text = context?.resources?.let {
                String.format(
                    it.getString(R.string.necessary_percentage),
                    result.gameSetting.minPercentOfRightAnswers
                )
            }
            tvScorePercentage.text = context?.let {
                String.format(
                    it.getString(
                        R.string.percent_true_answers,
                        getPercentOfRightAnswers().toString()
                    )
                )
            }
            emojiResult.setImageResource(getSmileResId())

        }
    }

    private fun getSmileResId(): Int {
        if (result.winner) {
            return R.drawable.ic_smile
        }
        else {
            return R.drawable.ic_sad_lamp
        }

    }

    private fun getPercentOfRightAnswers(): Int {
        return if (result.countOfQuestions == 0) {
            0
        } else {
            ((result.countOfRightAnswers / result.countOfQuestions.toDouble()) * 100).toInt()
        }
    }

    fun parseArgs() {
        requireArguments().getParcelable<GameResult>("key")?.let { result = it }
    }

    fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(
            "ChooseLevelFragment",
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    companion object {
        fun newInstance(gameResult: GameResult): Fragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("key", gameResult)

                }
            }
        }
    }
}