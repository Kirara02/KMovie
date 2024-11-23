package id.kirara.kmovie.ui.scene.movie

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import id.kirara.kmovie.core.ifNotNull
import id.kirara.kmovie.domain.movie.NowPlayingMovie
import id.kirara.kmovie.domain.movie.PopularMovie
import id.kirara.kmovie.permission.Permission
import id.kirara.kmovie.permission.isGranted
import id.kirara.kmovie.permission.rememberPermissionState
import id.kirara.kmovie.ui.components.CardImageItem
import id.kirara.kmovie.ui.components.DateItem
import id.kirara.kmovie.ui.components.ErrorScreen
import id.kirara.kmovie.ui.components.LoadingScreen
import id.kirara.kmovie.ui.components.PosterImageItem
import id.kirara.kmovie.ui.components.RateItem
import id.kirara.kmovie.ui.components.TextItem
import id.kirara.kmovie.ui.theme.Fonts
import kmovie.composeapp.generated.resources.Res
import kmovie.composeapp.generated.resources.ic_maps
import kmovie.composeapp.generated.resources.tab_movies
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.math.absoluteValue

@Composable
fun MovieScreen(
    viewModel: MovieViewModel,
    onDetailClick: (Int) -> Unit,
    onMapClick: () -> Unit,
    onNowPlayingsClick: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Spacer(
            modifier = Modifier.fillMaxWidth()
                .windowInsetsTopHeight(WindowInsets.statusBars)
                .background(MaterialTheme.colorScheme.primary)
        )

        uiState.error.ifNotNull {
            ErrorScreen(it)
        }

        if(uiState.isLoading){
            LoadingScreen()
        }

        SuccessContent(
            popularMovieData = uiState.popularMovieData,
            nowPlayingMovieData = uiState.nowPlayingMovieData,
            onDetailClick = onDetailClick,
            onMapClick = onMapClick,
            onNowPlayingsClick = onNowPlayingsClick,
        )
    }

}

@Composable
fun SuccessContent(
    modifier: Modifier = Modifier,
    popularMovieData: List<PopularMovie>,
    nowPlayingMovieData: List<NowPlayingMovie>,
    onDetailClick: (Int) -> Unit,
    onMapClick: () -> Unit,
    onNowPlayingsClick: () -> Unit,
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            HorizontalMoviePager(popularMovieData, onMapClick = onMapClick, onDetailClick = {
                onDetailClick(it)
            })
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextItem(
                    text = "Now Playings",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                TextButton(
                    onClick = {
                        onNowPlayingsClick()
                    }
                ) {
                    TextItem(
                        text = "See All"
                    )
                }
            }
        }
//        items(nowPlayingMovieData) { nowPlayingMovies ->
//            NowPlayingMovieRow(nowPlayingMovies = nowPlayingMovies) { id ->
//                onDetailClick(id)
//            }
//        }
        item {
            LazyRow(
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                items(nowPlayingMovieData){ nowPlaying ->
                    NowPlayingMoviesCard(nowPlaying){ id ->
                        onDetailClick(id)
                    }
                }
            }
        }
    }
}

@Composable
fun NowPlayingMovieRow(
    nowPlayingMovies: NowPlayingMovie,
    onDetailClick: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .height(150.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onDetailClick.invoke(nowPlayingMovies.movieId) },
        shape = MaterialTheme.shapes.small,
    ) {
        Column {

            CardImageItem(imagePath = nowPlayingMovies.posterPath)

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                TextItem(
                    text = nowPlayingMovies.title,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DateItem(date = nowPlayingMovies.releaseDate)
                    RateItem(rate = nowPlayingMovies.voteAverage.toString())
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
            .width(160.dp)
            .height(240.dp)
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable { onDetailClick.invoke(nowPlayingMovies.movieId) },
        shape = MaterialTheme.shapes.small,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.Start,
        ) {
            CardImageItem(
                modifier = Modifier.size(height =  170.dp, width = 160.dp),
                imagePath = nowPlayingMovies.posterPath
            )

            TextItem(
                text = nowPlayingMovies.title,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 4.dp),
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            RateItem(
                backgroundColor = Color.Transparent,
                textColor = MaterialTheme.colorScheme.secondary,
                rate = nowPlayingMovies.voteAverage.toString(),
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalMoviePager(
    popularMovie: List<PopularMovie>,
    onDetailClick: (Int) -> Unit,
    onMapClick: () -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        popularMovie.size
    }

    Box {
        Surface(
            modifier = Modifier.fillMaxWidth().height(250.dp),
            color = MaterialTheme.colorScheme.primary
        ) {}
        Column {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextItem(
                    fontSize = 34.sp,
                    fontFamily = Fonts.bold,
                    textColor = MaterialTheme.colorScheme.primaryContainer,
                    text = stringResource(Res.string.tab_movies)
                )

                val permissionState = rememberPermissionState(Permission.LOCATION) { result ->
                    if (result) {
                        onMapClick.invoke()
                    }
                }

                IconButton(
                    onClick = {
                        if (permissionState.status.isGranted) {
                            onMapClick.invoke()
                        } else {
                            permissionState.launchPermissionRequest()
                        }
                    }
                ) {
                    Surface(
                        modifier = Modifier.size(35.dp),
                        shape = RoundedCornerShape(35.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(width = 14.dp, height = 19.dp)
                                .padding(8.dp),
                            painter = painterResource(Res.drawable.ic_maps),
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = null
                        )
                    }
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                Card(
                    Modifier
                        .graphicsLayer {
                            val pageOffset =
                                ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                            lerp(
                                start = 0.65f,
                                stop = 1f,
                                fraction = 0.5f - pageOffset.coerceIn(0f, 1f)
                            ).also { scale ->
                                scaleX = scale
                                scaleY = scale
                            }
                        }
                        .fillMaxWidth()
                        .clickable {
                            onDetailClick(popularMovie[page].movieId)
                        }
                        .aspectRatio(0.666f)) {
                    PosterImageItem(imagePath = popularMovie[page].posterPath)
                }
            }
        }
    }
}