package com.example.tictactoe.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoe.Model.Player
import com.example.tictactoe.enumeration.BoxStates
import com.example.tictactoe.enumeration.GameState
import com.example.tictactoe.enumeration.PlayerType

class GamesViewModel : ViewModel() {

    var boardSize : Int = 3

    lateinit var gameBoard : MutableList<BoxStates>

    var win : MutableLiveData<GameState> = MutableLiveData(GameState.playing)

    var player: Player = Player(PlayerType.player, BoxStates.X)

    var bot: Player = Player(PlayerType.bot, BoxStates.O)

    var activePlayer: MutableLiveData<Player> = MutableLiveData(player)

    init {
        resetBoard()
    }

    /***
     * Place a token on the board and check if the game is over.
     */
    public fun playTurn(place: Int){
        var playerActive = activePlayer.value
        if(playerActive != null) {
            gameBoard[place] = playerActive.symbol

            win.postValue(checkWin(playerActive.symbol))

            activePlayer.postValue(if(playerActive.type == PlayerType.player) bot else player)

        }else{
            // TODO handle error
        }
    }

    public fun getRandomBox():Int?{

        val emptyIndices = gameBoard.withIndex()
            .filter { it.value == BoxStates.Empty }
            .map { it.index }

        if(emptyIndices.isEmpty())
            return null

        return emptyIndices.random()


    }
    /***
     * Reset the game board data.
     */
    public fun resetBoard(){
        var gameboardBoxes = boardSize*boardSize
        gameBoard = MutableList(gameboardBoxes, {BoxStates.Empty})
    }

    public fun resetGame() {
        resetBoard()
        activePlayer.postValue(player)
        win.postValue(GameState.playing)

    }

    /***
     * Check winning conditions.
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