package ua.udevapp.magicrecycler.listeners

import android.view.View

fun interface OnItemLongClickListener<in M> {
    fun onItemLongClick(item: M, view: View?)
}