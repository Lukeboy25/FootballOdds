package hva.nl.footballodds.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import hva.nl.footballodds.database.ClubRepository
import hva.nl.footballodds.model.Club
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClubViewModel(application: Application) : AndroidViewModel(application) {
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val clubRepository = ClubRepository(application.applicationContext)

    val clubs: LiveData<List<Club>> = clubRepository.getAllClubs()
    val favoriteClubs: LiveData<List<Club>> = clubRepository.getFavoriteClubs()

    fun addClub(club: Club) {
        ioScope.launch {
            clubRepository.addClub(club)
        }
    }

    fun deleteClub(club: Club) {
        ioScope.launch {
            clubRepository.deleteClub(club)
        }
    }

    fun deleteAllClubs() {
        ioScope.launch {
            clubRepository.deleteAllClubs()
        }
    }

    fun triggerFavoriteClub(clubName: String, isFavorite: Boolean) {
        ioScope.launch {
            clubRepository.triggerFavoriteClub(clubName, isFavorite)
        }
    }
}