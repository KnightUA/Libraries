package ua.udevapp.libraries.sample.presentation.features.recycler.model

import ua.udevapp.libraries.core.exceptions.Failure
import ua.udevapp.libraries.sample.data.models.items.Phrase

sealed class PhrasesUiState {
    object Progress : PhrasesUiState()

    data class Success(val phrases: List<Phrase>) : PhrasesUiState()
    data class Error(val failure: Failure) : PhrasesUiState()
}