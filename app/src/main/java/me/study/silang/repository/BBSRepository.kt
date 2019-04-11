package me.study.silang.repository

import io.reactivex.Observable
import me.study.silang.bean.Param
import me.study.silang.bean.Rest
import me.study.silang.entity.Post
import me.study.silang.model.PostModel
import me.study.silang.model.UserData
import okhttp3.MultipartBody
import retrofit2.http.*

interface BBSRepository {

    @GET("user/get-data")
    fun getUserData(): Observable<Rest<UserData>>
    @GET("post")
    fun list(@QueryMap param: Param):Observable<Rest<List<PostModel>>>
}
