package me.gyanesh.hdp.data.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import me.gyanesh.hdp.HDPApp
import me.gyanesh.hdp.R
import me.gyanesh.hdp.util.getString
import java.text.SimpleDateFormat
import java.util.*

@Entity
@Keep
data class TestReport(
    @PrimaryKey(autoGenerate = true) var roomId: Int? = null,
//    var id: String = UUID.randomUUID().toString(),
    var created_at: String = "",
    var risk: String = "",
    var message: String? = null
) {
    fun getFormattedDate(): String {
        return try {
            val formatter = SimpleDateFormat("hh:mm aa, dd MMM yyy", Locale.getDefault())
            val date = Date(created_at.toLong() / 1000)
            formatter.format(date)
        } catch (e: Exception) {
            ""
        }
    }

    fun getFormattedRisk() =
        when (risk) {
            "0" -> getString(R.string.no)
            "1" -> getString(R.string.yes)
            else -> getString(R.string.unknown)
        }
}