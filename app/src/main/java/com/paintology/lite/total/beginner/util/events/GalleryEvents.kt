package com.paintology.lite.total.beginner.util.events

import com.paintology.lite.total.beginner.Activity.gallery_activity.model.new_models.NewDrawing
import com.paintology.lite.total.beginner.Activity.leader_board.model.CountriesModel

data class GalleryEvent(
    val model: CountriesModel,
    val flag: Int?,
    val position: Int
)

data class UserGalleryEvent(
    val model: CountriesModel,
    val flag: Int?,
    val position: Int
)

data class FilterChangeEvent(
    val filterType: String
)

data class DrawingsChangeEvent(
    val data: List<NewDrawing>
)


data class RefreshFavoriteEvent(
    val page: Int
)

data class RefreshPointEvent(
    val isRefresh: Boolean
)

