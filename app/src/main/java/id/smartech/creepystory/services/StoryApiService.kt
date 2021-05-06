package id.smartech.creepystory.services

import id.smartech.creepystory.model.GeneralResponse
import id.smartech.creepystory.model.story.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface StoryApiService {

    @GET("story")
    suspend fun getAllStory(): StoryResponse

    @GET("story?order=0")
    suspend fun getAllStoryByFavorite(): StoryResponse

    @GET("story/member/{meId}")
    suspend fun getStoryByMemberId(@Path("meId")memberId: Int) : StoryResponse

    @Multipart
    @POST("story")
    suspend fun createStory(
            @Part("ct_id")categoryId: RequestBody,
            @Part("me_id")memberId: RequestBody,
            @Part("st_title")storyTitle: RequestBody,
            @Part("st_content")storyContent: RequestBody,
            @Part image: MultipartBody.Part? = null
    ) : GeneralResponse

    @Multipart
    @POST("story")
    suspend fun createStoryWithoutImage(
            @Part("ct_id")categoryId: RequestBody,
            @Part("me_id")memberId: RequestBody,
            @Part("st_title")storyTitle: RequestBody,
            @Part("st_content")storyContent: RequestBody
    ) : GeneralResponse
}