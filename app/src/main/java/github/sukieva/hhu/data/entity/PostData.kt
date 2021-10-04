package github.sukieva.hhu.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PostData(
    var maccount: String,
    var mwid: String,
    var mname: String,
    var maid: String,
    var minstitute: String,
    var mgrade: String,
    var mclass: String,
    var mbuilding: String,
    var mroom: String,
    var mphone: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
