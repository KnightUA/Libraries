package ua.udevapp.libraries.sample.data.repository.items

import ua.udevapp.libraries.core.exceptions.Failure
import ua.udevapp.libraries.core.functional.Either
import ua.udevapp.libraries.sample.data.models.items.Phrase


interface ItemRepository {
    suspend fun fetchPhrases(): Either<Failure, List<Phrase>>
}