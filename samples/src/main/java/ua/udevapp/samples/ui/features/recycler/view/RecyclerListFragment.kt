package ua.udevapp.samples.ui.features.recycler.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ua.udevapp.libraries.R
import ua.udevapp.core.extensions.collect
import ua.udevapp.libraries.databinding.FragmentRecyclerListBinding
import ua.udevapp.samples.ui.features.recycler.model.PhrasesUiState
import ua.udevapp.samples.ui.features.recycler.viewModel.RecyclerViewModel
import ua.udevapp.samples.ui.recycler.adapter.items.PhrasesRecyclerAdapter

@AndroidEntryPoint
class RecyclerListFragment : Fragment(R.layout.fragment_recycler_list) {
    private val binding by viewBinding(FragmentRecyclerListBinding::bind)
    private val viewModel: RecyclerViewModel by viewModels()

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        return@lazy PhrasesRecyclerAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        collectStates()
    }

    private fun initViews() {
        with(binding) {
            listItems.adapter = adapter
        }
    }

    private fun initListeners() {
        with(binding) {
            buttonRetry.setOnClickListener { viewModel.fetchPhrases() }
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
                listItems.isVisible = state is PhrasesUiState.Success
                progress.isVisible = state is PhrasesUiState.Progress
                groupFailure.isVisible = state is PhrasesUiState.Error

                if(state is PhrasesUiState.Success) {
                    adapter.updateAll(state.phrases)
                }
            }
        }
    }
}