package ua.udevapp.magicrecycler.diffUtils

import androidx.recyclerview.widget.DiffUtil

/**
 * Used in [ItemDiffUtilCallback] for simplify comparing items
 */
interface CompareCollections<in D> {

    /**
     * Update both old and new collections
     *
     * @param old - previous used collection
     * @param new - new or updated collection
     *
     * Note: will setup collection if it is not @null
     */
    fun updateCollections(old: Collection<D>?, new: Collection<D>?) {
        old?.let { updateOldCollection(it) }
        new?.let { updateNewCollection(it) }
    }

    /**
     * Update old collection
     *
     * @param collection which will replace old one
     */
    fun updateOldCollection(collection: Collection<D>)

    /**
     * Update new collection
     *
     * @param collection which will replace new one
     */
    fun updateNewCollection(collection: Collection<D>)

    /**
     * Checking if items are the same. Require to compare the unique identifier
     *
     * @param old is an item from old collection
     * @param new is an item from new collection
     * @return result of comparison by items unique identifier
     *
     * @see [DiffUtil.Callback.areItemsTheSame]
     */
    fun areItemsTheSame(old: D?, new: D?) : Boolean

    /**
     * Checking if item's content are the same.
     * First it will call [areItemsTheSame] and if it is true then it will call this method.
     *
     * @param old is an item from old collection
     * @param new is an item from new collection
     * @return result of comparison by items content
     *
     * @see [DiffUtil.Callback.areContentsTheSame]
     */
    fun areContentsTheSame(old: D?, new: D?) : Boolean
}