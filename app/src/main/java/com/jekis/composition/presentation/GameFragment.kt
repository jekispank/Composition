package com.jekis.composition.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jekis.composition.R
import com.jekis.composition.databinding.FragmentGameBinding
import com.jekis.composition.domain.entity.GameResult
import com.jekis.composition.domain.entity.GameSetting
import com.jekis.composition.domain.entity.Level

class GameFragment : Fragment() {
    private lateinit var level: Level
    private lateinit var gameBinding: FragmentGameBinding

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

        gameBinding.tvQuestion.setOnClickListener {
            val gameSetting = GameSetting(11, 3, 35, 59)
            val gameResult = GameResult(true, 10, 15, gameSetting)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, GameFinishedFragment.newInstance(gameResult))
                .addToBackStack(null)
                .commit()
        }
    }

    fun parseArgs() {
        level = requireArguments().getSerializable(KEY_LEVEL) as Level
    }

    companion object{

        private const val KEY_LEVEL = "level"
        fun newInstance(level: Level): Fragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_LEVEL, level)

                }
            }
        }
    }
}
