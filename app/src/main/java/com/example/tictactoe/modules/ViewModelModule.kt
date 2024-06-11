package com.example.tictactoe.modules

import com.example.tictactoe.viewmodels.GamesViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel


/**
 * Viewmodel injectables by koin.
 */
val ViewModelModule = module {
    viewModel{GamesViewModel()}
}