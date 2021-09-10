package ua.udevapp.samples.presentation.recycler.adapter.items

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.udevapp.libraries.databinding.ListItemPhraseBinding
import ua.udevapp.magicrecycler.adapter.CollectionRecyclerAdapter
import ua.udevapp.samples.data.models.items.Phrase
import ua.udevapp.samples.presentation.recycler.viewHolder.PhraseViewHolder
import java.lang.ref.WeakReference

class PhrasesRecyclerAdapter : CollectionRecyclerAdapter<Phrase, PhraseViewHolder>() {
    override val _collection: MutableCollection<Phrase> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhraseViewHolder {
        val inflater = WeakReference(LayoutInflater.from(parent.context)).get()
        val binding = ListItemPhraseBinding.inflate(inflater!!, parent, false)
        return PhraseViewHolder(binding)
    }
}