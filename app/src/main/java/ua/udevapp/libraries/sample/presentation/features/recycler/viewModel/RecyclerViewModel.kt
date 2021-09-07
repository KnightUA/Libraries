package ua.udevapp.libraries.sample.presentation.features.recycler.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ua.udevapp.libraries.core.interactor.UseCase
import ua.udevapp.libraries.sample.domain.interactor.items.GetPhrases
import ua.udevapp.libraries.sample.presentation.features.recycler.model.PhrasesUiState
import javax.inject.Inject

@HiltViewModel
class RecyclerViewModel @Inject constructor(private val getPhrases: GetPhrases) : ViewModel() {

    private val _phrasesUiState =
        MutableStateFlow<PhrasesUiState>(PhrasesUiState.Success(emptyList()))
    val phrasesUiState: StateFlow<PhrasesUiState> = _phrasesUiState

    init {
        fetchPhrases()
    }

    fun fetchPhrases() {
        _phrasesUiState.value = PhrasesUiState.Progress
        getPhrases.invoke(viewModelScope, UseCase.None()) { either ->
            either.fold({ failure ->
                apply {
                    _phrasesUiState.value = PhrasesUiState.Error(failure)
                }
            }, { list ->
                apply {
                    _phrasesUiState.value = PhrasesUiState.Success(list)
                }
            })
        }
    }
}