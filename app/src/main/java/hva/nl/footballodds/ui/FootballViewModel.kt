package hva.nl.footballodds.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import hva.nl.footballodds.api.FootballMatchesList
import hva.nl.footballodds.database.FootballRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FootballViewModel(application: Application) : AndroidViewModel(application) {
    private val footballRepository = FootballRepository()

    val error = MutableLiveData<String>()
    val matches = MutableLiveData<FootballMatchesList>()

    fun getFootballMatches() {
        footballRepository.getFootballMatches().enqueue(object : Callback<FootballMatchesList?> {
            override fun onResponse(
                call: Call<FootballMatchesList?>,
                response: Response<FootballMatchesList?>
            ) {
                if (response.isSuccessful) {
                    matches.value = response.body()
                } else {
                    error.value = "An error occurred: ${response.errorBody().toString()}"
                }
            }

            override fun onFailure(call: Call<FootballMatchesList?>, t: Throwable) {
                error.value = t.message
            }
        })
    }
}