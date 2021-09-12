package ua.udevapp.core.extensions

import timber.log.Timber

/**
 *
 * Fetch last position of list, or return @null when can't find it
 *
 * @param M can be any type
 * @return last position of collection
 */
inline val <M> Collection<M>.lastPosition : Int? get() = if(isEmpty()) null else size - 1

/**
 *
 * Make copy of existing collection
 *
 * @param M can be any type which used in [Collection]
 * @return new collection without references on previous ones
 */
fun <M> Collection<M>.copy(): Collection<M> = run {
    val destination = mutableListOf<M>()
    return@run this.toCollection(destination)
}

/**
 *
 * Create a new empty [Collection]
 *
 * @param M can be any type
 * @return an new empty Collection
 */
fun <M> emptyCollection(): Collection<M> {
    return emptyList<M>().copy()
}

/**
 *
 * Convert exist [Collection] to safe collection without @null
 *
 * @param M can be any type and nullable
 * @return a new Collection without @null models
 */
fun <M> Collection<M?>?.safeCollection(): Collection<M> {
    return if (this.isNullOrEmpty()) emptyCollection()
    else this.filterNotNull()
}

/**
 *
 * Insert in [Collection] new item at position
 *
 * @param M can be any type
 * @param positionInsert is a position where need to insert item
 * @param itemInsert is a item which will be inserted in list
 * @throws Nothing when position out of range and item is @null
 */
fun <M> MutableCollection<M>.insertAt(positionInsert: Int, itemInsert: M?) {
    if (positionInsert !in this.indices) return Timber.w("Out of range position: trying to add item at $positionInsert where $size size")
    if(itemInsert == null) return Timber.w("Inserted item is Null!")

    val mutableCollection = mutableListOf<M>()
    forEachIndexed { index, model ->
        if (index == positionInsert) mutableCollection.add(itemInsert)
        mutableCollection.add(model)
    }

    clear()
    addAll(mutableCollection as Collection<M>)
}

/**
 *
 * Remove in [Collection] exist item at position
 *
 * @param M can be any type
 * @param positionRemove is a position where item is located for remove it
 * @throws Nothing when position out of range and item is @null
 */
fun <M> MutableCollection<M>.removeAt(positionRemove: Int) {
    if (positionRemove !in this.indices) return Timber.w("Out of range position: trying to remove item at $positionRemove where $size size")

    var modelRemove:M? = null
    forEachIndexed searchIndexOfModel@{ index, model ->
        if (index == positionRemove) {
            modelRemove = model
            return@searchIndexOfModel
        }
    }

    modelRemove?.let { remove(it) }
}

/**
 *
 * Replace in collection exist item to a new one at position
 *
 * @param M can be any type
 * @param positionReplace is a position where need to replace item
 * @param itemReplace is a item which will be replaced in list
 */
fun <M> MutableCollection<M>.replaceAt(positionReplace: Int, itemReplace: M?) {
    if (positionReplace !in this.indices) return Timber.w("Out of range position: trying to replace item at $positionReplace where $size size")
    if(itemReplace == null) return Timber.w("New item is Null!")

    removeAt(positionReplace)
    insertAt(positionReplace, itemReplace)
}

/**
 *
 * Fetch item from collection at position
 *
 * @param M can be any type
 * @param position from which need to fetch item
 * @return item or @null if not found
 */
fun <M> Collection<M>.getItemAt(position: Int): M? {
    forEachIndexed { index, item ->
        if (position == index) return item
    }

    return null
}