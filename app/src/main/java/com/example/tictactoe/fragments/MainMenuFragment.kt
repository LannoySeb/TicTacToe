package com.example.tictactoe.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.tictactoe.R
import com.example.tictactoe.databinding.FragmentMainMenuBinding


class MainMenuFragment : Fragment() {

    private lateinit var binding: FragmentMainMenuBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainMenuBinding.inflate(inflater, container, false)

        binding.playButton.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_mainMenuFragment_to_gameFragment)
        }



        return binding.root
    }
}