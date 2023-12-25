package ddwucom.moblie.hilo.data.repository

import dagger.hilt.android.lifecycle.HiltViewModel
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
        fitnessLocationDao.deleteFood(location)
    }
}
