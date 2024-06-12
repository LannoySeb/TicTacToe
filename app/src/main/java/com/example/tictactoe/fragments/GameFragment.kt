package com.example.tictactoe.fragments

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tictactoe.Model.Player
import com.example.tictactoe.R
import com.example.tictactoe.databinding.FragmentGameBinding
import com.example.tictactoe.enumeration.BoxStates
import com.example.tictactoe.enumeration.GameState
import com.example.tictactoe.enumeration.PlayerType
import com.example.tictactoe.viewmodels.GamesViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * fragment that will display the boardGame.
 */
class GameFragment : Fragment() {

    /**
     * Binding for the fragment.
     */
    private lateinit var binding: FragmentGameBinding

    /**
     * Arguments passed by navigation component.
     */
    private val args: GameFragmentArgs by navArgs()

    /**
     * [GamesViewModel] injected by koin.
     */
    val viewModel by viewModel<GamesViewModel>{parametersOf(args.boardSize, args.playerData, args.botData)}


    /**
     * GameBoard as a list of [ImageView]
     */
    private var boardGame = MutableList(0) { ImageView(context) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGameBinding.inflate(inflater, container, false)

        initializeBoardGame()

        binding.resetButton.setOnClickListener{
            resetBoard()
        }

        binding.options.setOnClickListener{
            var filledBox = viewModel.gameBoard.find { box -> box != BoxStates.Empty }
            if(filledBox != null){
                val builder = AlertDialog.Builder(context)
                builder.setTitle(getString(R.string.leave_game_title))
                builder.setMessage(getString(R.string.leave_game_message))
                builder.setPositiveButton(R.string.leave) { dialog, wich ->
                    navigateToMenu()
                }
                builder.setNegativeButton(R.string.stay) { dialog, wich ->
                    dialog.dismiss()
                }
                builder.create().show()
            }
            else{
                navigateToMenu()
            }
        }

        boardGame.forEach{ imageview ->
            imageview.setOnClickListener {
                playTurn(it as ImageView)
            }
        }

        setupLiveDatas()

        return binding.root
    }

    /**
     * Navigate to the [MainMenuFragment]
     */
    private fun navigateToMenu(){
        val navController = findNavController()
       navController.navigate(R.id.mainMenuFragment)
    }

    /**
     * Player place a symbol on a box.
     * @param box [ImageView] that will be updated.
     */
    private fun playTurn(box: ImageView){
        ToggleBox(box, viewModel.activePlayer.value!!.symbol)
        viewModel.playTurn(boardGame.indexOf(box))
    }


    /***
     * Change the icon of an empty box.
     * @param box [ImageView] that will be updated.
     * @param state [BoxStates] that will be set.
     */
    private fun ToggleBox(box:ImageView, state:BoxStates) {
        if(viewModel.gameBoard[boardGame.indexOf(box)] == BoxStates.Empty) {
            when (state) {
                BoxStates.X -> box.setImageResource(R.drawable.x)

                BoxStates.O -> box.setImageResource(R.drawable.o)

                else -> box.setImageResource(0)

            }
        }
    }

    /**
     * Initialize and generate all views needed for the board.
     */
    private fun initializeBoardGame() {

        // generate table rows
        for (row in 0 until viewModel.boardSize) {
            val tableRow = TableRow(context)
            tableRow.id = View.generateViewId()

            // Set layout_weight for vertical distribution
            val rowParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                0
            )
            rowParams.weight = 1.0f
            tableRow.layoutParams = rowParams

            //generates imageview for each column
            for (col in 0 until viewModel.boardSize) {
                val imageView = ImageView(context)
                imageView.id = View.generateViewId()

                val imageParams = TableRow.LayoutParams(
                    0,
                    150
                )
                imageParams.weight = 1.0f
                imageParams.setMargins(5, 5, 5, 5)

                imageView.layoutParams = imageParams
                imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                imageView.setBackgroundColor(Color.GRAY)

                boardGame.add(imageView)
                tableRow.addView(imageView)
            }
            binding.gameBoard.addView(tableRow, rowParams)
        }
        resetBoard()
    }

    /***
     * Reset the board to empty state visually and games data.
     */
    private fun resetBoard(){
        viewModel.resetBoard()
        boardGame.forEach{
            it.setImageResource(0)
        }
    }

    /***
     * Setup of livedata observers.
     */
    private fun setupLiveDatas(){

        // Observer used to check the state of the game.
        val winObserver = Observer<GameState>{ win ->

            // user win
            if(win == GameState.win) {
                val builder = AlertDialog.Builder(context)
                builder.setTitle(getString(R.string.win_title))
                builder.setMessage(getString(R.string.win_message,if (viewModel.activePlayer.value!!.type == PlayerType.player) getString(R.string.Player) else getString(R.string.Bot)))
                builder.setPositiveButton(getString(R.string.replay)) { dialog, wich ->
                    viewModel.resetGame()
                    resetBoard()
                }
                builder.create().show()
            }

            // nobody win
            else if(win == GameState.draw) {
                val builder = AlertDialog.Builder(context)
                builder.setTitle(getString(R.string.draw_title))
                builder.setPositiveButton(getString(R.string.retry)) { dialog, wich ->
                    viewModel.resetGame()
                    resetBoard()
                }
                builder.create().show()
            }
        }
        viewModel.gameState.observe(viewLifecycleOwner, winObserver)

        // Observer used to update the active player.
        val playerObserver = Observer<Player> { player ->
            if (player.type == PlayerType.bot) {
                viewLifecycleOwner.lifecycleScope.launch {
                    toggleImageviewClick(this@GameFragment.binding.gameBoard, false)

                    delay(500)
                    if(viewModel.gameState.value == GameState.playing) {
                        botTurn()
                    }

                    toggleImageviewClick(this@GameFragment.binding.gameBoard, true)
                }
            }
        }

        viewModel.activePlayer.observe(viewLifecycleOwner, playerObserver)
    }

    /**
     * The bot will play on a randomly selected box.
     */
    private fun botTurn(){
        val selectedId = viewModel.getRandomBox()
        if(selectedId != null) {
            boardGame[selectedId].performClick()
        }
    }

    /**
     * Recursively disable image views click in a ViewGroup
     * @param viewGroup [ViewGroup] containing all views that will be enabled or disabled.
     * @param enabled [Boolean] are the views enabled or disabled.
     */
    private fun toggleImageviewClick(viewGroup : ViewGroup, enabled : Boolean) {
        for (i in 0 until viewGroup.childCount) {
            val child = viewGroup.getChildAt(i)
            if (child is ImageView) {
                child.isEnabled = enabled
            } else if (child is ViewGroup) {
                toggleImageviewClick(child, enabled)
            }
        }
    }
}