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
import com.example.tictactoe.R
import com.example.tictactoe.databinding.FragmentGameBinding
import com.example.tictactoe.enumeration.BoxStates
import com.example.tictactoe.viewmodels.GamesViewModel
import com.google.android.material.tabs.TabLayout.Tab
import com.google.android.material.tabs.TabLayout.generateViewId
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * fragment that will display the boardGame.
 */
class GameFragment : Fragment() {

    lateinit var binding: FragmentGameBinding

    var boardGame = MutableList(0) { ImageView(context) }

    val viewModel by viewModel<GamesViewModel>()

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

        boardGame.forEach{
            it.setOnClickListener {
                ToggleBox(it as ImageView, viewModel.nextToken)
                viewModel.playTurn(boardGame.indexOf(it))
            }
        }

        setupLiveDatas()

        return binding.root;
    }

    /***
     * Change the icon of an empty box.
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

    private fun initializeBoardGame() {
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
     * Reset the board to empty state.
     */
    private fun resetBoard(){
        viewModel.resetBoard()
        boardGame.forEach{
            it.setImageResource(0)
        }
    }

    /***
     * Setup of lives data observers.
     */
    private fun setupLiveDatas(){
        val winObserver = Observer<Boolean>{ win ->
            if(win) {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("It's a win")
                builder.setPositiveButton("Retry") { dialog, wich ->
                    resetBoard()
                }
                builder.create().show()
            }
    }
        viewModel.win.observe(viewLifecycleOwner, winObserver)
    }

}