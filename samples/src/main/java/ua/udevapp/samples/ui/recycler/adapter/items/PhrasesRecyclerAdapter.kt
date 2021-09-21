package ua.udevapp.samples.ui.recycler.adapter.items

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.udevapp.libraries.databinding.ItemPhraseBinding
import ua.udevapp.magicrecycler.adapter.CollectionRecyclerAdapter
import ua.udevapp.samples.data.models.items.Phrase
import ua.udevapp.samples.ui.recycler.viewHolder.PhraseViewHolder
import java.lang.ref.WeakReference

class PhrasesRecyclerAdapter : CollectionRecyclerAdapter<Phrase, PhraseViewHolder>() {
    override val collectionData: MutableCollection<Phrase> = mutableListOf()

    override val areItemsTheSame: (newItem: Phrase, existItem: Phrase) -> Boolean
        get() =
            { newItem, existItem -> newItem.id == existItem.id }

    override val areContentsTheSame: (newItem: Phrase, existItem: Phrase) -> Boolean
        get() =
            { newItem, existItem -> newItem == existItem }

    init {
        enableDiffUtil()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhraseViewHolder {
        val inflater = WeakReference(LayoutInflater.from(parent.context)).get()
        val binding = ItemPhraseBinding.inflate(inflater!!, parent, false)
        return PhraseViewHolder(binding)
    }
}