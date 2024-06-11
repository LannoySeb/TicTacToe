package com.example.tictactoe.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoe.enumeration.BoxStates

class GamesViewModel : ViewModel() {

    var boardSize : Int = 3

    lateinit var gameBoard : MutableList<BoxStates>

    var nextToken : BoxStates = BoxStates.X

    var win : MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        resetBoard()
    }

    /***
     * Place a token on the board and check if the game is over.
     */
    public fun playTurn(place:Int){
        gameBoard[place] = nextToken

        win.postValue(checkWin(nextToken))

        if(nextToken == BoxStates.X){
            nextToken = BoxStates.O
        }
        else{
            nextToken = BoxStates.X
        }
    }

    /***
     * Reset the game board data.
     */
    public fun resetBoard(){
        var gameboardBoxes = boardSize*boardSize
        gameBoard = MutableList(gameboardBoxes, {BoxStates.Empty})
    }

    /***
     * Check winning conditions.
     */
    private fun checkWin(player: BoxStates):Boolean{
        // Check rows
        for (row in 0 until boardSize) {
            var count = 0
            for (col in 0 until boardSize) {
                if (gameBoard[row * boardSize + col] == player) count++
            }
            if (count == boardSize) return true
        }

        // Check columns
        for (col in 0 until boardSize) {
            var count = 0
            for (row in 0 until boardSize) {
                if (gameBoard[row * boardSize + col] == player) count++
            }
            if (count == boardSize) return true
        }

        // Check diagonals
        var countDiagonal1 = 0
        var countDiagonal2 = 0
        for (i in 0 until boardSize) {
            if (gameBoard[i * boardSize + i] == player) countDiagonal1++
            if (gameBoard[i * boardSize + (boardSize - i - 1)] == player) countDiagonal2++
        }
        if (countDiagonal1 == boardSize || countDiagonal2 == boardSize) return true

        return false
        }
}