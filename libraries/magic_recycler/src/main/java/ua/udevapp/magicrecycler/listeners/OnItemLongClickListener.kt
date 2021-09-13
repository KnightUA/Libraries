package ua.udevapp.magicrecycler.listeners

import android.view.View

/**
 * Handle simple long clicks on views and return item [M]
 */
fun interface OnItemLongClickListener<in M> {

    /**
     * Triggered when user long press on view
     *
     * @param item is a bound model
     * @param view is a triggered view
     */
    fun onItemLongClick(item: M, view: View?)
}