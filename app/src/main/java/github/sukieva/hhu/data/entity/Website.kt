package github.sukieva.hhu.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Website(var siteName: String, var siteAddress: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
