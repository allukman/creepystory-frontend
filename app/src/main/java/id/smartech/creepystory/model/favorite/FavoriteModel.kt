package id.smartech.creepystory.model.favorite

import com.google.gson.annotations.SerializedName

data class FavoriteModel (
    @SerializedName("fa_id") val faId: Int,
    @SerializedName("me_id") val meId: Int,
    @SerializedName("st_id") val stId: Int,
    @SerializedName("fa_created_at") val faCreated: String,
    @SerializedName("me_name") val meName: String,
    @SerializedName("me_photo_profile") val mePhotoProfile: String,
    @SerializedName("me_photo_cover") val mePhotoCover: String,
    @SerializedName("me_description") val meDescription: String,
    @SerializedName("me_role") val meRole: String,
    @SerializedName("me_domicile") val meDomicile: String,
    @SerializedName("st_title") val stTitle: String,
    @SerializedName("st_photo_cover") val stPhotoCover: String,
    @SerializedName("st_content") val stContent: String,
    @SerializedName("st_created_at") val stCreated: String,
    @SerializedName("st_favorited") val stFavorited: String
)