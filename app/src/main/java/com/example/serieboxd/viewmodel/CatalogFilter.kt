package com.example.serieboxd.viewmodel

enum class CatalogFilter(val label: String, val sortBy: String) {
    TRENDING("Popular", "popularity.desc"),
    TOP_RATED("Best rated", "vote_average.desc"),
    RECENT("New", "first_air_date.desc"),
}

enum class RatingsFilter(val label: String, val voteAverageGte: Int, val voteAverageLte: Int) {
    DEFAULT("All", 0, 10),
    POOR("Poor (0-4)", 0, 4),
    FAIR("Average (5-6)", 5, 6),
    GOOD("GOOD (>= 7)", 7, 10),
    EXCELLENT("Excellent (>= 8)", 8, 10),
    MASTERPIECE("Masterpiece (>= 9)", 9, 10),
}

data class DiscoverFilters(
    val catalog: CatalogFilter = CatalogFilter.TRENDING,
    val ratings: RatingsFilter = RatingsFilter.DEFAULT,
    val genreId: Int? = null
)
