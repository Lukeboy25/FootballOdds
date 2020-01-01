package hva.nl.footballodds.api

import retrofit2.Call
import retrofit2.http.GET

interface FootballApiService {

    @GET("odds/?sport=soccer_netherlands_eredivisie&region=uk&mkt=h2h&apiKey=d18b8c9614d01c66a8039eaaa581c922")
    fun getFootballMatches(): Call<FootballMatchesList>
}