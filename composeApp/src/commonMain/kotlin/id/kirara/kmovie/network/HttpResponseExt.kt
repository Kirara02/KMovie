package id.kirara.kmovie.network

import id.kirara.kmovie.network.exception.ApiErrorResponse
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

suspend fun HttpResponse.parseError(): ApiErrorResponse {
    val errorBody = try {
        this.bodyAsText()
    } catch (e: Exception) {
        "Error message not available."
    }

    // Parse the JSON error response to ApiErrorResponse
    return try {
        Json.decodeFromString<ApiErrorResponse>(errorBody)
    } catch (e: Exception) {
        // Return a default error response in case of JSON parsing failure
        ApiErrorResponse(success = false, statusCode = this.status.value, statusMessage = "Failed to parse error response.")
    }
}