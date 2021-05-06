package id.smartech.creepystory.model.story

import com.google.gson.annotations.SerializedName

data class StoryMemberModel (
    @SerializedName("st_id") val stId: Int,
    @SerializedName("ct_id") val ctId: Int,
    @SerializedName("me_id") val  meId: Int,
    @SerializedName("ct_name") val ctName: String,
    @SerializedName("me_name") val meName: String,
    @SerializedName("st_title") val stTitle: String,
    @SerializedName("st_content") val stContent: String,
    @SerializedName("me_photo_profile") val mePhotoProfile: String,
    @SerializedName("st_photo_cover") val stPhotoCover: String,
    @SerializedName("st_created_at") val stCreatedAt: String,
    @SerializedName("st_updated_at") val stUpdatedAt: String
)