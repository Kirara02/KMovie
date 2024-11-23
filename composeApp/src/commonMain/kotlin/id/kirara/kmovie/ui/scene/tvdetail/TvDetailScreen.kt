package id.kirara.kmovie.ui.scene.tvdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.kirara.kmovie.core.getPlatformContext
import id.kirara.kmovie.core.ifNotNull
import id.kirara.kmovie.core.share
import id.kirara.kmovie.domain.MediaType
import id.kirara.kmovie.domain.artist.Credits
import id.kirara.kmovie.ui.components.BackPressedItem
import id.kirara.kmovie.ui.components.ChipItem
import id.kirara.kmovie.ui.components.DetailPosterImage
import id.kirara.kmovie.ui.components.DetailScreensAppBar
import id.kirara.kmovie.ui.components.ErrorScreen
import id.kirara.kmovie.ui.components.FavouriteItem
import id.kirara.kmovie.ui.components.FloatingActionButtonItem
import id.kirara.kmovie.ui.components.LoadingScreen
import id.kirara.kmovie.ui.components.PosterImageItem
import id.kirara.kmovie.ui.components.RateItem
import id.kirara.kmovie.ui.components.RateRow
import id.kirara.kmovie.ui.components.TextItem
import kmovie.composeapp.generated.resources.Res
import kmovie.composeapp.generated.resources.movie_detail_cast
import kmovie.composeapp.generated.resources.share
import kmovie.composeapp.generated.resources.tv_detail_episode
import kmovie.composeapp.generated.resources.tv_detail_season
import org.jetbrains.compose.resources.stringResource
import kotlin.math.round

@Composable
fun TvDetailScreen(
    viewModel: TvDetailViewModel,
    tvId: Int,
    navigateToActor: (Int) -> Unit,
    onBackPressed: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()
    val rating = viewModel.rating.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchData(tvId)
        viewModel.getTvState(tvId)
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primaryContainer)
    ) {
        uiState.error.ifNotNull {
            ErrorScreen(it)
        }

        if (uiState.isLoading) {
            LoadingScreen()
        }

        SuccessContent(
            uiState = uiState,
            onDetailClick = navigateToActor,
            onFavouriteClicked = { isFav, tvId ->
                viewModel.addFavorite(
                    mediaId = tvId,
                    mediaType = MediaType.TV.mediaType,
                    isFavorite = isFav
                )
            },
            onBackPressed = onBackPressed,
            isFavorite = isFavorite,
            ratingValue = rating,
            onRateTvShow = viewModel::rateTvShow
        )
    }

}

@Composable
fun SuccessContent(
    uiState: TvDetailUiState,
    isFavorite: Boolean,
    ratingValue: State<Int?>,
    onRateTvShow: (rate: Int, tvShowId: Int) -> Unit,
    onDetailClick: (Int) -> Unit,
    onBackPressed: () -> Unit,
    onFavouriteClicked: (isFav: Boolean, movieId: Int) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        DetailScreensAppBar(
            leadingIcon = {
                BackPressedItem(
                    modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)
                ) {
                    onBackPressed()
                }
            },
            trailingIcon = {
                FavouriteItem(
                    modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
                    isFavorite = isFavorite,
                    onFavouriteClicked = {
                        onFavouriteClicked(
                            !isFavorite,
                            uiState.tvDetailData.tvSeriesId
                        )
                    }
                )
            },
            content = {
                DetailPosterImage(
                    imagePath = uiState.tvDetailData.backdropPath,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                RateItem(
                    rate = round(uiState.tvDetailData.voteAverage).toString(),
                    modifier = Modifier.align(
                        Alignment.BottomStart
                    ).padding(start = 16.dp)
                )
            }
        )
        TvDetailContent(
            uiState = uiState,
            ratingValue = ratingValue,
            onRateTvShow = onRateTvShow
        )

        TvCreditLazyRow(
            uiState = uiState,
            onDetailClick = onDetailClick
        )
    }
}


@Composable
fun TvDetailContent(
    uiState: TvDetailUiState,
    ratingValue: State<Int?>,
    onRateTvShow: (rate: Int, tvShowId: Int) -> Unit,
) {
    val platformContext = getPlatformContext()

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {

        TextItem(
            modifier = Modifier.padding(top = 8.dp),
            text = uiState.tvDetailData.title,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            maxLines = Int.MAX_VALUE,
            lineHeight = 34.sp
        )

        TextItem(
            modifier = Modifier.padding(top = 8.dp),
            text = uiState.tvDetailData.genre,
            textColor = MaterialTheme.colorScheme.secondary
        )

        RateRow(
            modifier = Modifier.padding(vertical = 12.dp).height(IntrinsicSize.Min),
            ratingValue = ratingValue,
            onRatingValueChange = { onRateTvShow.invoke(it, uiState.tvDetailData.tvSeriesId) },
            hidableContent = {
                FloatingActionButtonItem(
                    text = stringResource(Res.string.share),
                    icon = Icons.Default.Share,
                    onClick = {
                        share(platformContext, uiState.tvDetailData.homepage)
                    }
                )
            })

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 10.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.secondaryContainer
        )

        TextItem(
            text = uiState.tvDetailData.overview,
            maxLines = Int.MAX_VALUE
        )

        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            ChipItem(
                string = stringResource(Res.string.tv_detail_season) + " ${uiState.tvDetailData.numberOfSeasons}",
                backgroundColor = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.padding(8.dp))
            ChipItem(
                string = stringResource(Res.string.tv_detail_episode) + " ${uiState.tvDetailData.numberOfEpisodes}",
                backgroundColor = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun TvCreditLazyRow(
    uiState: TvDetailUiState,
    onDetailClick: (Int) -> Unit,
) {
    TextItem(
        text = stringResource(Res.string.movie_detail_cast),
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )

    LazyRow(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(uiState.tvDetailData.credit.size) { index ->
            TvCreditCardView(
                credit = uiState.tvDetailData.credit[index],
                onClick = { id -> onDetailClick(id) })
        }
    }
}

@Composable
fun TvCreditCardView(
    credit: Credits,
    onClick: (Int) -> Unit,
) {


    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(6.dp)
            .clickable { onClick.invoke(credit.castId) },
        verticalAlignment = Alignment.Top,
    ) {
        androidx.compose.material3.Card(
            modifier = Modifier
                .size(80.dp),
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
        ) {
            PosterImageItem(imagePath = credit.profilePath)
        }
        Spacer(Modifier.width(12.dp))
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            TextItem(
                text = credit.originalName,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            TextItem(
                text = credit.character,
                fontSize = 14.sp,
            )
        }
    }
}