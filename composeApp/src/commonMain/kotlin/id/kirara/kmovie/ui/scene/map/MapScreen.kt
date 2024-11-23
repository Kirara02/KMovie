package id.kirara.kmovie.ui.scene.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.kirara.kmovie.core.getPlatformContext
import id.kirara.kmovie.core.navigateToMap
import id.kirara.kmovie.core.viewModel
import id.kirara.kmovie.map.Map
import id.kirara.kmovie.ui.components.BackPressedItem
import id.kirara.kmovie.ui.components.MapsMarkerDialog
import id.kirara.kmovie.ui.components.TextItem
import id.kirara.kmovie.ui.theme.Fonts
import kmovie.composeapp.generated.resources.Res
import kmovie.composeapp.generated.resources.cinema
import org.jetbrains.compose.resources.stringResource

class MapScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val viewModel: MapViewModel = viewModel()
        val uiState by viewModel.uiState.collectAsState()

        val platformContext = getPlatformContext()

        viewModel.loadForecastWithLocation()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        TextItem(
                            text = stringResource(Res.string.cinema),
                            fontSize = 20.sp,
                            fontFamily = Fonts.bold,
                            textColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
                    navigationIcon = {
                        BackPressedItem { navigator.pop() }
                    }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(top = paddingValues.calculateTopPadding())
            ) {
                Map(
                    modifier = Modifier,
                    uiState = uiState,
                    onMarkerClick = viewModel::setSelectedCinema,
                    onPositionChange = viewModel::getUpdates
                )

                AnimatedVisibility(
                    visible = uiState.selectedCinema != null,
                    enter = expandVertically(),
                    exit = shrinkVertically(),
                ) {
                    MapsMarkerDialog(
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 18.dp)
                            .align(Alignment.TopCenter),
                        title = uiState.selectedCinema?.name ?: "",
                        subTitle = uiState.selectedCinema?.description ?: ""
                    ) {
                        navigateToMap(
                            context = platformContext,
                            deviceLocation = uiState.selectedCinema?.location,
                            destinationName = uiState.selectedCinema?.name ?: ""
                        )
                    }
                }
            }
        }
    }
}