package me.study.silang.repository

import io.reactivex.Observable
import me.study.silang.bean.Param
import me.study.silang.bean.Rest
import me.study.silang.model.PostModel
import me.study.silang.model.ReplyModel
import retrofit2.http.*

interface PostRepository{
    @FormUrlEncoded
    @POST("post")
    fun add(@FieldMap param: Param): Observable<Rest<String>>

    @GET("reply/get-id")
    fun getById(@Query("id") id:Int):Observable<Rest<List<ReplyModel>>>

    @GET("reply")
    fun list(@QueryMap param: Param):Observable<Rest<List<ReplyModel>>>

    @FormUrlEncoded
    @POST("reply")
    fun addReply(@FieldMap param: Param): Observable<Rest<String>>

}
