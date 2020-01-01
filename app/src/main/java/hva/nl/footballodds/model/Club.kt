package hva.nl.footballodds.model

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import hva.nl.footballodds.R

@Entity(tableName = "club_table")
data class Club (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "image_url")
    @DrawableRes var imageUrl: Int
) {
    companion object {
        val CLUB_NAMES = arrayOf(
            "ADO Den Haag",
            "Ajax",
            "AZ Alkmaar",
            "FC Emmen",
            "Feyenoord",
            "Fortuna Sittard",
            "Groningen",
            "Heerenveen",
            "Heracles Almelo",
            "FC Zwolle",
            "PSV Eindhoven",
            "RKC Waalwijk",
            "Sparta Rotterdam",
            "FC Twente Enschede",
            "FC Utrecht",
            "Vitesse Arnhem",
            "VVV Venlo",
            "Willem II"
        )

        val CLUB_LOGO_IDS = arrayOf(
            R.drawable.logo_groot_adodenhaag,
            R.drawable.logo_groot_ajax,
            R.drawable.logo_groot_az,
            R.drawable.logo_groot_emmen,
            R.drawable.logo_groot_feyenoord,
            R.drawable.logo_groot_fortunasittard,
            R.drawable.logo_groot_groningen,
            R.drawable.logo_groot_heerenveen,
            R.drawable.logo_groot_heracles,
            R.drawable.logo_groot_peczwolle,
            R.drawable.logo_groot_psv,
            R.drawable.logo_groot_rkc,
            R.drawable.logo_groot_sparta,
            R.drawable.logo_groot_twente,
            R.drawable.logo_groot_utrecht,
            R.drawable.logo_groot_vitesse,
            R.drawable.logo_groot_vvvvenlo,
            R.drawable.logo_groot_willem2
        )
    }
}