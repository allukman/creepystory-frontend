package id.smartech.creepystory.model.label

import com.google.gson.annotations.SerializedName

data class LabelModel (
    @SerializedName("la_id") val laId: Int,
    @SerializedName("st_id") val stId: Int,
    @SerializedName("la_name") val laName: String
)