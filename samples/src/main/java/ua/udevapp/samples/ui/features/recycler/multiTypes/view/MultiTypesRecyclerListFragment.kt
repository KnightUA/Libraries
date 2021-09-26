package ua.udevapp.samples.ui.features.recycler.multiTypes.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import ua.udevapp.libraries.R
import ua.udevapp.core.extensions.collect
import ua.udevapp.libraries.databinding.FragmentRecyclerListBinding
import ua.udevapp.magicrecycler.adapter.MultiTypesRecyclerAdapter
import ua.udevapp.magicrecycler.extensions.setItemClickListener
import ua.udevapp.magicrecycler.extensions.setItemLongClickListener
import ua.udevapp.samples.ui.features.recycler.multiTypes.model.ArticlesUiState
import ua.udevapp.samples.ui.features.recycler.multiTypes.viewModel.MultiTypesRecyclerViewModel
import ua.udevapp.samples.ui.recycler.viewHolder.factory.ArticlesViewHoldersFactory

@AndroidEntryPoint
class MultiTypesRecyclerListFragment : Fragment(R.layout.fragment_recycler_list) {
    private val binding by viewBinding(FragmentRecyclerListBinding::bind)
    private val recyclerViewModel: MultiTypesRecyclerViewModel by viewModels()

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        return@lazy MultiTypesRecyclerAdapter(ArticlesViewHoldersFactory()).apply {
            setItemClickListener { Timber.i("Click on item $it") }
            setItemLongClickListener { Timber.i("Long click on item $it") }
        }
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
            buttonRetry.setOnClickListener { recyclerViewModel.fetchPhrases() }
        }
    }

    private fun collectStates() {
        with(recyclerViewModel) {
            collect(articlesUiState, ::renderArticlesState)
        }
    }

    private fun renderArticlesState(phrasesUiState: ArticlesUiState?) {
        phrasesUiState?.let { state ->
            with(binding) {
                listItems.isVisible = state is ArticlesUiState.Success
                progress.isVisible = state is ArticlesUiState.Progress
                groupFailure.isVisible = state is ArticlesUiState.Error

                if(state is ArticlesUiState.Success) {
                    adapter.updateAll(state.articles)
                }
            }
        }
    }
}