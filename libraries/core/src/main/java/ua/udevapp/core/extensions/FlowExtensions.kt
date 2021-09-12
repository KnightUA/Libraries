package ua.udevapp.core.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Simplify call from [Fragment] to collect all [Flow] data
 *
 * @param FM is model type which used in [Flow]
 * @param uiState is a flow from which you want to collect data
 * @param handler is a function which will handle state changes
 * @param lifecycleState (optional) - when need to invoke collect function
 * @see repeatOnLifecycle where you find information about lifecycleState usage
 */
inline fun <reified FM> Fragment.collect(
    uiState: Flow<FM>,
    crossinline handler: (FM) -> Unit,
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED
) {
    lifecycleScope.launch {
        repeatOnLifecycle(lifecycleState) {
            uiState.collect { handler.invoke(it) }
        }
    }
}