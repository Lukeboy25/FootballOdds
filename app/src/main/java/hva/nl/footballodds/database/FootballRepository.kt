package hva.nl.footballodds.database

import hva.nl.footballodds.api.FootballApi
import hva.nl.footballodds.api.FootballApiService

class FootballRepository {
    private val footballApi: FootballApiService = FootballApi.createApi()

    fun getFootballMatches() = footballApi.getFootballMatches()
}