package com.example.tictactoe.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.tictactoe.R
import com.example.tictactoe.databinding.FragmentMainMenuBinding
import com.example.tictactoe.enumeration.BoxStates
import com.example.tictactoe.viewmodels.GamesViewModel
import com.example.tictactoe.viewmodels.MainMenuViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainMenuFragment : Fragment() {

    val viewModel by viewModel<MainMenuViewModel>()

    private lateinit var binding: FragmentMainMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainMenuBinding.inflate(inflater, container, false)

        setupLiveDataObservers()

        binding.playAsXButton.setOnClickListener {
            viewModel.SelectedToken.value = BoxStates.X
        }

        binding.playAsOButton.setOnClickListener {
            viewModel.SelectedToken.value = BoxStates.O
        }

        binding.boardSizeSlider.addOnChangeListener { _, value, _ ->
            viewModel.boardSize.value = value.toInt()
        }

        binding.playButton.setOnClickListener {
            var player = viewModel.generatePlayer()
            var bot = viewModel.generateBot(player)
            var boardSize = viewModel.boardSize.value
            var action = MainMenuFragmentDirections.actionMainMenuFragmentToGameFragment(player,bot,boardSize!!)

            val navController = findNavController()
            navController.navigate(action)
        }

        return binding.root
    }

    private fun toggleCheckMarks(imageView: ImageView, isVisible:Boolean){
        if(isVisible){
            imageView.visibility = View.VISIBLE
        }
        else{
            imageView.visibility = View.GONE
        }
    }

    private fun setupLiveDataObservers(){
        val selectedTokenObserver = Observer<BoxStates>{token ->
            if(token != null) {
                when (token) {
                    BoxStates.X -> {
                        toggleCheckMarks(binding.xCheckMark, true)
                        toggleCheckMarks(binding.oCheckMark, false)
                    }

                    BoxStates.O  -> {
                        toggleCheckMarks(binding.xCheckMark, false)
                        toggleCheckMarks(binding.oCheckMark, true)
                    }

                    BoxStates.Empty -> {
                        toggleCheckMarks(binding.xCheckMark, true)
                        toggleCheckMarks(binding.oCheckMark, false)
                    }
                }
            }
        }

        viewModel.SelectedToken.observe(viewLifecycleOwner, selectedTokenObserver)

        val boardSizeObserver = Observer<Int>{boardSize ->
            if(boardSize != null){
                binding.boardSizeTextView.text = "Board size: ${boardSize}"
            }
        }
        viewModel.boardSize.observe(viewLifecycleOwner, boardSizeObserver)
    }
}