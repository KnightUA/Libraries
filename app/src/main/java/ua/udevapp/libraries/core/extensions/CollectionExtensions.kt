package ua.udevapp.libraries.core.extensions

import timber.log.Timber

inline val <M> Collection<M>?.isSafeEmpty get() = this?.isEmpty() == true
inline val <M> Collection<M>.lastPosition get() = if(isEmpty()) 0 else size - 1

fun <M> Collection<M>.copy(): Collection<M> = run {
    val destination = mutableListOf<M>()
    return@run this.toCollection(destination)
}

fun <M> emptyCollection(): Collection<M> {
    return emptyList<M>().copy()
}

fun <M> Collection<M?>?.safeCollection(): Collection<M> {
    return if (this.isNullOrEmpty()) emptyCollection()
    else this.filterNotNull()
}

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

fun <M> MutableCollection<M>.removeAt(positionRemove: Int) {
    if (positionRemove !in this.indices) return Timber.w("Out of range position: trying to remove item at $positionRemove where $size size")

    forEachIndexed searchIndexOfModel@{ index, model ->
        if (index == positionRemove) {
            remove(model)
            return@searchIndexOfModel
        }
    }
}

fun <M> MutableCollection<M>.replaceAt(positionReplace: Int, itemReplace: M?) {
    if (positionReplace !in this.indices) return Timber.w("Out of range position: trying to replace item at $positionReplace where $size size")
    if(itemReplace == null) return Timber.w("New item is Null!")

    removeAt(positionReplace)
    insertAt(positionReplace, itemReplace)
}