package ua.udevapp.samples.ui.recycler.viewHolder.factory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.udevapp.core.extensions.mutableCollectionOf
import ua.udevapp.libraries.databinding.ItemArticleDescriptionBinding
import ua.udevapp.libraries.databinding.ItemArticleImageBinding
import ua.udevapp.magicrecycler.viewHolders.BinderViewHolder
import ua.udevapp.magicrecycler.viewHolders.factory.BinderViewHoldersFactory
import ua.udevapp.samples.data.models.items.Article
import ua.udevapp.samples.ui.recycler.viewHolder.articles.ArticleDescriptionViewHolder
import ua.udevapp.samples.ui.recycler.viewHolder.articles.ArticleImageViewHolder
import java.lang.ref.WeakReference
import kotlin.reflect.KClass

class ArticlesViewHoldersFactory : BinderViewHoldersFactory() {

    override val supportedClasses: Collection<KClass<Any>> = listOf(
        Article.Description::class,
        Article.Image::class
    ) as Collection<KClass<Any>>

    override fun create(
        parent: ViewGroup,
        kClass: KClass<Any>
    ): BinderViewHolder<Any> {
        WeakReference(LayoutInflater.from(parent.context)).get()?.let {
            layoutInflater ->
            return when(kClass) {
                Article.Description::class -> ArticleDescriptionViewHolder(
                    ItemArticleDescriptionBinding.inflate(layoutInflater, parent, false)
                )
                Article.Image::class -> ArticleImageViewHolder(
                    ItemArticleImageBinding.inflate(layoutInflater, parent, false)
                )
                else -> error("Unhandled class ${kClass.simpleName}")
            } as BinderViewHolder<Any>
        } ?: throw error("Can't get LayoutInflater from provided context!")
    }
}