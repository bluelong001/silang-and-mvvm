package me.study.silang.repository

import io.reactivex.Observable
import me.study.silang.bean.Rest
import okhttp3.MultipartBody
import retrofit2.http.*

interface MainRepository {
    @Multipart
    @POST("file/upload")
    fun upload(@PartMap param: Map<String,String>, @Part files: List<MultipartBody.Part>): Observable<Rest<String>>
}
