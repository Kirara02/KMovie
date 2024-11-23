package id.kirara.kmovie.data.search

import id.kirara.kmovie.domain.search.SearchItem
import id.kirara.kmovie.domain.search.SearchRepository
import id.kirara.kmovie.utils.resultOf

class SearchRepositoryImpl(
    private val service: SearchService
) : SearchRepository {
    override suspend fun getSearch(query: String): Result<List<SearchItem>> {
        return resultOf {
            service.search(query).results.map { it.toDomain() }
        }
    }
}