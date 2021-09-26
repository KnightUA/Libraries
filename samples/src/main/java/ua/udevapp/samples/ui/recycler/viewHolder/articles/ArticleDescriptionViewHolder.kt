package ua.udevapp.samples.ui.recycler.viewHolder.articles

import android.view.View
import ua.udevapp.libraries.databinding.ItemArticleDescriptionBinding
import ua.udevapp.magicrecycler.viewHolders.BinderViewHolder
import ua.udevapp.samples.data.models.items.Article

class ArticleDescriptionViewHolder(private val binding: ItemArticleDescriptionBinding) :
    BinderViewHolder<Article.Description>(binding.root) {
    override fun bind(model: Article.Description) {
        with(binding) {
            textTitle.text = model.title
            textDescription.text = model.description
        }
    }
}