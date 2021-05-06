package id.smartech.creepystory.model.account

import com.google.gson.annotations.SerializedName

data class LoginResponse(val success: Boolean, val message: String, val data: DataAccount) {
    data class DataAccount(
        @SerializedName("ac_id") val acId: Int,
        @SerializedName("me_name") val meName: String,
        @SerializedName("ac_email") val acEmail: String,
        @SerializedName("ac_phone") val acPhone: String,
        @SerializedName("ac_status") val acStatus: Int,
        @SerializedName("me_id") val meId: Int,
        @SerializedName("me_domicile") val meDomicile: String,
        @SerializedName("me_description") val meDescription: String,
        @SerializedName("me_role") val meRole: String,
        @SerializedName("me_dob") val meDob: String,
        @SerializedName("me_gender") val meGender: String,
        @SerializedName("me_photo_profile") val mePhotoProfile: String,
        @SerializedName("me_photo_cover") val mePhotoCover: String,
        @SerializedName("token") val token: String
    )
}