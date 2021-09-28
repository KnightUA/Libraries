package ua.udevapp.magicrecycler.extensions

import ua.udevapp.magicrecycler.adapter.MultipleTypesRecyclerAdapter
import ua.udevapp.magicrecycler.listeners.OnItemClickListener
import ua.udevapp.magicrecycler.listeners.OnItemLongClickListener

/**
 * Add new item click listener using class name hashcode
 * @param N type of model which will be provided when view click triggered
 * @see MultipleTypesRecyclerAdapter.addOnItemClickListener
 */
inline fun <reified N> MultipleTypesRecyclerAdapter<*>.addOnItemClickListener(
    onItemClickListener: OnItemClickListener<N>
) where N : Any {
    val key = N::class.simpleName.hashCode()
    addOnItemClickListener(key, onItemClickListener)
}

/**
 * Remove exist item click listener using class name hashcode
 * @param N type of model which has been provided when view click triggered
 * @see MultipleTypesRecyclerAdapter.removeOnItemClickListener
 */
inline fun <reified N> MultipleTypesRecyclerAdapter<*>.removeOnItemClickListener() where N : Any {
    val key = N::class.simpleName.hashCode()
    removeOnItemClickListener(key)
}

/**
 * Add new item long click listener using class name hashcode
 * @param N type of model which will be provided when view long click triggered
 * @see MultipleTypesRecyclerAdapter.addOnItemLongClickListener
 */
inline fun <reified N> MultipleTypesRecyclerAdapter<*>.addOnItemLongClickListener(
    onItemLongClickListener: OnItemLongClickListener<N>
) where N : Any {
    val key = N::class.simpleName.hashCode()
    addOnItemClickListener(key, onItemLongClickListener)
}

/**
 * Remove exist item long click listener using class name hashcode
 * @param N type of model which has been provided when view long click triggered
 * @see MultipleTypesRecyclerAdapter.removeOnLongItemClickListener
 */
inline fun <reified N> MultipleTypesRecyclerAdapter<*>.removeOnLongItemClickListener() where N : Any {
    val key = N::class.simpleName.hashCode()
    removeOnLongItemClickListener(key)
}
