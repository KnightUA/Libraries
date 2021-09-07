package ua.udevapp.libraries.recycler.diffUtils

interface CompareCollections<in D> {
    fun updateCollections(old: Collection<D>?, new: Collection<D>?) {
        old?.let { updateOldCollection(it) }
        new?.let { updateNewCollection(it) }
    }

    fun updateOldCollection(collection: Collection<D>)
    fun updateNewCollection(collection: Collection<D>)

    fun areItemsTheSame(old: D?, new: D?) : Boolean
    fun areContentsTheSame(old: D?, new: D?) : Boolean
}