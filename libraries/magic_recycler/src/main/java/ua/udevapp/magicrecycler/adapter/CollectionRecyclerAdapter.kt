package ua.udevapp.magicrecycler.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import ua.udevapp.core.extensions.*
import ua.udevapp.magicrecycler.listeners.OnItemClickListener
import ua.udevapp.magicrecycler.listeners.OnItemLongClickListener
import ua.udevapp.magicrecycler.listeners.RecyclerAdapterListeners
import ua.udevapp.magicrecycler.viewHolders.BinderViewHolder

abstract class CollectionRecyclerAdapter<M, VM> : RecyclerView.Adapter<VM>(),
    RecyclerAdapterListeners<M>,
    RecyclerAdapterCollection<M> where VM : BinderViewHolder<M> {

    override var onItemClickListener: OnItemClickListener<M> =
        OnItemClickListener { model: M, view: View? -> Timber.d("Item click: model = $model, view = $view") }

    override var onItemLongClickListener: OnItemLongClickListener<M> =
        OnItemLongClickListener { model: M, view: View? -> Timber.d("Item long click: model = $model, view = $view") }

    override val areItemsTheSame: ((newItem: M?, existItem: M) -> Boolean)? = null

    override fun getItemCount(): Int {
        return all.size
    }

    override fun onBindViewHolder(holder: VM, position: Int) {
        getItemAt(position)?.let { model -> holder.bind(model) }
    }

    override fun clear() {
        val previousData = collectionData.copy()

        collectionData.clear()

        notifyItemRangeRemoved(START_POSITION, itemCount)

        Timber.d("clear")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $collectionData")
    }

    override fun addAll(items: Collection<M?>?) {
        val safeItems = items.safeCollection()
        val previousData = collectionData.copy()

        collectionData.addAll(safeItems)

        notifyItemRangeInserted(previousData.size, safeItems.size)

        Timber.d("addAll: $items")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $collectionData")
    }

    override fun addItem(item: M?) {
        if (item == null) return Timber.w("Item is Null!")

        val previousData = collectionData.copy()

        collectionData.add(item)

        collectionData.lastPosition?.let { notifyItemInserted(it) }

        Timber.d("addItem: $item")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $collectionData")
    }

    override fun insertAllAt(items: Collection<M?>?, positionStart: Int) {
        val safeItems = items.safeCollection()
        val previousData = collectionData.copy()

        safeItems.forEachIndexed { index, model ->
            collectionData.insertAt(positionStart + index, model)
        }

        notifyItemRangeInserted(positionStart, safeItems.size)

        Timber.d("insertAllAt: $items from position = $positionStart")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $collectionData")
    }

    override fun insertItemAt(position: Int, item: M?) {
        if (item == null) return Timber.w("Item is Null!")

        val previousData = collectionData.copy()

        collectionData.insertAt(position, item)

        notifyItemInserted(position)

        Timber.d("insertItemAt: $position, item = $item")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $collectionData")
    }

    override fun replaceAll(items: Collection<M?>?) {
        val safeItems = items.safeCollection()
        val previousData = collectionData.copy()
        val updatedPositions = mutableSetOf<Int>()

        safeItems.forEach { model ->
            updateItem(model)
            val position = getPositionOf(model)
            if(position > 0) {
                updatedPositions.add(position)
            }
        }

        for(index in collectionData.indices.reversed()) {
            if(index !in updatedPositions)
                removeItemAt(index)
        }

        Timber.d("updatedPositions: $updatedPositions")
        Timber.d("updateAll: $items")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $collectionData")
    }

    override fun updateAll(items: Collection<M?>?) {
        val safeItems = items.safeCollection()
        val previousData = collectionData.copy()

        safeItems.forEach { model ->
            updateItem(model)
        }

        Timber.d("updateAll: $items")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $collectionData")
    }

    override fun updateItem(item: M?) {
        if (item == null) return Timber.w("Item is Null!")

        val positionItem = areItemsTheSame?.let { getPositionOfItemBy(item, it) } ?: getPositionOf(item)
        if (positionItem < 0) {
            Timber.w("Not found position of item = $item")
            return addItem(item)
        }

        Timber.d("updateItem: $item")
        updateItemAt(positionItem, item)
    }

    override fun updateItemAt(position: Int, item: M?) {
        if (item == null) return Timber.w("Item is Null!")

        val previousData = collectionData.copy()

        collectionData.replaceAt(position, item)

        notifyItemChanged(position)

        Timber.d("updateItemAt: $position, item = $item")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $collectionData")
    }

    override fun removeAll(items: Collection<M?>?) {
        val safeItems = items.safeCollection()
        val previousData = collectionData.copy()

        safeItems.forEach { model ->
            removeItem(model)
        }

        Timber.d("removeAll: $items")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $collectionData")
    }

    override fun removeItem(item: M?) {
        if (item == null) return Timber.w("Item is Null!")

        val previousData = collectionData.copy()

        val positionItem = getPositionOf(item)
        if (positionItem < 0) return Timber.w("Not found position of item = $item")

        removeItemAt(positionItem)

        Timber.d("removeItem: $item")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $collectionData")
    }

    override fun removeItemAt(position: Int) {
        val previousData = collectionData.copy()

        collectionData.removeAt(position)

        notifyItemRemoved(position)

        Timber.d("removeItemAt: $position")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $collectionData")
    }

    override fun removeItemRange(positionStart: Int, positionEndInclusive: Int) {
        if (positionStart !in all.indices || positionEndInclusive !in all.indices) return Timber.w("Out of range position: trying to remove items from $positionStart to $positionEndInclusive where ${all.size} size")

        val previousData = collectionData.copy()

        for (position in positionStart..positionEndInclusive) {
            removeItemAt(position)
        }

        Timber.d("removeItemRange: $positionStart to $positionEndInclusive")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $collectionData")
    }

    override fun getItemAt(position: Int): M? {
        if (position !in all.indices) return null.also { Timber.w("Out of range position: trying to get item at $position where ${all.size} size") }
        all.forEachIndexed { index, model ->
            if (index == position) return model
        }

        return null
    }

    override fun getPositionOf(item: M?): Int {
        return all.indexOf(item)
    }

    override fun getPositionOfItemBy(
        lookingItem: M?,
        predicate: (lookingItem: M?, existItem: M) -> Boolean
    ): Int {
        return all.indexOfFirst { predicate.invoke(lookingItem, it) }
    }

    companion object {
        const val START_POSITION = 0
    }
}