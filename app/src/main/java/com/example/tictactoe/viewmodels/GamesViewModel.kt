package com.example.tictactoe.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoe.enumeration.BoxStates
import java.util.Dictionary

class GamesViewModel : ViewModel() {
    var gameBoard : MutableLiveData<List<BoxStates>> = MutableLiveData(emptyList())

    init {
        resetBoard()
    }
    private fun resetBoard(){
        gameBoard.value = listOf(BoxStates.Empty, BoxStates.Empty, BoxStates.Empty, BoxStates.Empty, BoxStates.Empty, BoxStates.Empty, BoxStates.Empty, BoxStates.Empty, BoxStates.Empty)
    }
}