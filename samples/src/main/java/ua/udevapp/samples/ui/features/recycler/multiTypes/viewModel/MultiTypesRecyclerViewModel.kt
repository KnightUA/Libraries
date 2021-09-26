package ua.udevapp.samples.ui.features.recycler.multiTypes.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ua.udevapp.core.interactor.UseCase
import ua.udevapp.samples.domain.interactor.items.GetArticles
import ua.udevapp.samples.ui.features.recycler.multiTypes.model.ArticlesUiState
import javax.inject.Inject

@HiltViewModel
class MultiTypesRecyclerViewModel @Inject constructor(private val getArticles: GetArticles) :
    ViewModel() {

    private val _articlesUiState =
        MutableStateFlow<ArticlesUiState>(ArticlesUiState.Success(emptyList()))
    val articlesUiState: StateFlow<ArticlesUiState> = _articlesUiState

    init {
        fetchPhrases()
    }

    fun fetchPhrases() {
        _articlesUiState.value = ArticlesUiState.Progress
        getArticles.invoke(viewModelScope, UseCase.None()) { either ->
            either.fold({ failure ->
                apply {
                    _articlesUiState.value = ArticlesUiState.Error(failure)
                }
            }, { list ->
                apply {
                    _articlesUiState.value =
                        ArticlesUiState.Success(list)
                }
            })
        }
    }
}