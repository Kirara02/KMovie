package id.kirara.kmovie.domain.search

interface SearchRepository {
    suspend fun getSearch(query: String) : Result<List<SearchItem>>
}