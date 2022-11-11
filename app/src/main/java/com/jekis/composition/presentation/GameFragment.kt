package com.jekis.composition.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jekis.composition.R
import com.jekis.composition.databinding.FragmentGameBinding
import com.jekis.composition.domain.entity.GameResult
import com.jekis.composition.domain.entity.Level

class GameFragment : Fragment() {
    private lateinit var level: Level
    private lateinit var gameBinding: FragmentGameBinding
    private val gameViewModel: GameViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[GameViewModel::class.java]
    }
    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(gameBinding.tvOption1)
            add(gameBinding.tvOption2)
            add(gameBinding.tvOption3)
            add(gameBinding.tvOption4)
            add(gameBinding.tvOption5)
            add(gameBinding.tvOption6)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        gameBinding = FragmentGameBinding.inflate(inflater)
        return gameBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        gameViewModel.startGame(level)
        setClickToOptions()
    }

    private fun observeViewModel() {
        gameViewModel.question.observe(viewLifecycleOwner, Observer {
            gameBinding.tvSum.text = it.sum.toString()
            gameBinding.tvLeftNumber.text = it.visibleNumber.toString()

            for (i in 0 until tvOptions.size) {
                tvOptions[i].text = it.options[i].toString()
            }
        })

        gameViewModel.percentOfRightAnswers.observe(viewLifecycleOwner, Observer {
            gameBinding.progressBar.setProgress(it, true)
        })

        gameViewModel.enoughCount.observe(viewLifecycleOwner, Observer {
            gameBinding.tvAnswersProgress.setTextColor(getColorByState(it))
        })

        gameViewModel.enoughPercent.observe(viewLifecycleOwner, Observer {
            val color = getColorByState(it)
            gameBinding.progressBar.progressTintList = ColorStateList.valueOf(color)
        })

        gameViewModel.formattedTime.observe(viewLifecycleOwner, Observer{
            gameBinding.tvTimer.text= it
        })

        gameViewModel.minPercent.observe(viewLifecycleOwner, Observer {
            gameBinding.progressBar.secondaryProgress = it
        })

        gameViewModel.gameResult.observe(viewLifecycleOwner, Observer {
            launchGameFinishedFragment(it)
        })

        gameViewModel.progressAnswers.observe(viewLifecycleOwner, Observer {
            gameBinding.tvAnswersProgress.text = it
        })
    }

    private fun getColorByState(goodState: Boolean): Int {
        val colorResId = if (goodState){
            android.R.color.holo_green_light
        }
        else {
            android.R.color.holo_red_light
        }

        return ContextCompat.getColor(requireContext(), colorResId)
    }

    fun parseArgs() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let { level = it }
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    private fun setClickToOptions(){
        for (tvOption in tvOptions) {
            tvOption.setOnClickListener {
                gameViewModel.chooseAnswer(tvOption.text.toString().toInt())
            }
        }
    }

    companion object {

        private const val KEY_LEVEL = "level"
        fun newInstance(level: Level): Fragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)

                }
            }
        }
    }
}
