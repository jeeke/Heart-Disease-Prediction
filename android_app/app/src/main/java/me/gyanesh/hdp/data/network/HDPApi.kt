package me.gyanesh.hdp.data.network


import androidx.annotation.Keep
import me.gyanesh.hdp.BuildConfig
import me.gyanesh.hdp.data.model.Article
import me.gyanesh.hdp.data.model.TestInput
import me.gyanesh.hdp.data.model.TestReport
import me.gyanesh.hdp.data.model.Video
import me.gyanesh.hdp.ui.BaseActivity
import me.gyanesh.hdp.util.BACKEND_URL
import me.gyanesh.hdp.util.ML_PREDICTOR_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

@Keep
interface HDPApi {

    @GET("articles")
    suspend fun getAllArticles(@Query("lang") language: String): Response<List<Article>>

    @GET("videos")
    suspend fun getAllVideos(@Query("lang") language: String): Response<List<Video>>

    @POST(ML_PREDICTOR_URL)
    suspend fun predictRisk(@Body testInput: TestInput): Response<TestReport>

//    ----------------------------Driver Code------------------------------

    companion object {
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor): HDPApi {

//            val authHeaderInterceptor = Interceptor { chain ->
//                val request = chain.request()
//                val token = NblikApp.currentUser()?.authentication_token
//                Log.d("Token hi ye", "intercept: $token")
//                val newRequest =
//                    if (token == null || request.header("No-Authentication") != null) {
//                        request.newBuilder().removeHeader("No-Authentication").build()
//                    } else if (request.header("Authorization") == null) {
//                        request.newBuilder().header("Authorization", token).build()
//                    } else request
//                chain.proceed(newRequest)
//            }

            val builder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(loggingInterceptor)
            }
            val okkHttpclient = builder
//                .addInterceptor(authHeaderInterceptor)
                .addInterceptor(networkConnectionInterceptor)
                .callTimeout(2, TimeUnit.MINUTES)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl(BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HDPApi::class.java)
        }
    }
}