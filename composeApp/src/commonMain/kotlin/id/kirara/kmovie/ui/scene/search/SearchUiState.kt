package id.kirara.kmovie.ui.scene.search

import id.kirara.kmovie.ui.scene.search.model.SearchUiModel

data class SearchUiState(
    val isLoading: Boolean = false,
    val data: List<SearchUiModel> = listOf(),
    val error: String? = null,
    val emptyState: Boolean = false,
) {
    fun removeList() = copy(
        data = listOf(),
        isLoading = false,
        error = null,
        emptyState = false
    )

    fun updateData(list: List<SearchUiModel>) = copy(
        data = list,
        isLoading = false,
        emptyState = list.isEmpty(),
    )

    fun showLoading() = copy(
        isLoading = true,
        error = null
    )

    fun showError(message: String) = copy(
        isLoading = false,
        data = listOf(),
        error = message
    )
}
