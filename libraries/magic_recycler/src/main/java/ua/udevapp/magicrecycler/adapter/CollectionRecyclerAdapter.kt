package ua.udevapp.magicrecycler.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import ua.udevapp.core.extensions.*
import ua.udevapp.magicrecycler.listeners.OnItemClickListener
import ua.udevapp.magicrecycler.listeners.OnItemLongClickListener
import ua.udevapp.magicrecycler.viewHolders.BinderViewHolder

abstract class CollectionRecyclerAdapter<M, VM> : RecyclerView.Adapter<VM>(),
    RecyclerAdapterCollection<M> where VM : BinderViewHolder<M> {

    override var onItemClickListener: OnItemClickListener<M> =
        OnItemClickListener { model: M, view: View? -> Timber.d("Item click: model = $model, view = $view") }

    override var onItemLongClickListener: OnItemLongClickListener<M> =
        OnItemLongClickListener { model: M, view: View? -> Timber.d("Item long click: model = $model, view = $view") }

    override fun getItemCount(): Int {
        return all.size
    }

    override fun onBindViewHolder(holder: VM, position: Int) {
        getItemAt(position)?.let { model -> holder.bind(model) }
    }

    override fun clear() {
        val previousData = _collection.copy()

        _collection.clear()

        notifyItemRangeRemoved(START_POSITION, itemCount)

        Timber.d("clear")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $_collection")
    }

    override fun addAll(items: Collection<M?>?) {
        val safeItems = items.safeCollection()
        val previousData = _collection.copy()

        _collection.addAll(safeItems)

        notifyItemRangeInserted(previousData.size, safeItems.size)

        Timber.d("addAll: $items")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $_collection")
    }

    override fun addItem(item: M?) {
        if (item == null) return Timber.w("Item is Null!")

        val previousData = _collection.copy()

        _collection.add(item)

        _collection.lastPosition?.let { notifyItemInserted(it) }

        Timber.d("addItem: $item")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $_collection")
    }

    override fun insertAllAt(items: Collection<M?>?, positionStart: Int) {
        val safeItems = items.safeCollection()
        val previousData = _collection.copy()

        safeItems.forEachIndexed { index, model ->
            _collection.insertAt(positionStart + index, model)
        }

        notifyItemRangeInserted(positionStart, safeItems.size)

        Timber.d("insertAllAt: $items from position = $positionStart")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $_collection")
    }

    override fun insertItemAt(position: Int, item: M?) {
        if (item == null) return Timber.w("Item is Null!")

        val previousData = _collection.copy()

        _collection.insertAt(position, item)

        notifyItemInserted(position)

        Timber.d("insertItemAt: $position, item = $item")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $_collection")
    }

    override fun updateAll(items: Collection<M?>?) {
        val safeItems = items.safeCollection()
        val previousData = _collection.copy()

        safeItems.forEach { model ->
            updateItem(model)
        }

        Timber.d("updateAll: $items")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $_collection")
    }

    override fun updateItem(item: M?) {
        if (item == null) return Timber.w("Item is Null!")

        val positionItem = getPositionOf(item)
        if (positionItem < 0) {
            Timber.w("Not found position of item = $item")
            return addItem(item)
        }

        Timber.d("updateItem: $item")
        updateItemAt(positionItem, item)
    }

    override fun updateItemAt(position: Int, item: M?) {
        if (item == null) return Timber.w("Item is Null!")

        val previousData = _collection.copy()

        _collection.replaceAt(position, item)

        notifyItemChanged(position)

        Timber.d("updateItemAt: $position, item = $item")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $_collection")
    }

    override fun removeAll(items: Collection<M?>?) {
        val safeItems = items.safeCollection()
        val previousData = _collection.copy()

        safeItems.forEach { model ->
            removeItem(model)
        }

        Timber.d("removeAll: $items")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $_collection")
    }

    override fun removeItem(item: M?) {
        if (item == null) return Timber.w("Item is Null!")

        val previousData = _collection.copy()

        val positionItem = getPositionOf(item)
        if (positionItem < 0) return Timber.w("Not found position of item = $item")

        removeItemAt(positionItem)

        Timber.d("removeItem: $item")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $_collection")
    }

    override fun removeItemAt(position: Int) {
        val previousData = _collection.copy()

        _collection.removeAt(position)

        notifyItemRemoved(position)

        Timber.d("removeItemAt: $position")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $_collection")
    }

    override fun removeItemRange(positionStart: Int, positionEndInclusive: Int) {
        if (positionStart !in all.indices || positionEndInclusive !in all.indices) return Timber.w("Out of range position: trying to remove items from $positionStart to $positionEndInclusive where ${all.size} size")

        val previousData = _collection.copy()

        for (position in positionStart..positionEndInclusive) {
            removeItemAt(position)
        }

        Timber.d("removeItemRange: $positionStart to $positionEndInclusive")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $_collection")
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

    companion object {
        const val START_POSITION = 0
    }
}