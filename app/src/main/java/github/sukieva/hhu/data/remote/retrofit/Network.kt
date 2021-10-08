package github.sukieva.hhu.data.remote.retrofit

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import github.sukieva.hhu.data.bean.LoginData
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object Network {

    private val service = ServiceCreator.create<Api>()

    suspend fun login(data: LoginData) = service.login(data).await()

    suspend fun getCaptchaPic() = service.getCaptchaPic().await()

    suspend fun getGrades() = service.getGrades().await()

    suspend fun getRank() = service.getRank().await()


    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine {
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) it.resume(body)
                    else it.resumeWithException(RuntimeException("Response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    it.resumeWithException(t)
                }
            })
        }
    }


}