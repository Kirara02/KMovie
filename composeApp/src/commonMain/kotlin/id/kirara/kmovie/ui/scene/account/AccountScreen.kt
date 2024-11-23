package id.kirara.kmovie.ui.scene.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seiko.imageloader.rememberImagePainter
import id.kirara.kmovie.domain.MediaType
import id.kirara.kmovie.ui.components.TextItem
import id.kirara.kmovie.ui.theme.Fonts
import id.kirara.kmovie.utils.Constants
import kmovie.composeapp.generated.resources.Res
import kmovie.composeapp.generated.resources.fav_movie
import kmovie.composeapp.generated.resources.fav_tv
import kmovie.composeapp.generated.resources.hello
import kmovie.composeapp.generated.resources.tab_profile
import org.jetbrains.compose.resources.stringResource

@Composable
fun AccountScreen(
    viewModel: AccountDetailViewModel,
    navigateToSplash: () -> Unit,
    navigateToFavorite:(MediaType) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val logoutState by viewModel.logoutState.collectAsState()

    LaunchedEffect(Unit){
        viewModel.getAccountDetail()
    }

    LaunchedEffect(logoutState){
        if(logoutState){
            navigateToSplash()
        }
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Spacer(
            modifier =  Modifier
                .fillMaxWidth()
                .windowInsetsTopHeight(WindowInsets.statusBars)
                .background(MaterialTheme.colorScheme.primary)
        )

        SuccessContent(
            uiState = uiState,
            onFavMovieClick = navigateToFavorite,
            onFavTvClick = navigateToFavorite,
            onLogoutClick = { viewModel.logout() }
        )

    }
}

@Composable
fun SuccessContent(
    uiState: AccountUiState,
    onFavMovieClick: (MediaType) -> Unit,
    onFavTvClick: (MediaType) -> Unit,
    onLogoutClick:() -> Unit
) {
    Column {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            color = MaterialTheme.colorScheme.primary
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
            ) {
                TextItem(
                    modifier = Modifier.padding(top = 24.dp),
                    text = stringResource(Res.string.tab_profile),
                    fontSize = 34.sp,
                    textColor = MaterialTheme.colorScheme.primaryContainer,
                    fontFamily = Fonts.bold
                )
                Spacer(modifier = Modifier.padding(top = 24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        TextItem(
                            text = stringResource(Res.string.hello), fontSize = 20.sp,
                            textColor = MaterialTheme.colorScheme.primaryContainer,
                            fontFamily = Fonts.medium
                        )
                        TextItem(
                            modifier = Modifier.padding(top = 8.dp),
                            text = uiState.accountData.fullName,
                            fontSize = 25.sp,
                            textColor = MaterialTheme.colorScheme.primaryContainer,
                            fontFamily = Fonts.bold
                        )
                    }

                    uiState.accountData.profilePath?.let {
                        Image(
                            modifier = Modifier.size(72.dp)
                                .clip(CircleShape),
                            painter = rememberImagePainter(
                                Constants.IMAGE_BASE.plus(it)
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(vertical = 8.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .clickable {
                        onFavMovieClick(MediaType.MOVIE)
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextItem(
                    modifier = Modifier.padding(start = 16.dp),
                    text = stringResource(Res.string.fav_movie),
                )
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(vertical = 8.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .clickable {
                        onFavTvClick(MediaType.TV)
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextItem(
                    modifier = Modifier.padding(start = 16.dp),
                    text = stringResource(Res.string.fav_tv),
                )
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                onClick = {
                    onLogoutClick.invoke()
                }
            ) {
                TextItem(
                    text = "Logout",
                    textColor = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}