package ua.udevapp.libraries.recycler.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BinderViewHolder<M>(itemView: View) : RecyclerView.ViewHolder(itemView),
    ModelBinder<M> {
}