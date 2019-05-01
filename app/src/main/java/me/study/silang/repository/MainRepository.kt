package me.study.silang.repository

import io.reactivex.Observable
import me.study.silang.bean.Param
import me.study.silang.bean.Rest
import me.study.silang.entity.Message
import me.study.silang.entity.Post
import me.study.silang.entity.User
import me.study.silang.entity.Video
import me.study.silang.model.UserData
import me.study.silang.model.UserInfo
import okhttp3.MultipartBody
import retrofit2.http.*

interface MainRepository {
    @GET("user/by-id")
    fun getUserInfo(): Observable<Rest<UserInfo>>

    @GET("user/get-data")
    fun getUserData(): Observable<Rest<UserData>>

    @FormUrlEncoded
    @PUT("user/set-pass")
    fun updatePass(@Field("userId") userId: Int, @Field("oldPass") oldPass: String, @Field("newPass") newPass: String): Observable<Rest<String>>

    @GET("message/list")
    fun listMessages(): Observable<Rest<List<Message>>>

    @GET("message/get/by-id")
    fun getMessage(@Query("id") id:Int): Observable<Rest<Message>>

    @DELETE("message/del/um")
    fun delMessageById(@Query("id") id:Int): Observable<Rest<String>>
}
