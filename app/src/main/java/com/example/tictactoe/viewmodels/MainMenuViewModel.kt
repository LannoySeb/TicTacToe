package com.example.tictactoe.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoe.Model.Player
import com.example.tictactoe.enumeration.BoxStates
import com.example.tictactoe.enumeration.PlayerType

/**
 * Main menu viewModel. Store data for the game presets.
 * @param selectedToken MutableLiveData<BoxStates> that contains the selected token for the player.
 */
class MainMenuViewModel(
    var selectedToken: MutableLiveData<BoxStates> = MutableLiveData(BoxStates.X)
) : ViewModel() {

    /**
     * MutableLiveData<Int> that contains the board size selected by the user.
     */
    var boardSize: MutableLiveData<Int> = MutableLiveData(3)

    /**
     * Generate a player object based on the selected token.
     */
    public fun generatePlayer(): Player {
        if (selectedToken.value != null) {
            return Player(PlayerType.player, selectedToken.value!!)
        }
        return Player(PlayerType.player, BoxStates.X)
    }

    /**
     * Generate a bot player based on player.
     * @param player Player data.
     */
    public fun generateBot(player: Player): Player {
        var token = if (player.symbol == BoxStates.X) BoxStates.O else BoxStates.X
        return Player(PlayerType.bot, token)
    }
}