package hva.nl.footballodds.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FootballApi {
    companion object {
        private const val baseUrl = "https://api.the-odds-api.com/v3/"

        /**
         * @return [FootballApiService] The service class off the retrofit client.
         */
        fun createApi(): FootballApiService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            val footballApi = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return footballApi.create(FootballApiService::class.java)
        }
    }
}