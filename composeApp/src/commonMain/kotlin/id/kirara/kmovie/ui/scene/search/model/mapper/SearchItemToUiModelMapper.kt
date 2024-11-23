package id.kirara.kmovie.ui.scene.search.model.mapper

import id.kirara.kmovie.domain.MediaType
import id.kirara.kmovie.domain.search.SearchItem
import id.kirara.kmovie.ui.scene.search.model.SearchUiModel
import kmovie.composeapp.generated.resources.Res
import kmovie.composeapp.generated.resources.ic_search_actor
import kmovie.composeapp.generated.resources.ic_search_movie
import kmovie.composeapp.generated.resources.ic_search_tv
import org.jetbrains.compose.resources.DrawableResource

class SearchItemToUiModelMapper {
    fun map(from: SearchItem): SearchUiModel {
        with(from) {
            return SearchUiModel(
                name = name,
                imagePath = imagePath,
                iconType = getIconType(from),
                mediaType = mediaType?.title ?: "",
                id = id
            )
        }
    }

    private fun getIconType(searchItem: SearchItem): DrawableResource? =
        when (searchItem.mediaType) {
            MediaType.MOVIE -> {
                Res.drawable.ic_search_movie
            }

            MediaType.TV -> {
                Res.drawable.ic_search_tv
            }

            MediaType.PERSON -> {
                Res.drawable.ic_search_actor
            }

            else -> null
        }
}