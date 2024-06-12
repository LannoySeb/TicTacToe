package com.example.tictactoe

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.tictactoe.Model.Player
import com.example.tictactoe.enumeration.BoxStates
import com.example.tictactoe.enumeration.GameState
import com.example.tictactoe.enumeration.PlayerType
import com.example.tictactoe.viewmodels.GamesViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class WinConditionUnitTest{

     @JvmField
     @Rule
      val instantTaskExecutorRule = InstantTaskExecutorRule()

    val player = Player(PlayerType.player, BoxStates.X)
    val bot = Player(PlayerType.bot, BoxStates.O)

    lateinit var viewModel : GamesViewModel
    @Before fun setUp(){

        viewModel = GamesViewModel(3, player,bot)
    }

    @Test fun playTurn_WhenNoWinningCondition_thenReturnPlayingState(){
        viewModel.playTurn(0)

        assert(viewModel.gameState.value == GameState.playing)
    }

    @Test fun playTurn_WhenHorizontalWinningCondition_thenReturnWinState() {
        viewModel.gameBoard.set(0, BoxStates.X)
        viewModel.gameBoard.set(1, BoxStates.X)

        viewModel.playTurn(2)

        assert(viewModel.gameState.value == GameState.win)

    }

    @Test fun playTurn_WhenVerticalWinningCondition_thenReturnWinState(){
        viewModel.gameBoard.set(0, BoxStates.X)
        viewModel.gameBoard.set(3, BoxStates.X)

        viewModel.playTurn(6)

        assert(viewModel.gameState.value == GameState.win)

    }

    @Test fun playTurn_WhenDiagonalWinningCondition_thenReturnWinState(){
        viewModel.gameBoard.set(0, BoxStates.X)
        viewModel.gameBoard.set(4, BoxStates.X)

        viewModel.playTurn(8)

        assert(viewModel.gameState.value == GameState.win)

    }

    @Test fun playTurn_WhenDraw_thenReturnDrawState(){
        viewModel.gameBoard.set(0, BoxStates.O)
        viewModel.gameBoard.set(1, BoxStates.X)
        viewModel.gameBoard.set(2, BoxStates.X)
        viewModel.gameBoard.set(3, BoxStates.X)
        viewModel.gameBoard.set(4, BoxStates.O)
        viewModel.gameBoard.set(5, BoxStates.O)
        viewModel.gameBoard.set(6, BoxStates.X)
        viewModel.gameBoard.set(7, BoxStates.O)

        viewModel.playTurn(8)


        assert(viewModel.gameState.value == GameState.draw)
    }
}