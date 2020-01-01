package hva.nl.footballodds.api

class FootballMatchesList {
    val data: Array<Matches>? = null

    class Matches {
        val teams = arrayListOf<String>()
        val commence_time: Double = 0.0
        val sites : Array<Sites>? = null
        val sites_count : Int? = null

        class Sites {
            val odds = Odds()

            class Odds {
               val h2h = arrayListOf<Float>()
            }
        }
    }
}
