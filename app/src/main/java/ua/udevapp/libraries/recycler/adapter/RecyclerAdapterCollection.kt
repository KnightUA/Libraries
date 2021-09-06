package ua.udevapp.libraries.recycler.adapter

import ua.udevapp.libraries.recycler.listeners.OnItemClickListener
import ua.udevapp.libraries.recycler.listeners.OnItemLongClickListener
import java.text.FieldPosition

interface RecyclerAdapterCollection<M> {
    val _collection: MutableCollection<M>

    val all: Collection<M> get() = _collection
    val isEmpty get() = all.isEmpty()

    val onItemClickListener: OnItemClickListener<M>
    val onItemLongClickListener: OnItemLongClickListener<M>

    fun setOnItemClickListener(listener: OnItemClickListener<M>)
    fun setOnItemLongClickListener(listener: OnItemClickListener<M>)

    fun clear()

    fun addAll(items: Collection<M?>?)
    fun addItem(item: M?)
    fun addItemAt(position: Int, item: M?)

    fun updateItem(item: M?)
    fun updateItemAt(position: Int, item: M?)

    fun removeItem(item: M?)
    fun removeItemAt(position: Int)

    fun getItemAt(position: Int): M?

    fun clearAndAddAll(items: Collection<M?>?) {
        clear()
        addAll(items)
    }
}