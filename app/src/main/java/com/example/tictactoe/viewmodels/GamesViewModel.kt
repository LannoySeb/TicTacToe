package com.example.tictactoe.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoe.enumeration.BoxStates
import java.util.Dictionary

class GamesViewModel : ViewModel() {

    var gameBoard : MutableList<BoxStates> = mutableListOf(BoxStates.Empty, BoxStates.Empty ,BoxStates.Empty,BoxStates.Empty,BoxStates.Empty,BoxStates.Empty,BoxStates.Empty,BoxStates.Empty,BoxStates.Empty)

    var nextToken : BoxStates = BoxStates.X

    var win : MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        resetBoard()
    }

    public fun placeToken(place:Int){
        gameBoard[place] = nextToken

checkWin()

        if(nextToken == BoxStates.X){
            nextToken = BoxStates.O
        }
        else{
            nextToken = BoxStates.X
        }
    }

    public fun resetBoard(){
        gameBoard = mutableListOf(BoxStates.Empty, BoxStates.Empty, BoxStates.Empty, BoxStates.Empty, BoxStates.Empty, BoxStates.Empty, BoxStates.Empty, BoxStates.Empty, BoxStates.Empty)
    }

    private fun checkWin(){
        if(checkWinHorizontal() || checkWinVertical() || checkWinDiagonal()){
            win.postValue(true)
        }
    }
    private fun checkWinHorizontal():Boolean{
        for (i in 0..2){
            if(gameBoard[i]!= BoxStates.Empty && gameBoard[i] == gameBoard[i+1] && gameBoard[i] == gameBoard[i+2]){
                return true
            }
        }
        return false
    }

    private fun checkWinVertical():Boolean{
        for (i in 0..2){
            if(gameBoard[i]!= BoxStates.Empty && gameBoard[i] == gameBoard[i+3] && gameBoard[i] == gameBoard[i+6]){
                return true
            }
        }
        return false
    }

    private fun checkWinDiagonal():Boolean{
        if(gameBoard[4]!= BoxStates.Empty){
            if(gameBoard[0] == gameBoard[4] && gameBoard[0] == gameBoard[8]){
                return true
            }
            if(gameBoard[2] == gameBoard[4] && gameBoard[2] == gameBoard[6]){
                return true
            }
        }
        return false
    }
}