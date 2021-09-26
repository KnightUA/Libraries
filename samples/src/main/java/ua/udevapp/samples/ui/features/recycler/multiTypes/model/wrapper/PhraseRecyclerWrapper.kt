package ua.udevapp.samples.ui.features.recycler.multiTypes.model.wrapper

import ua.udevapp.libraries.databinding.ItemPhraseBinding
import ua.udevapp.magicrecycler.models.wrapper.ModelRecyclerWrapper
import ua.udevapp.magicrecycler.viewHolders.BinderViewHolder
import ua.udevapp.samples.data.models.items.Phrase
import java.lang.ref.WeakReference

class PhraseRecyclerWrapper(model: Phrase) : ModelRecyclerWrapper<Phrase>(model) {
    override fun bindIn(binderViewHolder: BinderViewHolder<Phrase>) {
        with(binderViewHolder) {
            WeakReference(ItemPhraseBinding.bind(itemView)).get()?.let { binding ->
                with(binding) {
                    textValue.text = model.value
                    textDefinition.text = model.definition

                    root.setupOnItemClickListenerWith(model)
                    root.setupOnItemLongClickListenerWith(model)
                }
            }
        }
    }
}