package ddwucom.moblie.hilo.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "fitness_table"
)
data class FitnessRecord(
    @PrimaryKey(autoGenerate = true)
    val _id: Int,

    @ColumnInfo(name = "fitness_place")
    var placeName: String?,

    @ColumnInfo(name = "fitness_name")
    var fitnessName: String?,

    @ColumnInfo(name = "fitness_date")
    var fitnessDate: String?,

    @ColumnInfo(name = "fitness_memo")
    var fitnessMemo: String?,
) {
    override fun toString(): String {
        return placeName + fitnessName + fitnessDate
    }
}
