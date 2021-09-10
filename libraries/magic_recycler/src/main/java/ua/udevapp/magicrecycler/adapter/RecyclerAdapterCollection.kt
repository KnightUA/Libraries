package ua.udevapp.magicrecycler.adapter

import ua.udevapp.magicrecycler.listeners.OnItemClickListener
import ua.udevapp.magicrecycler.listeners.OnItemLongClickListener

interface RecyclerAdapterCollection<M> {
    val _collection: MutableCollection<M>

    val all: Collection<M> get() = _collection
    val isEmpty get() = all.isEmpty()

    var onItemClickListener: OnItemClickListener<M>
    var onItemLongClickListener: OnItemLongClickListener<M>

    fun clear()

    fun addAll(items: Collection<M?>?)
    fun addItem(item: M?)

    fun insertAllAt(items: Collection<M?>?, positionStart: Int)
    fun insertItemAt(position: Int, item: M?)

    fun updateAll(items: Collection<M?>?)
    fun updateItem(item: M?)
    fun updateItemAt(position: Int, item: M?)

    fun removeAll(items: Collection<M?>?)
    fun removeItem(item: M?)
    fun removeItemAt(position: Int)
    fun removeItemRange(range: ClosedRange<Int>) = removeItemRange(range.start, range.endInclusive)
    fun removeItemRange(positionStart: Int, positionEndInclusive: Int)

    fun getItemAt(position: Int): M?
    fun getPositionOf(item: M?): Int

    fun clearAndAddAll(items: Collection<M?>?) {
        clear()
        addAll(items)
    }
}