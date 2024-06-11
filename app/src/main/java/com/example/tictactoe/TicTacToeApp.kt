package com.example.tictactoe

import android.app.Application
import com.example.tictactoe.modules.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Represent the tic tac toe application.
 */
class TicTacToeApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TicTacToeApp )
            modules(ViewModelModule)
        }
    }
}