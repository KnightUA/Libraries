package ua.udevapp.magicrecycler.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import ua.udevapp.magicrecycler.listeners.OnItemClickListener
import ua.udevapp.magicrecycler.listeners.OnItemLongClickListener
import ua.udevapp.magicrecycler.listeners.RecyclerAdapterListeners

/**
 * [RecyclerView.ViewHolder] Abstraction for implementing [ModelBinder] for binding [M] data
 *
 * @param listeners is optional. Used for setup [RecyclerAdapterListeners] by using fields which there declared
 * @param M can be any type as this is a model which will be bind with UI
 */
abstract class BinderViewHolder<M>(
    itemView: View,
    private val listeners: RecyclerAdapterListeners<M>? = null
) : RecyclerView.ViewHolder(itemView),
    ModelBinder<M> {


    /**
     * Safe call for setup [RecyclerAdapterListeners.onItemClickListener] in [BinderViewHolder].
     * It will use [BinderViewHolder.listeners] as default parameter for fetch [OnItemClickListener]
     * @param model is a bound model which used in [BinderViewHolder]
     * @param listener is optional, so it can be replaced by your own implementation
     * @see RecyclerAdapterListeners
     */
    fun View.setupOnItemClickListenerWith(
        model: M,
        listener: OnItemClickListener<M>? = listeners?.onItemClickListener
    ) {
        val safeListener =
            checkNotNull(listener) { Timber.w("Not setup OnItemClickListener") }
        setOnClickListener { safeListener.onItemClick(model, it) }
    }

    /**
     * Safe call for setup [RecyclerAdapterListeners.onItemLongClickListener] in [BinderViewHolder].
     * It will use [BinderViewHolder.listeners] as default parameter for fetch [OnItemLongClickListener]
     * @param model is a bound model which used in [BinderViewHolder]
     * @param listener is optional, so it can be replaced by your own implementation
     * @see RecyclerAdapterListeners
     */
    fun View.setupOnItemLongClickListenerWith(
        model: M,
        listener: OnItemLongClickListener<M>? = listeners?.onItemLongClickListener
    ) {
        val safeListener =
            checkNotNull(listener) { Timber.w("Not setup OnItemLongClickListener") }
        setOnLongClickListener {
            safeListener.onItemLongClick(model, it)
            return@setOnLongClickListener true
        }
    }
}