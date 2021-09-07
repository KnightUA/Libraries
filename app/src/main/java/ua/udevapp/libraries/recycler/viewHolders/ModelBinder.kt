package ua.udevapp.libraries.recycler.viewHolders

interface ModelBinder<in M> {
    fun bind(model: M)
}