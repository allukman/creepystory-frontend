package id.smartech.creepystory.model.label

data class LabelResponse (
    val success: Boolean,
    val message: String,
    val data: List<LabelModel>
)