package com.example.tictactoe.Model

import android.os.Parcelable
import com.example.tictactoe.enumeration.BoxStates
import com.example.tictactoe.enumeration.PlayerType
import kotlinx.parcelize.Parcelize

/**
 * Represent a player in the game
 * @param type type of player
 * @param symbol symbol of the player.
 */
@Parcelize
data class Player(
    val type: PlayerType,
    val symbol: BoxStates )
    : Parcelable {
}
