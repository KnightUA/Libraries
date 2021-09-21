package ua.udevapp.samples.ui.recycler.viewHolder

import ua.udevapp.libraries.databinding.ItemPhraseBinding
import ua.udevapp.magicrecycler.viewHolders.BinderViewHolder
import ua.udevapp.samples.data.models.items.Phrase

class PhraseViewHolder(private val binding: ItemPhraseBinding) :
    BinderViewHolder<Phrase>(binding.root) {
    override fun bind(model: Phrase) {
        with(binding) {
            textValue.text = model.value
            textDefinition.text = model.definition

            root.setupOnItemClickListenerWith(model)
            root.setupOnItemLongClickListenerWith(model)
        }
    }
}