package com.example.tictactoe.Model

import android.os.Parcelable
import com.example.tictactoe.enumeration.BoxStates
import com.example.tictactoe.enumeration.PlayerType
import kotlinx.parcelize.Parcelize

@Parcelize
data class Player(val type: PlayerType, val symbol: BoxStates ) : Parcelable {
}
