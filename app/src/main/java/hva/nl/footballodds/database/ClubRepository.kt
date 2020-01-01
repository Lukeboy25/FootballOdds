package hva.nl.footballodds.database

import android.content.Context
import androidx.lifecycle.LiveData
import hva.nl.footballodds.model.Club

class ClubRepository(context: Context) {
    private val clubDao: ClubDao

    init {
        val database =
            ClubRoomDatabase.getDatabase(context)
        clubDao = database!!.clubDao()
    }

    fun getAllClubs(): LiveData<List<Club>> {
        return clubDao.getAllClubs()
    }

    fun deleteClub(club: Club) {
        clubDao.deleteClub(club)
    }

    fun deleteAllClubs() {
        clubDao.deleteAllClubs()
    }

    suspend fun addClub(club: Club) {
        clubDao.addClub(club)
    }

    fun triggerFavoriteClub(clubName: String, isFavorite: Boolean) {
        val newFavoriteValue = !isFavorite
        clubDao.updateClubTriggerFavorite(clubName, newFavoriteValue)
    }
}