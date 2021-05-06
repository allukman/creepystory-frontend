package id.smartech.creepystory.model.story

import com.google.gson.annotations.SerializedName

data class StoryModel (
    @SerializedName("st_id") val stId: Int,
    @SerializedName("ct_id") val ctId: Int,
    @SerializedName("me_id") val meId: Int,
    @SerializedName("ct_name") val ctName: String,
    @SerializedName("me_name") val meName: String,
    @SerializedName("st_title") val stTitle: String,
    @SerializedName("st_content") val stContent: String,
    @SerializedName("me_photo_profile") val mePhotoProfile: String? = null,
    @SerializedName("st_photo_cover") val stPhotoCover: String? = null,
    @SerializedName("me_photo_cover") val mePhotoCover: String? = null,
    @SerializedName("me_domicile") val meDomicile: String,
    @SerializedName("me_description") val meDescription: String,
    @SerializedName("me_role") val meRole: String,
    @SerializedName("st_created_at") val stCreated: String,
    @SerializedName("st_updated_at") val stUpdated: String,
    @SerializedName("st_favorite_length") val stFavorited: String
)