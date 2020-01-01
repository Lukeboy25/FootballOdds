package hva.nl.footballodds.database

import androidx.lifecycle.LiveData
import androidx.room.*
import hva.nl.footballodds.model.Club

@Dao
interface ClubDao {
    @Query("SELECT * FROM club_table")
    fun getAllClubs(): LiveData<List<Club>>

    @Delete
    fun deleteClub(club: Club)

    @Query("DELETE FROM club_table")
    fun deleteAllClubs()

    @Query("UPDATE club_table SET is_favorite = :isFavorite  WHERE name = :clubName")
    fun updateClubTriggerFavorite(clubName: String, isFavorite: Boolean)

    @Insert
    suspend fun addClub(club: Club)
}