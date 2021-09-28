package ua.udevapp.samples.ui.recycler.viewHolder.articles

import ua.udevapp.libraries.databinding.ItemArticleDescriptionBinding
import ua.udevapp.magicrecycler.listeners.RecyclerAdapterListeners
import ua.udevapp.magicrecycler.viewHolders.BinderViewHolder
import ua.udevapp.samples.data.models.items.Article

class ArticleDescriptionViewHolder(
    private val binding: ItemArticleDescriptionBinding,
    listeners: RecyclerAdapterListeners<Article.Description>?
) :
    BinderViewHolder<Article.Description>(binding.root, listeners) {
    override fun bind(model: Article.Description) {
        with(binding) {
            textTitle.text = model.title
            textDescription.text = model.description

            root.setupOnItemClickListenerWith(model)
        }
    }
}