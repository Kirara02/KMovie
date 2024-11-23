package id.kirara.kmovie.ui.scene.artisdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.kirara.kmovie.domain.MediaType
import id.kirara.kmovie.domain.artist.ArtistCredit
import id.kirara.kmovie.ui.components.BackPressedItem
import id.kirara.kmovie.ui.components.DetailPosterImage
import id.kirara.kmovie.ui.components.DetailScreensAppBar
import id.kirara.kmovie.ui.components.ExpandableText
import id.kirara.kmovie.ui.components.PosterImageItem
import id.kirara.kmovie.ui.components.TextItem
import kmovie.composeapp.generated.resources.Res
import kmovie.composeapp.generated.resources.actor_born
import org.jetbrains.compose.resources.stringResource

@Composable
fun ArtistDetailScreen(
    viewModel: ArtistDetailViewModel,
    actorId: Int,
    navigateToMovie: (Int) -> Unit,
    navigateToTv: (Int) -> Unit,
    onBackPressed: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchData(actorId)
    }

    SuccessContent(
        uiState = uiState,
        onDetailClick = {
            when(it.second) {
                MediaType.MOVIE.mediaType -> navigateToMovie(it.first)
                MediaType.TV.mediaType -> navigateToTv(it.first)
            }
        },
        onBackPressed = onBackPressed,
    )


}

@Composable
fun SuccessContent(
    uiState: ArtistDetailUiState,
    onDetailClick: (Pair<Int, String>) -> Unit,
    onBackPressed: () -> Unit
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        DetailScreensAppBar(
            leadingIcon = { BackPressedItem { onBackPressed() } },
            content = {
                DetailPosterImage(
                    imagePath = uiState.artistDetailData.profilePath
                )
            }
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            TextItem(
                text = uiState.artistDetailData.name,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            ExpandableText(
                text = uiState.artistDetailData.biography
            )
            Row(
                modifier = Modifier.padding(bottom = 24.dp, top = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                TextItem(
                    text = stringResource(Res.string.actor_born),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                TextItem(
                    maxLines = Int.MAX_VALUE,
                    fontSize = 16.sp,
                    text = "${uiState.artistDetailData.birthday}  ${uiState.artistDetailData.placeOfBirth}"
                )
            }
        }
        PersonCreditLazyRow(uiState, onDetailClick)
    }
}

@Composable
fun PersonCreditLazyRow(
    uiState: ArtistDetailUiState,
    onDetailClick: (Pair<Int, String>) -> Unit,
) {
    LazyRow(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(uiState.artistDetailData.credit.size) { index ->
            PersonCreditCardView(
                credit = uiState.artistDetailData.credit[index],
                onClick = onDetailClick
            )
        }
    }
}

@Composable
fun PersonCreditCardView(
    credit: ArtistCredit,
    onClick: (Pair<Int, String>) -> Unit,
) {
    Row(
        modifier = Modifier
            .width(240.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(6.dp)
            .clickable { onClick.invoke(Pair(credit.id ?: 0, credit.mediaType ?: "")) },
        verticalAlignment = Alignment.Top,
    ) {
        Card(
            modifier = Modifier
                .size(80.dp),
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
        ) {
            PosterImageItem(imagePath = credit.imagePath)
        }
        Spacer(Modifier.width(12.dp))
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            TextItem(
                text = credit.name ?: "-",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            TextItem(
                text = credit.character ?: "-",
                fontSize = 14.sp,
            )
        }
    }
}