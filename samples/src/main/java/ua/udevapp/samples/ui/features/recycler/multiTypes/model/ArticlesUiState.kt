package ua.udevapp.samples.ui.features.recycler.multiTypes.model

import ua.udevapp.core.exceptions.Failure
import ua.udevapp.samples.data.models.items.Article

sealed class ArticlesUiState {
    object Progress : ArticlesUiState()

    data class Success(val articles: List<Article>) : ArticlesUiState()
    data class Error(val failure: Failure) : ArticlesUiState()
}