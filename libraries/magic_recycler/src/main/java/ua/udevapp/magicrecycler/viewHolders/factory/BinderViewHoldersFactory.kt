package ua.udevapp.magicrecycler.viewHolders.factory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.udevapp.magicrecycler.listeners.RecyclerAdapterListeners
import ua.udevapp.magicrecycler.viewHolders.BinderViewHolder
import kotlin.reflect.KClass

abstract class BinderViewHoldersFactory {
    abstract val supportedClasses: Collection<KClass<Any>>

    fun create(parent: ViewGroup, viewType: Int): BinderViewHolder<Any> {
        return supportedClasses.find { it.simpleName.hashCode() == viewType }
            ?.let { create(parent, it) }
            ?: error("Can't find viewType with ID $viewType in supported classes ")
    }

    abstract protected fun create( parent: ViewGroup, kClass: KClass<Any>): BinderViewHolder<Any>
}