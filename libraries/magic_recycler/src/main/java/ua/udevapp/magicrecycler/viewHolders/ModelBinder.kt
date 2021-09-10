package ua.udevapp.magicrecycler.viewHolders

interface ModelBinder<in M> {
    fun bind(model: M)
}