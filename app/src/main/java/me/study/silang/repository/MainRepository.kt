package me.study.silang.repository

import io.reactivex.Observable
import me.study.silang.bean.Param
import me.study.silang.bean.Rest
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
    fun updatePass(userId:Int, oldPass:String,newPass:String):Observable<Rest<String>>
}
