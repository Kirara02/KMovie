package id.kirara.kmovie.di

import id.kirara.kmovie.domain.account.GetAccountDetailUseCase
import id.kirara.kmovie.domain.account.LogoutUseCase
import id.kirara.kmovie.domain.favorite.AddFavoriteUseCase
import id.kirara.kmovie.domain.favorite.GetMovieStateUseCase
import id.kirara.kmovie.domain.favorite.GetTvStateUseCase
import id.kirara.kmovie.domain.map.CinemaSearchUseCase
import id.kirara.kmovie.domain.rate.RateMovieUseCase
import id.kirara.kmovie.domain.rate.RateTvShowUseCase
import id.kirara.kmovie.domain.rate.RemoveMovieRatingUseCase
import id.kirara.kmovie.domain.rate.RemoveTvShowRatingUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetAccountDetailUseCase(get()) }
    factory { AddFavoriteUseCase(get()) }
    factory { GetMovieStateUseCase(get()) }
    factory { GetTvStateUseCase(get()) }
    factory { CinemaSearchUseCase(get()) }
    factory { RateTvShowUseCase(get()) }
    factory { RateMovieUseCase(get()) }
    factory { RemoveMovieRatingUseCase(get()) }
    factory { RemoveTvShowRatingUseCase(get()) }
    factory { LogoutUseCase(get(), get()) }
}