package ddwucom.moblie.hilo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ddwucom.moblie.hilo.data.model.dao.FitnessRecordDao
import ddwucom.moblie.hilo.data.model.entity.FitnessRecord

@Database(entities = [FitnessRecord::class], version = 1)
abstract class FitnessDatabase : RoomDatabase() {
    abstract fun fitnessRecordDao(): FitnessRecordDao
}
