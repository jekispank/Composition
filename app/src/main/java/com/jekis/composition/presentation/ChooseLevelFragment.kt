package com.jekis.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jekis.composition.R
import com.jekis.composition.databinding.FragmentChooseLevelBinding
import com.jekis.composition.domain.entity.Level

class ChooseLevelFragment : Fragment() {
    private lateinit var chooseLevelBinding: FragmentChooseLevelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        chooseLevelBinding = FragmentChooseLevelBinding.inflate(inflater)
        return chooseLevelBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(chooseLevelBinding) {
            buttonLevelEasy.setOnClickListener {
                launchGameFragment(Level.EASY)
            }
            buttonLevelMiddle.setOnClickListener {
                launchGameFragment(Level.NORMAL)
            }
            buttonLevelHard.setOnClickListener {
                launchGameFragment(Level.HARD)
            }
            buttonLevelTest.setOnClickListener {
                launchGameFragment(Level.TEST)
            }
        }
    }

    private fun launchGameFragment(level: Level) {
            findNavController().navigate(ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(level))
    }
}