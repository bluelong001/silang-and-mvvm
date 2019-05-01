package me.study.silang.repository

import io.reactivex.Observable
import me.study.silang.bean.Param
import me.study.silang.bean.Rest
import me.study.silang.model.UserData
import me.study.silang.model.UserInfo
import okhttp3.MultipartBody
import retrofit2.http.*

interface MeRepository  {
    @Multipart
    @POST("file/upload")
    fun upload(@PartMap param: Map<String,String>, @Part files: List<MultipartBody.Part>): Observable<Rest<String>>

    @FormUrlEncoded
    @PUT("user/swap-head")
    fun updateHead(@FieldMap param: Param): Observable<Rest<String>>

    @FormUrlEncoded
    @PUT("user/swap-signature")
    fun updateSignature(@FieldMap param: Param): Observable<Rest<String>>

}