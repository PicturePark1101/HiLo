package ddwucom.moblie.hilo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ddwucom.moblie.hilo.data.model.dao.FitnessLocationDao
import ddwucom.moblie.hilo.data.model.entity.FitnessLocation

@Database(entities = [FitnessLocation::class], version = 1)
abstract class FitnessLocationDatabase : RoomDatabase() {
    abstract fun fitnessLocationDao(): FitnessLocationDao
}
