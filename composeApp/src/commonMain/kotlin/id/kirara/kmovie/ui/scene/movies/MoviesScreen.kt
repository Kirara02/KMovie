package id.kirara.kmovie.ui.scene.movies


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.kirara.kmovie.core.ifNotNull
import id.kirara.kmovie.domain.movie.NowPlayingMovie
import id.kirara.kmovie.ui.components.ErrorScreen
import id.kirara.kmovie.ui.components.LoadingScreen
import id.kirara.kmovie.ui.components.PosterImageItem
import id.kirara.kmovie.ui.components.RateItem
import id.kirara.kmovie.ui.components.TextItem
import kmovie.composeapp.generated.resources.Res
import kmovie.composeapp.generated.resources.ic_arrow_back
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.jetbrains.compose.resources.painterResource

@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel,
    onNavigateBack: () -> Unit,
    onMovieClick: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyGridState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .map { visibleItems ->
                visibleItems.lastOrNull()?.index == uiState.nowPlayingMovieData.size - 1
            }
            .distinctUntilChanged()
            .collect { isAtEnd ->
                if (isAtEnd) viewModel.loadNextPage()
            }
    }

    uiState.error.ifNotNull {
        ErrorScreen(it)
    }


    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .map { visibleItems ->
                visibleItems.lastOrNull()?.index == uiState.nowPlayingMovieData.size - 1
            }
            .distinctUntilChanged()
            .collect { isAtEnd ->
                if (isAtEnd) {
                    println("Memanggil loadNextPage")
                    viewModel.loadNextPage()
                }
            }
    }


    SuccessContent(
        listState = listState,
        uiState = uiState,
        navigateToDetail = onMovieClick,
        onNavigateBack = onNavigateBack
    )
}

@Composable
fun SuccessContent(
    modifier: Modifier = Modifier,
    listState: LazyGridState,
    uiState: MoviesUiState,
    navigateToDetail: (Int) -> Unit,
    onNavigateBack: () -> Unit,
) {

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(top = 28.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ){
            IconButton(
                onClick = {
                    onNavigateBack()
                }
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.ic_arrow_back),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            TextItem(
                text = "Now Playings",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textColor = MaterialTheme.colorScheme.onPrimary
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = listState,
            modifier = Modifier.weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            items(uiState.nowPlayingMovieData) { nowPlaying ->
                NowPlayingMoviesCard(nowPlaying) {
                    navigateToDetail(it)
                }
            }
            item {
                if (uiState.isLoading) {
                    LoadingScreen()
                }
            }
        }
    }
}

@Composable
fun NowPlayingMoviesCard(
    nowPlayingMovies: NowPlayingMovie,
    onDetailClick: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        shape = MaterialTheme.shapes.small
    ) {
        Column {
            PosterImageItem(
                imagePath = nowPlayingMovies.posterPath,
                modifier = Modifier.clickable {
                    onDetailClick.invoke(nowPlayingMovies.movieId)
                }
            )

            TextItem(
                text = nowPlayingMovies.title,
                modifier = Modifier.padding(start = 6.dp, top = 6.dp, bottom = 6.dp)
            )

            RateItem(
                rate = nowPlayingMovies.voteAverage.toString(),
                modifier = Modifier.padding(start = 6.dp, bottom = 6.dp)
            )
        }

    }
}

