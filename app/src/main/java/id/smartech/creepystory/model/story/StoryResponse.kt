package id.smartech.creepystory.model.story

data class StoryResponse (
        val success: Boolean,
        val message: String,
        val length: Int,
        val data: List<StoryModel>
)