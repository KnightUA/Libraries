package ua.udevapp.magicrecycler.extensions

import android.view.View
import ua.udevapp.magicrecycler.listeners.OnItemClickListener
import ua.udevapp.magicrecycler.listeners.OnItemLongClickListener
import ua.udevapp.magicrecycler.listeners.RecyclerAdapterListeners

/**
 *
 * Add click listener to adapter
 *
 * @param action is a function to handle click and receive triggered view and bound item
 */
inline fun <M> RecyclerAdapterListeners<M>.doOnItemViewClick(crossinline action: (item: M, view: View?) -> Unit) =
    setItemClickListener(onItemViewClick = action)

/**
 *
 * Add click listener to adapter
 *
 * @param action is a function to handle click and receive only triggered view
 */
inline fun <M> RecyclerAdapterListeners<M>.doOnViewClick(crossinline action: (view: View?) -> Unit) =
    setItemClickListener(onViewClick = action)

/**
 *
 * Add click listener to adapter
 *
 * @param action is a function to handle click and receive only bound item
 */
inline fun <M> RecyclerAdapterListeners<M>.doOnItemClick(crossinline action: (item: M) -> Unit) =
    setItemClickListener(onItemClick = action)

/**
 *
 * General function to add click listeners
 *
 */
inline fun <M> RecyclerAdapterListeners<M>.setItemClickListener(
    crossinline onItemViewClick: (item: M, view: View?) -> Unit = { _, _ -> },
    crossinline onViewClick: (view: View?) -> Unit = { _ -> },
    crossinline onItemClick: (item: M) -> Unit = {},
) {
    onItemClickListener = OnItemClickListener { item, view ->
        onItemViewClick.invoke(item, view)
        onItemClick.invoke(item)
        onViewClick.invoke(view)
    }
}

/**
 *
 * Add click listener to adapter
 *
 * @param action is a function to handle click and receive triggered view and bound item
 */
inline fun <M> RecyclerAdapterListeners<M>.doOnItemViewLongClick(crossinline action: (item: M, view: View?) -> Unit) =
    setItemLongClickListener(onItemViewLongClick = action)

/**
 *
 * Add long click listener to adapter
 *
 * @param action is a function to handle long click and receive only triggered view
 */
inline fun <M> RecyclerAdapterListeners<M>.doOnViewLongClick(crossinline action: (view: View?) -> Unit) =
    setItemLongClickListener(onViewLongClick = action)

/**
 *
 * Add long click listener to adapter
 *
 * @param action is a function to handle long click and receive only bound item
 */
inline fun <M> RecyclerAdapterListeners<M>.doOnItemLongClick(crossinline action: (item: M) -> Unit) =
    setItemLongClickListener(onItemLongClick = action)

/**
 *
 * General function to add long click listeners
 *
 */
inline fun <M> RecyclerAdapterListeners<M>.setItemLongClickListener(
    crossinline onItemViewLongClick: (item: M, view: View?) -> Unit = { _, _ -> },
    crossinline onViewLongClick: (view: View?) -> Unit = { _ -> },
    crossinline onItemLongClick: (item: M) -> Unit = {}
) {

    onItemLongClickListener =
        OnItemLongClickListener { item, view ->
            onItemViewLongClick.invoke(item, view)
            onItemLongClick.invoke(item)
            onViewLongClick.invoke(view)
        }
}