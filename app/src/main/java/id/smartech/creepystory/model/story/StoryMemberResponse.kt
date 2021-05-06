package id.smartech.creepystory.model.story

data class StoryMemberResponse (val success: Boolean, val message: String, val length: Int, val data: List<StoryMemberModel>)