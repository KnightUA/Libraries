package ua.udevapp.magicrecycler.listeners

/**
 * Main recycler view item listeners
 */
interface RecyclerAdapterListeners<M> {
    var onItemClickListener: OnItemClickListener<M>
    var onItemLongClickListener: OnItemLongClickListener<M>
}