package ua.udevapp.magicrecycler.recycler.viewHolder

import android.view.View
import timber.log.Timber
import ua.udevapp.magicrecycler.viewHolders.BinderViewHolder

class MockViewHolder(itemView: View) : BinderViewHolder<Pair<Int, String>>(itemView) {
    override fun bind(model: Pair<Int, String>) {
        Timber.d("bind: $model")
    }
}