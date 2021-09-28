package ua.udevapp.magicrecycler.adapter

import android.view.View
import timber.log.Timber
import ua.udevapp.magicrecycler.listeners.OnItemClickListener
import ua.udevapp.magicrecycler.listeners.OnItemLongClickListener
import ua.udevapp.magicrecycler.listeners.RecyclerAdapterListeners
import ua.udevapp.magicrecycler.viewHolders.BinderViewHolder

/**
 * The basic implementation of [CollectionRecyclerAdapter].
 * In general all feature adapters must extend this class as it provides needed functionality
 * for base recycler adapter
 */
abstract class SingleRecyclerAdapter<M> : CollectionRecyclerAdapter<M, BinderViewHolder<M>>(),
    RecyclerAdapterListeners<M> where M : Any {

    override val onItemClickListener: OnItemClickListener<M> get() = _onItemClickListener
    private var _onItemClickListener: OnItemClickListener<M> =
        OnItemClickListener { model: M, view: View? -> Timber.d("Item click: model = $model, view = $view") }

    override val onItemLongClickListener: OnItemLongClickListener<M> get() = _onItemLongClickListener
    private var _onItemLongClickListener: OnItemLongClickListener<M> =
        OnItemLongClickListener { model: M, view: View? -> Timber.d("Item long click: model = $model, view = $view") }

    override fun setupOnItemClickListener(onItemClickListener: OnItemClickListener<M>) {
        _onItemClickListener = onItemClickListener
    }

    override fun setupOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener<M>) {
        _onItemLongClickListener = onItemLongClickListener
    }
}