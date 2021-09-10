package ua.udevapp.samples.domain.interactor.items

import ua.udevapp.core.exceptions.Failure
import ua.udevapp.core.functional.Either
import ua.udevapp.core.interactor.UseCase
import ua.udevapp.samples.data.models.items.Phrase
import ua.udevapp.samples.data.repository.items.ItemRepository
import javax.inject.Inject

class GetPhrases @Inject constructor(private val itemRepository: ItemRepository) : UseCase<List<Phrase>, UseCase.None>() {
    override suspend fun run(params: None): Either<Failure, List<Phrase>> {
        return itemRepository.fetchPhrases()
    }
}