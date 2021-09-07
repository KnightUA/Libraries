package ua.udevapp.libraries.recycler.diffUtils

import androidx.recyclerview.widget.DiffUtil

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

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = kotlin.run {
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

    private fun Collection<D>.getItemAt(position: Int): D? {
        forEachIndexed { index, item ->
            if (position == index) return item
        }

        return null
    }
}