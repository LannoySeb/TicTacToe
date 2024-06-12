package com.example.tictactoe.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoe.Model.Player
import com.example.tictactoe.enumeration.BoxStates
import com.example.tictactoe.enumeration.GameState
import com.example.tictactoe.enumeration.PlayerType

/**
 * Logic side of the game.
 *
 * @property boardSize size of the board.
 * @property player player data.
 * @property bot bot data.
 */
class GamesViewModel(
    var boardSize: Int,
    private var player: Player,
    private var bot: Player)
    : ViewModel() {

    /**
     * Game board cells list.
     */
    lateinit var gameBoard : MutableList<BoxStates>

    /**
     * Game current state.
     */
    var gameState : MutableLiveData<GameState> = MutableLiveData(GameState.playing)

    /**
     * Active player.
     */
    var activePlayer: MutableLiveData<Player> = MutableLiveData(player)

    init {
        resetBoard()
    }

    /***
     * Place a token on the board and check if the game is over.
     * @param place index of the board cell where the token will be placed.
     */
    public fun playTurn(place: Int){
        var playerActive = activePlayer.value
        if(playerActive != null) {
            gameBoard[place] = playerActive.symbol

            gameState.postValue(checkWin(playerActive.symbol))

            activePlayer.postValue(if(playerActive.type == PlayerType.player) bot else player)

        }else{
            // TODO handle error
        }
    }

    /**
     * Get a random empty box index.
     *
     * @return index of the empty box or null if no empty box.
     */
    public fun getRandomBox():Int?{

        val emptyIndices = gameBoard.withIndex()
            .filter { it.value == BoxStates.Empty }
            .map { it.index }

        if(emptyIndices.isEmpty())
            return null

        return emptyIndices.random()


    }

    /***
     * Reset the game.
     */
    public fun resetGame() {
        resetBoard()
        activePlayer.postValue(player)
        gameState.postValue(GameState.playing)

    }

    /***
     * Reset the game board cells.
     */
    public fun resetBoard(){
        var gameboardBoxes = boardSize*boardSize
        gameBoard = MutableList(gameboardBoxes, {BoxStates.Empty})
    }

    /***
     * Check winning conditions (horizontal, vertical, diagonal). Then check if the game is draw.
     * @param player player symbol.
     *
     * @return game state after the received data.
     */
    private fun checkWin(player: BoxStates):GameState{
        // Check rows
        for (row in 0 until boardSize) {
            var count = 0
            for (col in 0 until boardSize) {
                if (gameBoard[row * boardSize + col] == player) count++
            }
            if (count == boardSize) return GameState.win
        }

        // Check columns
        for (col in 0 until boardSize) {
            var count = 0
            for (row in 0 until boardSize) {
                if (gameBoard[row * boardSize + col] == player) count++
            }
            if (count == boardSize) return GameState.win
        }

        // Check diagonals
        var countDiagonal1 = 0
        var countDiagonal2 = 0
        for (i in 0 until boardSize) {
            if (gameBoard[i * boardSize + i] == player) countDiagonal1++
            if (gameBoard[i * boardSize + (boardSize - i - 1)] == player) countDiagonal2++
        }
        if (countDiagonal1 == boardSize || countDiagonal2 == boardSize) return GameState.win

        // check draw
        var emptyBoxes = gameBoard.count { it == BoxStates.Empty }

        if(emptyBoxes == 0){
            return GameState.draw
        }
        return GameState.playing
        }
    }