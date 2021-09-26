package ua.udevapp.samples.ui.recycler.viewHolder.articles

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import coil.load
import timber.log.Timber
import ua.udevapp.libraries.databinding.ItemArticleDescriptionBinding
import ua.udevapp.libraries.databinding.ItemArticleImageBinding
import ua.udevapp.magicrecycler.viewHolders.BinderViewHolder
import ua.udevapp.samples.data.models.items.Article
import java.io.InputStream
import java.lang.Exception
import java.net.URL

class ArticleImageViewHolder(private val binding: ItemArticleImageBinding) : BinderViewHolder<Article.Image>(binding.root) {
    override fun bind(model: Article.Image) {
        with(binding) {
            imageContent.load(model.imageUrl)
            textTitle.text = model.title
        }
    }
}