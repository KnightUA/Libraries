package ua.udevapp.magicrecycler.adapter

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import ua.udevapp.core.extensions.*
import ua.udevapp.magicrecycler.diffUtils.ItemDiffUtilCallback
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

    override val areItemsTheSame: ((newItem: M, existItem: M) -> Boolean)? get() = null
    override val areContentsTheSame: ((newItem: M, existItem: M) -> Boolean)? get() = null

    /**
     * Created from [ItemDiffUtilCallback.Builder] as abstraction builder.
     * Depends on [areItemsTheSame], if this function isn't initialized it won't create it and be @null.
     * Also reuse function [areContentsTheSame] if it's initialized too. Otherwise will use [equals] as default behaviour
     * @see ItemDiffUtilCallback
     */
    protected val _itemDiffUtil: ItemDiffUtilCallback<M>? = kotlin.run {
        areItemsTheSame?.let {
            ItemDiffUtilCallback.Builder(areItemsTheSame = it,
                areContentsTheSame = areContentsTheSame
                    ?: { newItem, existItem -> newItem == existItem })
                .build()
        }
    }

    /**
     * Will use diff utils callback if this property is true.
     * Note: this doesn't mean that it will be used, as [_itemDiffUtil] can be @null
     */
    protected var hasDiffUtilEnabled: Boolean = false

    override fun getItemCount(): Int {
        return all.size
    }

    override fun onBindViewHolder(holder: VM, position: Int) {
        getItemAt(position)?.let { model -> holder.bind(model) }
    }

    private fun tryUseDiffUtilWith(previousData: Collection<M>): Unit? {
        return tryUseDiffUtilWith(previousData, collectionData.copy())
    }

    private fun tryUseDiffUtilWith(previousData: Collection<M>, newData: Collection<M>): Unit? {
        if (!hasDiffUtilEnabled) return null

        return _itemDiffUtil?.let { diffUtilCallback ->
            collectionData.clear()
            collectionData.addAll(newData)

            diffUtilCallback.updateCollections(previousData, collectionData)
            val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
            diffResult.dispatchUpdatesTo(this)
        }
    }

    override fun clear() {
        val previousData = collectionData.copy()

        collectionData.clear()

        tryUseDiffUtilWith(previousData) ?: notifyItemRangeRemoved(START_POSITION, itemCount)

        Timber.d("clear")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $collectionData")
    }

    override fun addAll(items: Collection<M?>?) {
        val safeItems = items.safeCollection()
        val previousData = collectionData.copy()

        collectionData.addAll(safeItems)

        tryUseDiffUtilWith(previousData) ?: notifyItemRangeInserted(
            previousData.size,
            safeItems.size
        )

        Timber.d("addAll: $items")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $collectionData")
    }

    override fun addItem(item: M?) {
        if (item == null) return Timber.w("Item is Null!")

        val previousData = collectionData.copy()

        collectionData.add(item)

        tryUseDiffUtilWith(previousData)
            ?: collectionData.lastPosition?.let { notifyItemInserted(it) }

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

        tryUseDiffUtilWith(previousData) ?: notifyItemRangeInserted(positionStart, safeItems.size)

        Timber.d("insertAllAt: $items from position = $positionStart")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $collectionData")
    }

    override fun insertItemAt(position: Int, item: M?) {
        if (item == null) return Timber.w("Item is Null!")

        val previousData = collectionData.copy()

        collectionData.insertAt(position, item)

        tryUseDiffUtilWith(previousData) ?: notifyItemInserted(position)

        Timber.d("insertItemAt: $position, item = $item")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $collectionData")
    }

    override fun replaceAll(items: Collection<M?>?) {
        val safeItems = items.safeCollection()
        val previousData = collectionData.copy()

        tryUseDiffUtilWith(previousData, safeItems) ?: run {
            val updatedPositions = mutableSetOf<Int>()

            safeItems.forEach { model ->
                updateItem(model)
                val position = getPositionOf(model)
                if (position > 0) {
                    updatedPositions.add(position)
                }
            }

            Timber.d("updatedPositions: $updatedPositions")

            for (index in collectionData.indices.reversed()) {
                if (index !in updatedPositions)
                    removeItemAt(index)
            }
        }

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

        val positionItem =
            areItemsTheSame?.let { getPositionOfItemBy(item, it) } ?: getPositionOf(item)
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

        tryUseDiffUtilWith(previousData) ?: notifyItemChanged(position)

        Timber.d("updateItemAt: $position, item = $item")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $collectionData")
    }

    override fun removeAll(items: Collection<M?>?) {
        val safeItems = items.safeCollection()
        val previousData = collectionData.copy()
        val updatedData = collectionData.toMutableCollection().apply {
            safeItems.forEach { item ->
                val positionItem = getPositionOf(item)
                if (positionItem > 0) removeAt(positionItem)
            }
        }

        tryUseDiffUtilWith(previousData, updatedData) ?: run {
            safeItems.forEach { model ->
                removeItem(model)
            }
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

        tryUseDiffUtilWith(previousData) ?: notifyItemRemoved(position)

        Timber.d("removeItemAt: $position")
        Timber.d("previousData: $previousData")
        Timber.d("currentData: $collectionData")
    }

    override fun removeItemRange(positionStart: Int, positionEndInclusive: Int) {
        if (positionStart !in all.indices || positionEndInclusive !in all.indices) return Timber.w("Out of range position: trying to remove items from $positionStart to $positionEndInclusive where ${all.size} size")

        val previousData = collectionData.copy()
        val updatedData = collectionData.toMutableCollection().apply {
            for (position in positionEndInclusive..positionStart) {
                removeAt(position)
            }
        }

        tryUseDiffUtilWith(previousData, updatedData) ?: run {
            for (position in positionEndInclusive..positionStart) {
                removeItemAt(position)
            }
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
        predicate: (lookingItem: M, existItem: M) -> Boolean
    ): Int {
        if (lookingItem == null) return NO_POSITION
        return all.indexOfFirst { predicate.invoke(lookingItem, it) }
    }

    /**
     * Enable usage of diff utils, if it is already has been setup
     * @see hasDiffUtilEnabled for more info
     */
    fun enableDiffUtil() {
        hasDiffUtilEnabled = true
    }

    /**
     * Enable usage of diff utils, if it is already has been setup
     * @see hasDiffUtilEnabled for more info
     */
    fun disableDiffUtil() {
        hasDiffUtilEnabled = false
    }

    companion object {
        const val START_POSITION = 0
        const val NO_POSITION = -1
    }
}