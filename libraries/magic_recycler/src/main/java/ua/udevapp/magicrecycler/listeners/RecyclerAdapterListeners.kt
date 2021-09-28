package ua.udevapp.magicrecycler.listeners

/**
 * Main recycler view item listeners
 */
interface RecyclerAdapterListeners<M> {
    /**
     * Represent current item click listener which can be used in viewHolders
     */
    val onItemClickListener: OnItemClickListener<M>?
    /**
     * Represent current item long click listener which can be used in viewHolders
     */
    val onItemLongClickListener: OnItemLongClickListener<M>?

    /**
     * Update current item click listener
     * @param onItemClickListener as new listener for replace previous one
     */
    fun setupOnItemClickListener(onItemClickListener: OnItemClickListener<M>) {}
    /**
     * Update current item long click listener
     * @param onItemLongClickListener as new listener for replace previous one
     */
    fun setupOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener<M>) {}
}