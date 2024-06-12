package com.example.tictactoe.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoe.Model.Player
import com.example.tictactoe.enumeration.BoxStates
import com.example.tictactoe.enumeration.PlayerType

class MainMenuViewModel(
    var SelectedToken: MutableLiveData<BoxStates> = MutableLiveData(BoxStates.X)): ViewModel() {

    public fun generatePlayer():Player{
        if(SelectedToken.value!=null) {
            return Player(PlayerType.player, SelectedToken.value!!)
        }
        return Player(PlayerType.player, BoxStates.X)
    }

    public fun generateBot(player: Player):Player{
        var token = if (player.symbol == BoxStates.X) BoxStates.O else BoxStates.X
        return Player(PlayerType.bot,token)
    }
}