package id.kirara.kmovie.ui.scene.account.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.kirara.kmovie.domain.MediaType
import id.kirara.kmovie.domain.favorite.FavoriteMovie
import id.kirara.kmovie.domain.favorite.FavoriteTv
import id.kirara.kmovie.ui.components.BackPressedItem
import id.kirara.kmovie.ui.components.CardImageItem
import id.kirara.kmovie.ui.components.DateItem
import id.kirara.kmovie.ui.components.RateItem
import id.kirara.kmovie.ui.components.TextItem
import id.kirara.kmovie.ui.scene.account.FavoriteMovieUiState
import id.kirara.kmovie.ui.scene.account.FavoriteTvUiState
import id.kirara.kmovie.ui.theme.Fonts
import kmovie.composeapp.generated.resources.Res
import kmovie.composeapp.generated.resources.fav_movie
import kmovie.composeapp.generated.resources.fav_tv
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel,
    mediaType: MediaType,
    navigateToMovie: (Int) -> Unit,
    navigateToTv: (Int) -> Unit,
    navigateBack: () -> Unit,
) {

    val favoriteMovieUiState by viewModel.favoriteMovieUiState.collectAsState()
    val favoriteTvUiState by viewModel.favoriteTvUiState.collectAsState()

    when (mediaType) {
        MediaType.MOVIE -> {
            viewModel.getPopularMovie()

            Scaffold(topBar = {
                TopAppBar(
                    title = {
                        TextItem(
                            text = stringResource(Res.string.fav_movie),
                            fontSize = 20.sp,
                            fontFamily = Fonts.bold,
                            textColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
                    navigationIcon = {
                        BackPressedItem(onBackPressed = navigateBack)
                    }
                )
            }) { contentPadding ->
                Box(modifier = Modifier.padding(top = contentPadding.calculateTopPadding())) {
                    Spacer(
                        modifier = Modifier.height(190.dp).fillMaxWidth()
                            .align(Alignment.TopCenter)
                            .background(MaterialTheme.colorScheme.primary)
                    )
                    FavoriteMovieContent(favoriteMovieUiState, navigateToMovie)
                }
            }
        }

        MediaType.TV -> {
            viewModel.getPopularTv()

            Scaffold(topBar = {
                TopAppBar(
                    title = {
                        TextItem(
                            text = stringResource(Res.string.fav_tv),
                            fontSize = 20.sp,
                            fontFamily = Fonts.bold,
                            textColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
                    navigationIcon = {
                        BackPressedItem(onBackPressed = navigateBack)
                    }
                )
            }) { contentPadding ->
                Box(modifier = Modifier.padding(top = contentPadding.calculateTopPadding())) {
                    Spacer(
                        modifier = Modifier.height(190.dp).fillMaxWidth()
                            .align(Alignment.TopCenter)
                            .background(MaterialTheme.colorScheme.primary)
                    )
                    FavoriteTvContent(favoriteTvUiState, navigateToTv)
                }
            }
        }

        else -> Unit
    }

}

@Composable
fun FavoriteTvContent(
    favoriteTvUiState: FavoriteTvUiState,
    onTvDetailClick: (Int) -> Unit,
) {
    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
        items(favoriteTvUiState.favoriteTvData) {
            FavoriteTvRow(favoriteTv = it) { tvId ->
                onTvDetailClick(tvId)
            }
        }
    }
}

@Composable
fun FavoriteMovieContent(
    favoriteMovieUiState: FavoriteMovieUiState,
    onMovieDetailClick: (Int) -> Unit,
) {
    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
        items(favoriteMovieUiState.favoriteMovieData) {
            FavoriteMovieRow(favoriteMovie = it) { movieId ->
                onMovieDetailClick(movieId)
            }
        }
    }
}

@Composable
fun FavoriteMovieRow(
    favoriteMovie: FavoriteMovie,
    onDetailClick: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onDetailClick.invoke(favoriteMovie.movieId) },
        shape = MaterialTheme.shapes.small,
    ) {
        Row {
            CardImageItem(imagePath = favoriteMovie.posterPath)
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                TextItem(
                    text = favoriteMovie.title,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DateItem(date = favoriteMovie.releaseDate)
                    RateItem(rate = favoriteMovie.voteAverage.toString())
                }
            }
        }
    }
}

@Composable
fun FavoriteTvRow(
    favoriteTv: FavoriteTv,
    onDetailClick: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onDetailClick.invoke(favoriteTv.tvId) },
        shape = MaterialTheme.shapes.small,
    ) {
        Row {
            CardImageItem(imagePath = favoriteTv.posterPath)
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                TextItem(
                    text = favoriteTv.title,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    RateItem(rate = favoriteTv.voteAverage.toString())
                }
            }
        }
    }
}