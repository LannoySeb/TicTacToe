package com.example.tictactoe.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tictactoe.R
import com.example.tictactoe.databinding.FragmentGameBinding
import com.example.tictactoe.viewmodels.GamesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * fragment that will display the boardGame.
 */
class GameFragment : Fragment() {

    lateinit var binding: FragmentGameBinding
    val viewModel by viewModel<GamesViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGameBinding.inflate(inflater, container, false)
        if(binding.box3 != null) {
            binding.box3.setImageResource(R.drawable.test)
        }
        return binding.root;
    }
}