package ua.udevapp.magicrecycler.listeners

import android.view.View

/**
 * Handle simple clicks on views and return item [M]
 */
fun interface OnItemClickListener<in M> {

    /**
     * Triggered when user tap on view
     *
     * @param item is a bound model
     * @param view is a triggered view
     */
    fun onItemClick(item: M, view: View?)
}