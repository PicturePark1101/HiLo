package ddwucom.moblie.hilo.data.repository

import android.util.Log
import ddwucom.moblie.hilo.data.model.dao.FitnessLocationDao
import ddwucom.moblie.hilo.data.model.entity.FitnessLocation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FitnessLocationRepository @Inject constructor(private val fitnessLocationDao: FitnessLocationDao) {

    val allFitnessLocation: Flow<List<FitnessLocation>> = fitnessLocationDao.getAllLocationList()

    suspend fun addLocation(location: FitnessLocation) {
        fitnessLocationDao.insertLocation(location)
    }

    suspend fun removeLocation(location: FitnessLocation) {
        try {
            fitnessLocationDao.deleteFood(location)
            Log.e("FitnessLocationDao", "삭제 완 $location")

        } catch (e: Exception) {
            Log.e("FitnessLocationDao", "Error deleting location: $location", e)
        }
    }

//    suspend fun removeLoc(name : String, address: String) {
//        try {
//            fitnessLocationDao.deleteLoc(name, address)
//            Log.e("FitnessLocationDao", "삭제 완 $name, $address")
//
//        } catch (e: Exception) {
//            Log.e("FitnessLocationDao", "Error deleting location: $name, $address", e)
//        }
//    }
}
