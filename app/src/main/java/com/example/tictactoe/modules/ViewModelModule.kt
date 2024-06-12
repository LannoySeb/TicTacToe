package com.example.tictactoe.modules

import com.example.tictactoe.viewmodels.GamesViewModel
import com.example.tictactoe.viewmodels.MainMenuViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel


/**
 * Viewmodel injectables by koin.
 */
val ViewModelModule = module {
    viewModel{GamesViewModel()}
    viewModel{MainMenuViewModel()}
}