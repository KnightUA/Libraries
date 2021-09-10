package ua.udevapp.samples.data.repository.items

import ua.udevapp.core.exceptions.Failure
import ua.udevapp.core.functional.Either
import ua.udevapp.samples.data.models.items.Phrase


interface ItemRepository {
    suspend fun fetchPhrases(): Either<Failure, List<Phrase>>
}