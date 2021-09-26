package ua.udevapp.samples.ui.features.recycler.collection.model

import ua.udevapp.core.exceptions.Failure
import ua.udevapp.samples.data.models.items.Phrase

sealed class PhrasesUiState {
    object Progress : PhrasesUiState()

    data class Success(val phrases: List<Phrase>) : PhrasesUiState()
    data class Error(val failure: Failure) : PhrasesUiState()
}