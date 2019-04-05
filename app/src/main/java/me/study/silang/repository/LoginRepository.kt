package me.study.silang.repository

import io.reactivex.Observable
import me.study.silang.bean.Rest
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginRepository {
    @FormUrlEncoded
    @POST("login")
    fun login(@Field("username") username: String, @Field("password") password: String): Observable<Rest<String>>
}
