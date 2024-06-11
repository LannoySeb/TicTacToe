package com.example.tictactoe.enumeration

/**
 * Games states of the game session.
 */
enum class GameState {
    /**
     * The game is lost by the player.
     */
    loose,

    /**
     * The game is won by the player.
     */
    win,

    /**
     * The game is a draw.
     */
    draw,

    /**
     * The game is in progress.
     */
    playing,
}