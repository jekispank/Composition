package com.jekis.composition.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jekis.composition.R
import com.jekis.composition.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    private lateinit var welcomeBinding: FragmentWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        welcomeBinding = FragmentWelcomeBinding.inflate(inflater)
        return welcomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        welcomeBinding.btUnderstand.setOnClickListener {
            launchChooseLevelFragment()
        }
    }

    private fun launchChooseLevelFragment(){
        findNavController().navigate(R.id.action_welcomeFragment_to_chooseLevelFragment)
    }
}