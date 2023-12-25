package ddwucom.moblie.hilo.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fitness_location_table")
data class FitnessLocation(
    @PrimaryKey(autoGenerate = true)
    val _id: Int,

    @ColumnInfo(name = "name")
    var name: String?,

    @ColumnInfo(name = "address")
    var address: String?,

    @ColumnInfo(name = "lat")
    var lat: String?,

    @ColumnInfo(name = "lot")
    var lot: String?,

    @ColumnInfo(name = "locType")
    var locType: String?,

    @ColumnInfo(name = "regDate")
    var regDate: String?,
) {
    override fun toString(): String {
        return super.toString()
    }
}
