package me.study.silang.repository

import io.reactivex.Observable
import me.study.silang.bean.Param
import me.study.silang.bean.Rest
import me.study.silang.entity.Post
import me.study.silang.entity.Video
import me.study.silang.model.VideoModel
import okhttp3.MultipartBody
import retrofit2.http.*

interface VideoRepository {
    @Multipart
    @POST("file/upload")
    fun upload(@PartMap param: Map<String,String>, @Part files: List<MultipartBody.Part>): Observable<Rest<String>>

    @FormUrlEncoded
    @POST("video")
    fun add(@FieldMap param: Param): Observable<Rest<String>>

    @GET("video")
    fun list(@QueryMap param: Param):Observable<Rest<List<VideoModel>>>
}
