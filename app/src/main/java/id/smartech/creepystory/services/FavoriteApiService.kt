package id.smartech.creepystory.services

import id.smartech.creepystory.model.GeneralResponse
import id.smartech.creepystory.model.favorite.FavoriteResponse
import retrofit2.http.*

interface FavoriteApiService {

    @GET("favorite/{meId}")
    suspend fun getFavoriteByMeId(@Path("meId")memberId: Int) : FavoriteResponse

    @DELETE("favorite/{faId}")
    suspend fun deleteFavorite(@Path("faId") favoriteID: Int) : GeneralResponse

    @FormUrlEncoded
    @POST("favorite")
    suspend fun addFavorite(
            @Field("me_id") meId: Int,
            @Field("st_id") stId: Int
    ): GeneralResponse

    @GET("favorite")
    suspend fun checkIsFavorite(
            @Query("meId") meId: Int,
            @Query("stId") stId: Int
    ): GeneralResponse
}