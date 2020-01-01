package hva.nl.footballodds.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import hva.nl.footballodds.model.Club

@Dao
interface ClubDao {
    @Query("SELECT * FROM club_table")
    fun getAllClubs(): LiveData<List<Club>>

    @Delete
    fun deleteClub(club: Club)

    @Query("DELETE FROM club_table")
    fun deleteAllClubs()

    @Insert
    suspend fun addClub(club: Club)
}