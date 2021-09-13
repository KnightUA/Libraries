package ua.udevapp.magicrecycler.diffUtils

import androidx.recyclerview.widget.DiffUtil
import ua.udevapp.core.extensions.emptyCollection
import ua.udevapp.core.extensions.getItemAt

abstract class ItemDiffUtilCallback<in D> : DiffUtil.Callback, CompareCollections<D> {
    private val _oldCollection: MutableCollection<D>
    private val _newCollection: MutableCollection<D>

    constructor() {
        _oldCollection = mutableListOf()
        _newCollection = mutableListOf()
    }

    constructor(oldCollection: MutableCollection<D>, newCollection: MutableCollection<D>) {
        _oldCollection = oldCollection
        _newCollection = newCollection
    }

    override fun getOldListSize(): Int = _oldCollection.size
    override fun getNewListSize(): Int = _newCollection.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = kotlin.run {
        val oldItem = _oldCollection.getItemAt(oldItemPosition)
        val newItem = _newCollection.getItemAt(newItemPosition)
        return@run areItemsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        kotlin.run {
            val oldItem = _oldCollection.getItemAt(oldItemPosition)
            val newItem = _newCollection.getItemAt(newItemPosition)
            return@run areContentsTheSame(oldItem, newItem)
        }

    override fun updateOldCollection(collection: Collection<D>) {
        _oldCollection.clear()
        _oldCollection.addAll(collection)
    }

    override fun updateNewCollection(collection: Collection<D>) {
        _newCollection.clear()
        _newCollection.addAll(collection)
    }

    data class Builder<M>(
        val areItemsTheSame: (newItem: M, existItem: M) -> Boolean,
        val areContentsTheSame: (newItem: M, existItem: M) -> Boolean,
        var oldCollection: Collection<M> = emptyCollection(),
        var newCollection: Collection<M> = emptyCollection()
    ) {
        fun oldCollection(collection: Collection<M>) =
            apply { this.oldCollection = collection }

        fun newCollection(collection: Collection<M>) =
            apply { this.newCollection = collection }

        fun build() = object : ItemDiffUtilCallback<M>() {
            override fun areItemsTheSame(old: M?, new: M?): Boolean {
                if(old == null || new == null) return false
                return areItemsTheSame.invoke(old, new)
            }

            override fun areContentsTheSame(old: M?, new: M?): Boolean {
                if(old == null || new == null) return false
                return areContentsTheSame.invoke(old, new)
            }
        }.apply {
            updateNewCollection(newCollection)
            updateOldCollection(oldCollection)
        }
    }
}