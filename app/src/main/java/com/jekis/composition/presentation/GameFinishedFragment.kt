package com.jekis.composition.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import com.jekis.composition.R
import com.jekis.composition.databinding.FragmentGameFinishedBinding
import com.jekis.composition.domain.entity.GameResult
import com.jekis.composition.domain.entity.GameSetting
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
    ): View? {
        gameFinishedBinding = FragmentGameFinishedBinding.inflate(inflater)
        return gameFinishedBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseArgs()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    retryGame()
                }
            })
    }

    fun parseArgs() {
        result = requireArguments().getSerializable("key") as GameResult
    }

    fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack("ChooseLevelFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)
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