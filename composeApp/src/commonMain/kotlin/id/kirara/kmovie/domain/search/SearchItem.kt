package id.kirara.kmovie.domain.search

import id.kirara.kmovie.domain.MediaType

data class SearchItem(
    val name: String = "",
    val imagePath: String = "",
    val mediaType: MediaType? = null,
    val id: Int = 0
)