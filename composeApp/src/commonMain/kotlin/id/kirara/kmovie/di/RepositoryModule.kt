package id.kirara.kmovie.di

import id.kirara.kmovie.data.account.AccountRepositoryImpl
import id.kirara.kmovie.data.artist.ArtistRepositoryImpl
import id.kirara.kmovie.data.favorite.FavoriteRepositoryImpl
import id.kirara.kmovie.data.map.MapRepositoryImpl
import id.kirara.kmovie.data.movie.MovieRepositoryImpl
import id.kirara.kmovie.data.rate.RateRepositoryImpl
import id.kirara.kmovie.data.search.SearchRepositoryImpl
import id.kirara.kmovie.data.tv.TvRepositoryImpl
import id.kirara.kmovie.domain.account.AccountRepository
import id.kirara.kmovie.domain.artist.ArtistRepository
import id.kirara.kmovie.domain.favorite.FavoriteRepository
import id.kirara.kmovie.domain.map.MapRepository
import id.kirara.kmovie.domain.movie.MovieRepository
import id.kirara.kmovie.domain.rate.RateRepository
import id.kirara.kmovie.domain.search.SearchRepository
import id.kirara.kmovie.domain.tv.TvRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<AccountRepository> { AccountRepositoryImpl(get(), get()) }
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
    single<TvRepository> { TvRepositoryImpl(get(), get()) }
    single<RateRepository> { RateRepositoryImpl(get(), get()) }
    single<FavoriteRepository> { FavoriteRepositoryImpl(get(), get()) }
    single<ArtistRepository> { ArtistRepositoryImpl(get()) }
    single<SearchRepository> { SearchRepositoryImpl(get()) }
    single<MapRepository> { MapRepositoryImpl(get()) }
}