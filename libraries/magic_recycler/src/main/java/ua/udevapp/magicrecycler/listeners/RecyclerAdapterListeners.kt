package ua.udevapp.magicrecycler.listeners

interface RecyclerAdapterListeners<M> {
    var onItemClickListener: OnItemClickListener<M>
    var onItemLongClickListener: OnItemLongClickListener<M>
}