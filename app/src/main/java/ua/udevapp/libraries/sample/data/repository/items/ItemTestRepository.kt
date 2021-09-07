package ua.udevapp.libraries.sample.data.repository.items

import kotlinx.coroutines.delay
import ua.udevapp.libraries.core.exceptions.Failure
import ua.udevapp.libraries.core.functional.Either
import ua.udevapp.libraries.sample.data.models.items.Phrase
import ua.udevapp.libraries.sample.exceptions.items.ReceiveListFailure
import javax.inject.Inject
import kotlin.random.Random

class ItemTestRepository @Inject constructor() : ItemRepository {
    private val phrases = listOf(
        Phrase(
            id = 0,
            value = "Knuckle Down",
            definition = "Getting sincere about something; applying oneself seriously to a job"
        ),
        Phrase(
            id = 1,
            value = "My Cup of Tea",
            definition = "Someone or something that one finds to be agreeable or delightful"
        ),
        Phrase(
            id = 2,
            value = "Jaws of Life",
            definition = "Usually this references a tool used by rescuers when they pry or cut open a car to save the occupant"
        ),
        Phrase(
            id = 3,
            value = "Jaws of Death",
            definition = "Being in a dangerous or very deadly situation"
        ),
        Phrase(
            id = 4,
            value = "Cut The Mustard",
            definition = "To cut the mustard is to meet a required standard, or to meet expectations"
        ),
        Phrase(
            id = 5,
            value = "Hard Pill to Swallow",
            definition = "Something that's difficult to accept"
        ),
        Phrase(
            id = 6,
            value = "Mountain Out of a Molehill",
            definition = "One who escalates small things and turns them into big problems"
        ),
        Phrase(
            id = 7,
            value = "Eat My Hat",
            definition = "Having confidence in a specific outcome; being almost sure about something"
        ),
        Phrase(id = 8, value = "A Cat Nap", definition = "A short slumber taken during the day"),
        Phrase(
            id = 9,
            value = "Keep Your Eyes Peeled",
            definition = "To be watchful; paying careful attention to something"
        ),
        Phrase(id = 10, value = "Raining Cats and Dogs", definition = "When it is raining heavily"),
        Phrase(
            id = 11,
            value = "Fit as a Fiddle",
            definition = "Being fit as a fiddle means to be in perfect health"
        ),
        Phrase(
            id = 12,
            value = "Cry Wolf",
            definition = "Someone that calls for help when it is not needed. Someone who is lying"
        ),
        Phrase(
            id = 13,
            value = "Back To the Drawing Board",
            definition = "Starting over again on a new design from a previously failed attempt"
        ),
        Phrase(
            id = 14,
            value = "Happy as a Clam",
            definition = "The state of being happy; feeling delighted"
        ),
        Phrase(
            id = 15,
            value = "Scot-free",
            definition = "Getting away freely from custody, punishment, or any type of risky situation"
        )
    )

    override suspend fun fetchPhrases(): Either<Failure, List<Phrase>> {
        delay(DELAY_TIME)
        val randomBoolean = Random.nextBoolean()
        return if (randomBoolean) {
            Either.Right(phrases)
        } else Either.Left(ReceiveListFailure())
    }

    companion object {
        private const val DELAY_TIME = 3_000L
    }
}