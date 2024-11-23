package id.kirara.kmovie.network.exception

class NetworkException(
    val networkError: ApiErrorResponse
) : Exception("API Error: ${networkError.statusMessage}")