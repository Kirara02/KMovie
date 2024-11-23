package id.kirara.kmovie.domain

enum class MediaType(val mediaType: String, val title: String) {
    MOVIE("movie", "Movie"),
    TV("tv", "TV Series"),
    PERSON("person", "Actor")
}