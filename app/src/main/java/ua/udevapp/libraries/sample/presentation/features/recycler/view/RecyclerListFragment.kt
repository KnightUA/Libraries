package ua.udevapp.libraries.sample.presentation.features.recycler.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ua.udevapp.libraries.R
import ua.udevapp.libraries.core.extensions.collect
import ua.udevapp.libraries.databinding.FragmentRecyclerListBinding
import ua.udevapp.libraries.sample.presentation.features.recycler.model.PhrasesUiState
import ua.udevapp.libraries.sample.presentation.features.recycler.viewModel.RecyclerViewModel

@AndroidEntryPoint
class RecyclerListFragment : Fragment(R.layout.fragment_recycler_list) {
    private val binding by viewBinding(FragmentRecyclerListBinding::bind)
    private val viewModel: RecyclerViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        collectStates()
    }

    private fun initListeners() {
        with(binding) {
            btnRetry.setOnClickListener { viewModel.fetchPhrases() }
        }
    }

    private fun collectStates() {
        with(viewModel) {
            collect(phrasesUiState, ::renderPhrasesState)
        }
    }

    private fun renderPhrasesState(phrasesUiState: PhrasesUiState?) {
        phrasesUiState?.let { state ->
            with(binding) {
                itemsList.isVisible = state is PhrasesUiState.Success
                progress.isVisible = state is PhrasesUiState.Progress
                failureGroup.isVisible = state is PhrasesUiState.Error
            }
        }
    }
}