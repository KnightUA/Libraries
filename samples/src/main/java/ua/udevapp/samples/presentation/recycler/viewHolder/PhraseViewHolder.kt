package ua.udevapp.samples.presentation.recycler.viewHolder

import ua.udevapp.libraries.databinding.ListItemPhraseBinding
import ua.udevapp.magicrecycler.viewHolders.BinderViewHolder
import ua.udevapp.samples.data.models.items.Phrase

class PhraseViewHolder(private val binding: ListItemPhraseBinding) :
    BinderViewHolder<Phrase>(binding.root) {
    override fun bind(model: Phrase) {
        with(binding) {
            tvValue.text = model.value
            tvDefinition.text = model.definition
        }
    }
}