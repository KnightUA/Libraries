package ua.udevapp.magicrecycler.listeners

import android.view.View

/**
 *
 */
fun interface OnItemClickListener<in M> {
    fun onItemClick(item: M, view: View?)
}