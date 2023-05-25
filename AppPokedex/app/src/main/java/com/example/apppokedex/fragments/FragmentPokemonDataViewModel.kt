package com.example.apppokedex.fragments

import androidx.lifecycle.ViewModel
import com.example.apppokedex.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FragmentPokemonDataViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
): ViewModel() {
    // TODO: Implement the ViewModel
}