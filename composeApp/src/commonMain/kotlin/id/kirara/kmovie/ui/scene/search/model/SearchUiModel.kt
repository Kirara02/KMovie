package id.kirara.kmovie.ui.scene.search.model

import org.jetbrains.compose.resources.DrawableResource

data class SearchUiModel(
    val name: String = "",
    val imagePath: String = "",
    val iconType: DrawableResource? = null,
    val mediaType: String = "",
    val id: Int = 0

)