package ua.udevapp.magicrecycler.recycler.adapter

import android.view.ViewGroup
import ua.udevapp.magicrecycler.adapter.CollectionRecyclerAdapter
import ua.udevapp.magicrecycler.recycler.viewHolder.MockViewHolder

class MockCollectionRecyclerAdapter :
    CollectionRecyclerAdapter<Pair<Int, String>, MockViewHolder>() {
    override val collectionData: MutableCollection<Pair<Int, String>> = mutableListOf()

    override val areItemsTheSame: ((newItem: Pair<Int, String>, existItem: Pair<Int, String>) -> Boolean)
        get() = { newItem, existItem -> newItem.first == existItem.first }

    override val areContentsTheSame: ((newItem: Pair<Int, String>, existItem: Pair<Int, String>) -> Boolean)
        get() = { newItem, existItem -> newItem.second == existItem.second }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MockViewHolder {
        return MockViewHolder(parent)
    }
}