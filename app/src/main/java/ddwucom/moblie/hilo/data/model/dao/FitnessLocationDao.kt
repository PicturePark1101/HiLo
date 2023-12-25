package ddwucom.moblie.hilo.data.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ddwucom.moblie.hilo.data.model.entity.FitnessLocation
import kotlinx.coroutines.flow.Flow

@Dao
interface FitnessLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(vararg loc: FitnessLocation)

    @Update
    suspend fun updateLocation(loc: FitnessLocation)

    @Delete
    suspend fun deleteFood(loc: FitnessLocation)

    // 이미 존재하는지 검사
    @Query("SELECT * FROM fitness_location_table WHERE name=:name AND address=:address")
    fun searchLocation(name: String, address: String): List<FitnessLocation>

    @Query("SELECT * FROM fitness_location_table")
    fun getAllLocationList(): Flow<List<FitnessLocation>>
}
