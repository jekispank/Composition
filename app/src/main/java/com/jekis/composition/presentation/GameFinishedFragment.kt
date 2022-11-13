package com.jekis.composition.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jekis.composition.R
import com.jekis.composition.databinding.FragmentGameFinishedBinding
import com.jekis.composition.domain.entity.GameResult
import com.jekis.composition.domain.entity.Level

class GameFinishedFragment : Fragment() {
    private val args by navArgs<GameFinishedFragmentArgs>()

    private lateinit var gameFinishedBinding: FragmentGameFinishedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        gameFinishedBinding = FragmentGameFinishedBinding.inflate(inflater)
        return gameFinishedBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameFinishedBinding.gameResult = args.gameResult

        gameFinishedBinding.btTryAgain.setOnClickListener {
            args.gameResult
            retryGame()
        }
    }

    fun retryGame() {
        findNavController().popBackStack()
    }
}