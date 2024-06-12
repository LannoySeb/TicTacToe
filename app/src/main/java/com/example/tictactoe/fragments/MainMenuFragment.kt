package com.example.tictactoe.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.tictactoe.databinding.FragmentMainMenuBinding
import com.example.tictactoe.enumeration.BoxStates
import com.example.tictactoe.viewmodels.MainMenuViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Main menu of the tic tac toe game. Used to set the presets of the game.
 */
class MainMenuFragment : Fragment() {

    /**
     * [MainMenuViewModel] injected by koin.
     */
    private val viewModel by viewModel<MainMenuViewModel>()

    /**
     * Binding for the fragment.
     */
    private lateinit var binding: FragmentMainMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainMenuBinding.inflate(inflater, container, false)

        setupLiveDataObservers()

        binding.playAsXButton.setOnClickListener {
            viewModel.selectedToken.value = BoxStates.X
        }

        binding.playAsOButton.setOnClickListener {
            viewModel.selectedToken.value = BoxStates.O
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

    /**
     * Toggles the visibility of the check marks below the selected token.
     * @param imageView The check mark to swich visibility.
     * @param isVisible The visibility to set.
     */
    private fun toggleCheckMarks(imageView: ImageView, isVisible:Boolean){
        if(isVisible){
            imageView.visibility = View.VISIBLE
        }
        else{
            imageView.visibility = View.GONE
        }
    }

    /**
     * Sets up the observers for the viewModel livedata.
     */
    private fun setupLiveDataObservers(){

        // Observe wich token is selected.
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

                    // In case of bug, reselect x
                    BoxStates.Empty -> {
                        binding.playAsXButton.performClick()
                    }
                }
            }
        }

        viewModel.selectedToken.observe(viewLifecycleOwner, selectedTokenObserver)

        // observe the board size and update the ui
        val boardSizeObserver = Observer<Int>{boardSize ->
            if(boardSize != null){
                binding.boardSizeTextView.text = "Board size: ${boardSize}"
            }
        }
        viewModel.boardSize.observe(viewLifecycleOwner, boardSizeObserver)
    }
}