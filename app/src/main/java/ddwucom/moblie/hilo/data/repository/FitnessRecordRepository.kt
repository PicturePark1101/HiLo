package ddwucom.moblie.hilo.data.repository

import android.util.Log
import ddwucom.moblie.hilo.data.model.dao.FitnessRecordDao
import ddwucom.moblie.hilo.data.model.entity.FitnessRecord
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FitnessRecordRepository @Inject constructor(private val fitnessRecordDao: FitnessRecordDao) {

    val allFitnessLocation: Flow<List<FitnessRecord>> = fitnessRecordDao.getAllLocationList()

    suspend fun addLocation(fitnessRecord: FitnessRecord) {
        fitnessRecordDao.insertLocation(fitnessRecord)
    }

    suspend fun removeLocation(fitnessRecord: FitnessRecord) {
        try {
            fitnessRecordDao.deleteFood(fitnessRecord)
            Log.e("FitnessLocationDao", "삭제 완 $fitnessRecord")
        } catch (e: Exception) {
            Log.e("FitnessLocationDao", "Error deleting location: $fitnessRecord", e)
        }
    }

    suspend fun updateLocation(fitnessRecord: FitnessRecord) {
        try {
            fitnessRecordDao.updateLocation(fitnessRecord)
            Log.e("FitnessLocationDao", "삭제 완 $fitnessRecord")
        } catch (e: Exception) {
            Log.e("FitnessLocationDao", "Error deleting location: $fitnessRecord", e)
        }
    }


}
