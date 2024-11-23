package id.kirara.kmovie.di

import id.kirara.kmovie.data.account.AccountService
import id.kirara.kmovie.data.account.AccountServiceImpl
import id.kirara.kmovie.data.artist.ArtistService
import id.kirara.kmovie.data.artist.ArtistServiceImpl
import id.kirara.kmovie.data.favorite.FavoriteService
import id.kirara.kmovie.data.favorite.FavoriteServiceImpl
import id.kirara.kmovie.data.map.NominatimService
import id.kirara.kmovie.data.map.NominatimServiceImpl
import id.kirara.kmovie.data.movie.MovieService
import id.kirara.kmovie.data.movie.MovieServiceImpl
import id.kirara.kmovie.data.rate.RateService
import id.kirara.kmovie.data.rate.RateServiceImpl
import id.kirara.kmovie.data.search.SearchService
import id.kirara.kmovie.data.search.SearchServiceImpl
import id.kirara.kmovie.data.tv.TvService
import id.kirara.kmovie.data.tv.TvServiceImpl
import id.kirara.kmovie.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient {
            defaultRequest {
                url {
                    if(this.host.isBlank()){
                        takeFrom(Constants.BASE_URL)
                        parameters.append("api_key", Constants.API_KEY)
                    }
                }
            }
            expectSuccess = true
            install(HttpTimeout){
                val timeout = 30000L
                connectTimeoutMillis = timeout
                requestTimeoutMillis = timeout
                socketTimeoutMillis = timeout
            }
            install(Logging){
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            install(ContentNegotiation){
                json(Json {
                    ignoreUnknownKeys = true
                    explicitNulls = false
                })
            }
        }
    }

    single<AccountService> { AccountServiceImpl(get()) }
    single<MovieService> { MovieServiceImpl(get()) }
    single<FavoriteService> { FavoriteServiceImpl(get()) }
    single<TvService> { TvServiceImpl(get()) }
    single<RateService> { RateServiceImpl(get()) }
    single<ArtistService> { ArtistServiceImpl(get()) }
    single<SearchService> { SearchServiceImpl(get()) }
    single<NominatimService> {
        val client = get<HttpClient>()
        NominatimServiceImpl(client)
    }
}