package ua.udevapp.core.interactor

import kotlinx.coroutines.*
import ua.udevapp.core.exceptions.Failure
import ua.udevapp.core.functional.Either

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This abstraction represents an execution unit for different use cases (this means than any use
 * case in the application should implement this contract).
 *
 * By convention each [UseCase] implementation will execute its job in a background thread
 * (kotlin coroutine) and will post the result in the UI thread.
 */
abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(
        coroutineScope: CoroutineScope,
        params: Params,
        onResult: (Either<Failure, Type>) -> Unit = {}
    ) {
        val job = coroutineScope.async(Dispatchers.IO) { run(params) }
        coroutineScope.launch(Dispatchers.Main) { onResult(job.await()) }
    }

    class None
}
