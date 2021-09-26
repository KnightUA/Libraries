package ua.udevapp.samples.data.models.items

sealed class Article(val title: String) {
    class Description(title: String, val description: String) : Article(title)
    class Image(title: String, val imageUrl: String) : Article(title)
}