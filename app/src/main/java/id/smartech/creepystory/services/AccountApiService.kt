package id.smartech.creepystory.services

import id.smartech.creepystory.model.GeneralResponse
import id.smartech.creepystory.model.account.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AccountApiService {
    @FormUrlEncoded
    @POST("account/login")
    suspend fun loginAccount(
            @Field("email") email: String,
            @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("account/register")
    suspend fun RegisterAccount(
            @Field("ac_email") email: String,
            @Field("ac_password") password: String
    ): GeneralResponse


}