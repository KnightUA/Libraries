package ua.udevapp.magicrecycler.viewHolders.factory

import android.view.ViewGroup
import ua.udevapp.magicrecycler.listeners.RecyclerAdapterListeners
import ua.udevapp.magicrecycler.viewHolders.BinderViewHolder
import kotlin.reflect.KClass

/**
 * Factory which provides [BinderViewHolder] for specified supported model types in [supportedClasses]
 */
abstract class BinderViewHoldersFactory<M> where M : Any {
    abstract val supportedClasses: Map<Int, KClass<out M>>

    /**
     * Fetch kotlin class by viewType as a key
     * @param viewType as the key in the map
     * @return KClass for specified viewType in the [supportedClasses] map
     */
    @Suppress("UNCHECKED_CAST")
    fun fetchClassForViewType(viewType: Int): KClass<out M> {
        return supportedClasses[viewType]
            ?: error("Can't find viewType with ID $viewType in supported classes ")
    }

    /**
     * Create a new [BinderViewHolder] for generic [M] type
     * @param parent which used for bind viewHolder to the root and fetch layoutInflater
     * @param kClass which used to create state machine for different subtypes of [M] type
     * @param listeners as an optional for setup them in [BinderViewHolder]
     * @return new instance of [BinderViewHolder]
     */
    abstract fun create(
        parent: ViewGroup,
        kClass: KClass<out M>,
        listeners: RecyclerAdapterListeners<out M>?
    ): BinderViewHolder<out M>
}