package github.sukieva.hhu.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["site_name"], unique = true)])
data class Website(
    @ColumnInfo(name = "site_name")
    var siteName: String,
    @ColumnInfo(name = "site_address")
    var siteAddress: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
