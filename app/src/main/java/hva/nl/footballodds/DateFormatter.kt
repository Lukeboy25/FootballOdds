package hva.nl.footballodds

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class DateFormatter {
    companion object {
        fun getCorrectDate(date: Double) : LocalDateTime {
            val dateIso = DateTimeFormatter.ISO_INSTANT
                .format(java.time.Instant.ofEpochSecond(date.toLong()))
            val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

            return LocalDateTime.parse(dateIso, dateFormat).plusHours(1)
        }

        fun formatDutchDateShort(date : LocalDateTime) : String {
            var minutes = date.minute.toString()
            if(date.minute == 0) {
                minutes = "00"
            }

            return date.dayOfMonth.toString() + "-" + date.monthValue + " " + date.hour + ":" + minutes
        }

        fun formatDutchDateLong(date : LocalDateTime) : String {
            var minutes = date.minute.toString()
            if(date.minute == 0) {
                minutes = "00"
            }

            return date.dayOfMonth.toString() + "-" + date.monthValue + "-" + date.year + " " + date.hour + ":" + minutes
        }
    }
}