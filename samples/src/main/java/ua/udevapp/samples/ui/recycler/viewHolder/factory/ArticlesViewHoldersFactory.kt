package ua.udevapp.samples.ui.recycler.viewHolder.factory

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.udevapp.core.extensions.collectionOf
import ua.udevapp.libraries.databinding.ItemArticleDescriptionBinding
import ua.udevapp.libraries.databinding.ItemArticleImageBinding
import ua.udevapp.magicrecycler.listeners.RecyclerAdapterListeners
import ua.udevapp.magicrecycler.viewHolders.BinderViewHolder
import ua.udevapp.magicrecycler.viewHolders.factory.BinderViewHoldersFactory
import ua.udevapp.samples.data.models.items.Article
import ua.udevapp.samples.ui.recycler.viewHolder.articles.ArticleDescriptionViewHolder
import ua.udevapp.samples.ui.recycler.viewHolder.articles.ArticleImageViewHolder
import java.lang.ref.WeakReference
import kotlin.reflect.KClass

class ArticlesViewHoldersFactory : BinderViewHoldersFactory<Article>() {

    override val supportedClasses: Map<Int, KClass<out Article>> = collectionOf(
        Article.Description::class,
        Article.Image::class
    ).associateBy { it.simpleName.hashCode() }

    @Suppress("UNCHECKED CAST")
    override fun create(
        parent: ViewGroup,
        kClass: KClass<out Article>,
        listeners: RecyclerAdapterListeners<out Article>?
    ): BinderViewHolder<out Article> {
        WeakReference(LayoutInflater.from(parent.context)).get()?.let { layoutInflater ->
            return when (kClass) {
                Article.Description::class -> ArticleDescriptionViewHolder(
                    ItemArticleDescriptionBinding.inflate(layoutInflater, parent, false),
                    listeners as? RecyclerAdapterListeners<Article.Description>
                )
                Article.Image::class -> ArticleImageViewHolder(
                    ItemArticleImageBinding.inflate(layoutInflater, parent, false),
                    listeners as? RecyclerAdapterListeners<Article.Image>
                )
                else -> error("Unhandled class ${kClass.simpleName}")
            }
        } ?: throw error("Can't get LayoutInflater from provided context!")
    }
}