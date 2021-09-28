package ua.udevapp.magicrecycler.adapter

import android.view.ViewGroup
import ua.udevapp.magicrecycler.listeners.OnItemClickListener
import ua.udevapp.magicrecycler.listeners.OnItemLongClickListener
import ua.udevapp.magicrecycler.listeners.RecyclerAdapterListeners
import ua.udevapp.magicrecycler.viewHolders.BinderViewHolder
import ua.udevapp.magicrecycler.viewHolders.factory.BinderViewHoldersFactory
import kotlin.reflect.KClass

/**
 * Generic class which support multiple types of views. For use it correctly is need to setup [BinderViewHoldersFactory]
 * or implement own logic for create [BinderViewHolder] in [createViewHolderByViewType]
 */
open class MultipleTypesRecyclerAdapter<M> :
    CollectionRecyclerAdapter<M, BinderViewHolder<M>> where M : Any {

    /**
     * Used for create specific [BinderViewHolder] for any model. Must be specified in constructor as parameter or
     * can be ignored in case when [createViewHolderByViewType] has benn overridden by own logic
     */
    protected val viewHoldersFactory: BinderViewHoldersFactory<M>?

    /**
     * Represent current list of item click listeners which can be used in viewHolders
     */
    protected val _onItemClickListeners = mutableMapOf<Int, OnItemClickListener<*>>()
    val onItemClickListeners: Map<Int, OnItemClickListener<*>> = _onItemClickListeners

    /**
     * Represent current list of item long click listeners which can be used in viewHolders
     */
    protected val _onItemLongClickListeners = mutableMapOf<Int, OnItemLongClickListener<*>>()
    val onItemLongClickListeners: Map<Int, OnItemLongClickListener<*>> = _onItemLongClickListeners

    /**
     * Be careful with that constructor as [viewHoldersFactory] will be @null
     */
    constructor() : super() {
        this.viewHoldersFactory = null
    }

    /**
     * This constructor is required, but can be ignored in case own implementation of [onCreateViewHolder]
     */
    constructor(viewHoldersFactory: BinderViewHoldersFactory<M>) {
        this.viewHoldersFactory = viewHoldersFactory
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BinderViewHolder<M> {
        return createViewHolderByViewType(parent, viewType) as BinderViewHolder<M>
    }

    override fun getItemViewType(position: Int): Int {
        return getItemAt(position)?.let { model -> getItemViewTypeFrom(model) }
            ?: throw error("Can't fetch item at $position position!")
    }

    /**
     * Here can be defined which viewType ID can be received from specified model
     * @param model from which must fetched unique id as viewType
     * @return viewType identifier for model. By default return hashcode of class name
     */
    open fun getItemViewTypeFrom(model: M): Int = model::class.simpleName.hashCode()

    /**
     * Used in [onCreateViewHolder] for fetch specific viewHolder for provided viewType
     * @param parent as viewGroup which is used for create [BinderViewHolder]
     * @param viewType unique identifier of model, or used to recognize which viewHolder need to create
     * @return a new instance for [BinderViewHolder]
     * @throws error where [viewHoldersFactory] doesn't setup properly
     */
    protected open fun createViewHolderByViewType(
        parent: ViewGroup,
        viewType: Int
    ): BinderViewHolder<out M> {
        if (viewHoldersFactory == null) throw error("viewHoldersFactory doesn't setup properly or is missing!")
        val kClass = viewHoldersFactory.fetchClassForViewType(viewType)
        return viewHoldersFactory.create(
            parent,
            kClass,
            fetchSpecificListeners(kClass)
        )
    }

    /**
     * Add or replace by [key] new item click listener in the [_onItemClickListeners]
     * @param key unique identifier of listener
     * @param onItemClickListener a new listener
     */
    fun addOnItemClickListener(key: Int, onItemClickListener: OnItemClickListener<*>) {
        _onItemClickListeners[key] = onItemClickListener
    }

    /**
     * Remove exist click listener by [key] from the [_onItemClickListeners]
     * @param key unique identifier of listener
     */
    fun removeOnItemClickListener(key: Int) {
        _onItemClickListeners.remove(key)
    }

    /**
     * Add or replace by [key] new item long click listener in the [_onItemLongClickListeners]
     * @param key unique identifier of listener
     * @param onItemLongClickListener a new listener
     */
    fun addOnItemClickListener(key: Int, onItemLongClickListener: OnItemLongClickListener<*>) {
        _onItemLongClickListeners[key] = onItemLongClickListener
    }

    /**
     * Remove exist long click listener by [key] from the [_onItemLongClickListeners]
     * @param key unique identifier of listener
     */
    fun removeOnLongItemClickListener(key: Int) {
        _onItemLongClickListeners.remove(key)
    }

    /**
     * Used for fetch from [_onItemClickListeners] correct listener with [kClass] type
     */
    @Suppress("UNCHECKED_CAST")
    protected fun <N> fetchSpecificOnItemClickListener(kClass: KClass<*>): OnItemClickListener<N>? where N : M {
        val key = kClass.simpleName.hashCode()
        return _onItemClickListeners[key] as? OnItemClickListener<N>
    }

    /**
     * Used for fetch from [_onItemLongClickListeners] correct listener with [kClass] type
     */
    @Suppress("UNCHECKED_CAST")
    protected fun <N> fetchSpecificOnItemLongClickListener(kClass: KClass<*>): OnItemLongClickListener<N>? where N : M {
        val key = kClass.simpleName.hashCode()
        return _onItemLongClickListeners[key] as? OnItemLongClickListener<N>
    }

    /**
     * Used for fetch group of listeners with [kClass] type. It guarantee that it won't be @null, but
     * Note that inner listeners can be @null.
     */
    protected fun <N> fetchSpecificListeners(kClass: KClass<N>): RecyclerAdapterListeners<N> where N : M {
        return object : RecyclerAdapterListeners<N> {
            override val onItemClickListener: OnItemClickListener<N>? =
                fetchSpecificOnItemClickListener(kClass)
            override val onItemLongClickListener: OnItemLongClickListener<N>? =
                fetchSpecificOnItemLongClickListener(kClass)
        }
    }
}