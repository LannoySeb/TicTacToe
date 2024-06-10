package com.example.tictactoe

import android.app.Application
import com.example.tictactoe.modules.ViewModelModule
import org.koin.core.context.startKoin

class TicTacToeApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(ViewModelModule)
        }
    }
}