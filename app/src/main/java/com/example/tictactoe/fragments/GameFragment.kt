package com.example.tictactoe.fragments

import android.app.AlertDialog
import android.graphics.Color
import android.media.Image.Plane
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import com.example.tictactoe.R
import com.example.tictactoe.databinding.FragmentGameBinding
import com.example.tictactoe.enumeration.BoxStates
import com.example.tictactoe.viewmodels.GamesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * fragment that will display the boardGame.
 */
class GameFragment : Fragment() {

    lateinit var binding: FragmentGameBinding
    var boardGame = emptyList<ImageView>()
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

        boardGame = listOf(binding.box0, binding.box1, binding.box2, binding.box3, binding.box4, binding.box5, binding.box6, binding.box7, binding.box8)

        binding.resetButton.setOnClickListener{
            ResetBoard()
        }
        boardGame.forEach{
            it.setOnClickListener {
                ToggleBox(it as ImageView, viewModel.nextToken)
                viewModel.placeToken(boardGame.indexOf(it))
            }
        }

        setupLiveDatas()

        return binding.root;
    }

    private fun ToggleBox(box:ImageView, state:BoxStates) {
        if(viewModel.gameBoard[boardGame.indexOf(box)] == BoxStates.Empty)
        when (state) {
            BoxStates.X -> box.setImageResource(R.drawable.x)

            BoxStates.O -> box.setImageResource(R.drawable.o)

            else -> box.setImageResource(0)

        }
    }

    private fun ResetBoard(){
        viewModel.resetBoard()
        boardGame.forEach{
            it.setImageResource(0)
        }
    }

    private fun setupLiveDatas(){
        val winObserver = Observer<Boolean>{ win ->
            if(win) {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("It's a win")
                builder.setPositiveButton("Retry") { dialog, wich ->
                    ResetBoard()
                }
                builder.create().show()
            }
    }
        viewModel.win.observe(viewLifecycleOwner, winObserver)
    }

}