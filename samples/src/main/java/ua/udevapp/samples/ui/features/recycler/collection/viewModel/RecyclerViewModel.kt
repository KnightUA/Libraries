package ua.udevapp.samples.ui.features.recycler.collection.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ua.udevapp.core.interactor.UseCase
import ua.udevapp.samples.data.models.items.Phrase
import ua.udevapp.samples.domain.interactor.items.GetPhrases
import ua.udevapp.samples.ui.features.recycler.collection.model.PhrasesUiState
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
                    replacePhrases()
                }
            })
        }
    }

    fun replacePhrases() {
        viewModelScope.launch {
            delay(7_500L)
            _phrasesUiState.value = PhrasesUiState.Success(phrasesForReplace)
        }
    }

    companion object {
        val phrasesForReplace = listOf(
            Phrase(
                id = 3,
                value = "v2: Jaws of Death",
                definition = "v2: Being in a dangerous or very deadly situation"
            ),
            Phrase(
                id = 4,
                value = "v2: Cut The Mustard",
                definition = "v2: To cut the mustard is to meet a required standard, or to meet expectations"
            ),
            Phrase(
                id = 5,
                value = "v2: Hard Pill to Swallow",
                definition = "v2: Something that's difficult to accept"
            ),
        )
    }
}