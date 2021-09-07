package ua.udevapp.libraries.sample.domain.interactor.items

import ua.udevapp.libraries.core.exceptions.Failure
import ua.udevapp.libraries.core.functional.Either
import ua.udevapp.libraries.core.interactor.UseCase
import ua.udevapp.libraries.sample.data.models.items.Phrase
import ua.udevapp.libraries.sample.data.repository.items.ItemRepository
import javax.inject.Inject

class GetPhrases @Inject constructor(private val itemRepository: ItemRepository) : UseCase<List<Phrase>, UseCase.None>() {
    override suspend fun run(params: None): Either<Failure, List<Phrase>> {
        return itemRepository.fetchPhrases()
    }
}