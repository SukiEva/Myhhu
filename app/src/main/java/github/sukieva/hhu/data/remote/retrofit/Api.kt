package github.sukieva.hhu.data.remote.retrofit

import github.sukieva.hhu.data.bean.LoginData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @POST("j_spring_security_check")
    fun login(@Body data: LoginData): Call<Api>

    @GET("img/captcha.jpg")
    fun getCaptchaPic(): Call<Api>


    @GET("student/integratedQuery/scoreQuery/schemeScores/callback")
    fun getGrades(): Call<Api>


    @GET("student/integratedQuery/gpaRankingQuery/index/jdpmcx")
    fun getRank(): Call<Api>

}