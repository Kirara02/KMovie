package id.kirara.kmovie.utils

import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import id.kirara.kmovie.network.exception.NetworkException
import id.kirara.kmovie.network.parseError

suspend inline fun <T> invoke(noinline block: suspend () -> T): T = withContext(Dispatchers.IO) {
    block()
}

suspend inline fun <R> resultOf(block: () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: ClientRequestException) {
        val errorResponse = e.response.parseError()
        Result.failure(NetworkException(errorResponse))
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }
}