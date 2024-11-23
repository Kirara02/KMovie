package id.kirara.kmovie.data.search

interface SearchService {
    suspend fun search(query: String) : SearchModel
}