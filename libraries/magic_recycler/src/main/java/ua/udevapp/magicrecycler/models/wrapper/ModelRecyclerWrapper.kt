package ua.udevapp.magicrecycler.models.wrapper

import android.view.View
import ua.udevapp.magicrecycler.viewHolders.BinderViewHolder

abstract class ModelRecyclerWrapper<M>(val model: M) {
    abstract fun bindIn(binderViewHolder: BinderViewHolder<M>)
}