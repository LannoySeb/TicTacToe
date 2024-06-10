package com.example.tictactoe.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoe.enumeration.BoxStates
import java.util.Dictionary

class GamesViewModel : ViewModel() {

    var gameBoard : MutableList<BoxStates> = mutableListOf(BoxStates.Empty, BoxStates.Empty ,BoxStates.Empty,BoxStates.Empty,BoxStates.Empty,BoxStates.Empty,BoxStates.Empty,BoxStates.Empty,BoxStates.Empty)
    var nextToken : BoxStates = BoxStates.X

    init {
        resetBoard()
    }

    public fun placeToken(place:Int){
        gameBoard[place] = nextToken
        if(nextToken == BoxStates.X){
            nextToken = BoxStates.O
        }
        else{
            nextToken = BoxStates.X
        }
    }

    private fun resetBoard(){
        gameBoard = mutableListOf(BoxStates.Empty, BoxStates.Empty, BoxStates.Empty, BoxStates.Empty, BoxStates.Empty, BoxStates.Empty, BoxStates.Empty, BoxStates.Empty, BoxStates.Empty)
    }
}