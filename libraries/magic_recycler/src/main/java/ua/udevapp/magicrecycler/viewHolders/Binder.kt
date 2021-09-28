package ua.udevapp.magicrecycler.viewHolders

/**
 * Interface which allow binding class with some model
 *
 * @param M is a model type which using for binding
 */
interface Binder<in M> {

    /**
     *
     * Bind current class with [M] data
     *
     *  @param model can be any type with which you want to bind data
     */
    fun bind(model: M)
}