package id.smartech.creepystory.services

import id.smartech.creepystory.model.label.LabelResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface LabelApiService {
    @GET("label/story/{stId}")
    suspend fun getLabelByStoryId(@Path("stId")storyId: Int) : LabelResponse
}