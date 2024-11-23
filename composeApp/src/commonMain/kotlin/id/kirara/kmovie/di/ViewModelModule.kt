package id.kirara.kmovie.di

import id.kirara.kmovie.ui.scene.account.AccountDetailViewModel
import id.kirara.kmovie.ui.scene.account.favorite.FavoriteViewModel
import id.kirara.kmovie.ui.scene.artisdetail.ArtistDetailViewModel
import id.kirara.kmovie.ui.scene.login.LoginViewModel
import id.kirara.kmovie.ui.scene.main.MainViewModel
import id.kirara.kmovie.ui.scene.map.MapViewModel
import id.kirara.kmovie.ui.scene.movie.MovieViewModel
import id.kirara.kmovie.ui.scene.moviedetail.MovieDetailViewModel
import id.kirara.kmovie.ui.scene.movies.MoviesViewModel
import id.kirara.kmovie.ui.scene.search.SearchViewModel
import id.kirara.kmovie.ui.scene.splashscreen.SplashViewModel
import id.kirara.kmovie.ui.scene.tv.TvViewModel
import id.kirara.kmovie.ui.scene.tvdetail.TvDetailViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { MainViewModel() }
    factory { SplashViewModel(get()) }
    factory { LoginViewModel(get(), get(), get()) }
    factory { MovieViewModel(get()) }
    factory { MoviesViewModel(get()) }
    factory { MovieDetailViewModel(get(), get(), get(), get(), get(), get()) }
    factory { TvDetailViewModel(get(), get(), get(), get(), get(), get()) }
    factory { ArtistDetailViewModel(get()) }
    factory { TvViewModel(get()) }
    factory { AccountDetailViewModel(get(), get()) }
    factory { FavoriteViewModel(get(), get()) }
    factory { SearchViewModel(get()) }
    factory { MapViewModel(get(), get()) }
}