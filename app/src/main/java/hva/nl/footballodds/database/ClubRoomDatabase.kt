package hva.nl.footballodds.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hva.nl.footballodds.model.Club

@Database(entities = [Club::class], version = 1, exportSchema = false)
abstract class ClubRoomDatabase : RoomDatabase() {

    abstract fun clubDao(): ClubDao

    companion object {
        private const val DATABASE_NAME = "CLUB_DATABASE"

        @Volatile
        private var clubRoomDataBase: ClubRoomDatabase? = null

        fun getDatabase(context: Context): ClubRoomDatabase? {
            if (clubRoomDataBase == null) {
                synchronized(ClubRoomDatabase::class.java) {
                    if (clubRoomDataBase == null) {
                        clubRoomDataBase =
                            Room.databaseBuilder(
                                context.applicationContext,
                                ClubRoomDatabase::class.java,
                                DATABASE_NAME
                            ).build()
                    }
                }
            }
            return clubRoomDataBase
        }
    }
}