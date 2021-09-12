package ua.udevapp.magicrecycler.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * [RecyclerView.ViewHolder] Abstraction for implementing [ModelBinder] for binding [M] data
 *
 * @param M can be any type as this is a model which will be bind with UI
 */
abstract class BinderViewHolder<M>(itemView: View) : RecyclerView.ViewHolder(itemView),
    ModelBinder<M> {
}