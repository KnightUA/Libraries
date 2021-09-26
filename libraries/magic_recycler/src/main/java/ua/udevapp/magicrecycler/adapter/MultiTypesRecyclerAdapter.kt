package ua.udevapp.magicrecycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.udevapp.magicrecycler.viewHolders.BinderViewHolder
import ua.udevapp.magicrecycler.viewHolders.factory.BinderViewHoldersFactory
import java.lang.ref.WeakReference

open class MultiTypesRecyclerAdapter(
    protected val viewHoldersFactory: BinderViewHoldersFactory
) :
    CollectionRecyclerAdapter<Any, BinderViewHolder<Any>>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BinderViewHolder<Any> {
        return createViewHolderByViewType(parent, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        return getItemAt(position)?.let { model ->
            model::class.simpleName.hashCode()
        }
            ?: throw error("Unknown or invalid model at $position position!")
    }

    protected open fun createViewHolderByViewType(
        parent: ViewGroup,
        viewType: Int
    ): BinderViewHolder<Any> {
        return viewHoldersFactory.create(parent, viewType)
    }
}