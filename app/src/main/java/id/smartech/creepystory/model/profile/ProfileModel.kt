package id.smartech.creepystory.model.profile

import com.google.gson.annotations.SerializedName

data class ProfileModel (
        @SerializedName("me_id") val meId: Int,
        @SerializedName("me_name") val meName: String,
        @SerializedName("me_photo_profile") val mePhotoProfile: String? = null,
        @SerializedName("me_photo_cover") val mePhotoCover: String? = null,
        @SerializedName("me_domicile") val meDomicile: String,
        @SerializedName("me_description") val meDescription: String,
        @SerializedName("me_role") val meRole: String
)