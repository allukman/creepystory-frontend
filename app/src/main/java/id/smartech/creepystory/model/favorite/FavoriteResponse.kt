package id.smartech.creepystory.model.favorite

data class FavoriteResponse (
    val success: Boolean,
    val message: String,
    val data: List<FavoriteModel>
)