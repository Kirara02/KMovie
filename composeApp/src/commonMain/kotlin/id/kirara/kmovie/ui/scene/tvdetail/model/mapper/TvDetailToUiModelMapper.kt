package id.kirara.kmovie.ui.scene.tvdetail.model.mapper

import id.kirara.kmovie.domain.artist.Credits
import id.kirara.kmovie.domain.tv.TvDetail
import id.kirara.kmovie.ui.scene.tvdetail.model.TvDetailUiModel

class TvDetailToUiModelMapper {
    fun map(from: TvDetail, credit: List<Credits>): TvDetailUiModel {
        with(from) {
            return TvDetailUiModel(
                tvSeriesId = tvSeriesId,
                voteAverage = voteAverage,
                title = title,
                posterPath = posterPath,
                numberOfSeasons = numberOfSeasons,
                numberOfEpisodes = numberOfEpisodes,
                overview = overview,
                genre = genre,
                originalLanguage = originalLanguage,
                voteCount = voteCount,
                backdropPath = backdropPath,
                credit = credit,
                homepage = homepage
            )
        }
    }
}