package ua.udevapp.magicrecycler.adapter

import androidx.recyclerview.widget.DiffUtil

/**
 * [RecyclerAdapterCollection] helps manipulate with adapter's inner collection without accessing it forward.
 * It includes main operations as getting/removing/replacing/updating/adding data with [M] type
 * Also will apply current notify function to update items on UI
 */
interface RecyclerAdapterCollection<M> {

    /**
     * Mutable collection of [M] type items.
     * Can be replaced by any implementation of [MutableCollection] interface
     *
     * @see MutableCollection as this is interface to manipulate exist [Collection] of data
     */
    val collectionData: MutableCollection<M>

    /**
     * @return Immutable [Collection] of [M] type models
     */
    val all: Collection<M> get() = collectionData

    /**
     * Simple checking existing [Collection] for empty
     */
    val isEmpty get() = all.isEmpty()

    /**
     * Function which checks items by some unique identifier
     */
    val areItemsTheSame: ((newItem: M, existItem: M) -> Boolean)?

    /**
     * Function which checks same items by their content.
     * It is an optional, as by Default will use [equals] operator
     */
    val areContentsTheSame: ((newItem: M, existItem: M) -> Boolean)?

    /**
     * Clear all items in existing [Collection]
     */
    fun clear()

    /**
     * Add [Collection] of items at the end of existing [MutableCollection].
     * Will ignore operation if collection of items is @null
     *
     * @param items which need to add at the end
     */
    fun addAll(items: Collection<M?>?)

    /**
     * Add item at the end of existing [MutableCollection]
     * Will ignore operation if item is @null
     *
     * @param item which need to add at the end
     */
    fun addItem(item: M?)

    /**
     * Insert [Collection] of items at the existing position of existing [MutableCollection]
     *
     * @param positionStart is the existing position in [MutableCollection] from which need to past data.
     * Will do nothing if [positionStart] is out of range
     *
     * @param items which need to insert at the [positionStart].
     * Will ignore operation if collection of items are @null
     */
    fun insertAllAt(items: Collection<M?>?, positionStart: Int)

    /**
     * Insert item at the existing position of existing [MutableCollection]
     *
     * @param position is the existing position in [MutableCollection] where need to past [item].
     * Will do nothing if [position] is out of range
     *
     * @param item which need to insert at the [position].
     * Will ignore operation if item is @null
     */
    fun insertItemAt(position: Int, item: M?)

    /**
     * Replace [Collection] of items in existing [MutableCollection]
     *
     * @param items which need to replace in the collection.
     * Will ignore operation if collection of items are @null
     *
     * Note: if item is not found in the existing collection it will [addItem].
     * Also if there are items which not updated in the existing [Collection] they will be removed by using [removeItem] operation
     */
    fun replaceAll(items: Collection<M?>?)

    /**
     * Update [Collection] of items in existing [MutableCollection]
     *
     * @param items which need to update in the collection.
     * Will ignore operation if collection of items are @null
     *
     * Note: if item is not found in the existing collection it will [addItem]
     */
    fun updateAll(items: Collection<M?>?)

    /**
     * Update item in existing [MutableCollection]. Searching of item will depend on [areItemsTheSame] function.
     * If [areItemsTheSame] not set up, will use simple [getPositionOf] function
     *
     * @param item which need to update in the collection.
     * Will ignore operation if item is @null
     *
     * Note: if item is not found in the existing collection it will [addItem]
     */
    fun updateItem(item: M?)

    /**
     * Update item in existing [MutableCollection]
     *
     * @param item which need to update in the collection.
     * Will ignore operation if item is @null
     *
     * Note: if item is not found in the existing collection it will [addItem]
     */
    fun updateItemAt(position: Int, item: M?)

    /**
     * Remove [Collection] of items from the existing [MutableCollection]
     *
     * @param items which need to remove from the collection.
     * Will ignore operation if collection of items are @null
     */
    fun removeAll(items: Collection<M?>?)

    /**
     * Remove item from the existing [MutableCollection]. For find [item] in collection will be used [getPositionOf]
     *
     * @param item which need to remove from the collection.
     * Will ignore operation if item is @null
     */
    fun removeItem(item: M?)

    /**
     * Remove item from the existing [MutableCollection] at existing position
     *
     * @param position is the existing position in [MutableCollection] which item need to remove from it.
     * Will do nothing if [position] is out of range
     */
    fun removeItemAt(position: Int)

    /**
     * @see #removeItemRange(int, int)
     */
    fun removeItemRange(range: ClosedRange<Int>) = removeItemRange(range.start, range.endInclusive)

    /**
     * Remove range of items from the existing [MutableCollection]
     *
     * @param positionStart is the existing position in [MutableCollection] from which need to start removing items
     * @param positionEndInclusive is the existing position in [MutableCollection] to which need end removing items inclusive
     * Will do nothing if [positionStart] and [positionEndInclusive] is out of range
     */
    fun removeItemRange(positionStart: Int, positionEndInclusive: Int)

    /**
     * Fetch an item from the existing [MutableCollection] by [position]
     *
     * @param position is the existing item position in [MutableCollection]
     * Will return @null if [position] is out of range
     *
     * @return [M] if found item at [position], or @null if not found
     */
    fun getItemAt(position: Int): M?

    /**
     * Fetch the position from the existing [item] in [MutableCollection]
     *
     * @param item is the existing item in [MutableCollection]
     * @return position of existing item or negative value if nothing found. Same for @null item
     */
    fun getPositionOf(item: M?): Int

    /**
     * Fetch an item from the existing [MutableCollection] by [predicate].
     * Used in update operations as [updateItem], [updateAll] and [replaceAll]
     *
     * @param predicate is condition for find correct item in [MutableCollection]
     * @see [DiffUtil.Callback.areItemsTheSame] mandatory must be ID or other field which will be unique and identify item without content
     * @return [M] if found item by [predicate], or negative value if not found
     */
    fun getPositionOfItemBy(lookingItem: M?, predicate: (lookingItem: M, existItem: M) -> Boolean): Int

    /**
     * First clear all data then add new items [Collection]
     *
     * @param items which need to add in the existing collection
     * Will do nothing if items in the collection are @null
     *
     * @see clear which used for remove all data in collection
     * @see addAll which used for adding collection of items in the existing [MutableCollection]
     */
    fun clearAndAddAll(items: Collection<M?>?) {
        clear()
        addAll(items)
    }
}