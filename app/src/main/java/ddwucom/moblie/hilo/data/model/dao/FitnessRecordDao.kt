package ddwucom.moblie.hilo.data.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ddwucom.moblie.hilo.data.model.entity.FitnessRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface FitnessRecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(vararg fit: FitnessRecord)

    @Update
    suspend fun updateLocation(fit: FitnessRecord)

    @Delete
    suspend fun deleteFood(fit: FitnessRecord)

//
//    // 이미 존재하는지 검사
//    @Query("SELECT * FROM fitness_table WHERE name=:name AND address=:address")
//    fun searchLocation(name: String, address: String): List<FitnessLocation>

    @Query("SELECT * FROM fitness_table")
    fun getAllLocationList(): Flow<List<FitnessRecord>>
}
