package github.sukieva.hhu.network

import github.sukieva.hhu.data.bean.LoginData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface JwxtService {

    @POST("j_spring_security_check")
    fun login(@Body data: LoginData): Call<JwxtService>

    @GET("img/captcha.jpg")
    fun getCaptchaPic(): Call<JwxtService>


    @GET("student/integratedQuery/scoreQuery/schemeScores/callback")
    fun getGrades(): Call<JwxtService>


    @GET("student/integratedQuery/gpaRankingQuery/index/jdpmcx")
    fun getRank(): Call<JwxtService>

}