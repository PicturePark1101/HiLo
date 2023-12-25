package ddwucom.moblie.hilo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ddwucom.moblie.hilo.data.model.entity.FitnessLocation
import ddwucom.moblie.hilo.data.repository.FitnessLocationRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FitnessLocationViewModel @Inject constructor(var fitnessLocationRepository: FitnessLocationRepository) : ViewModel() {

    var allLocation: LiveData<List<FitnessLocation>> = fitnessLocationRepository.allFitnessLocation.asLiveData()

    fun addLocation(fitnessLocation: FitnessLocation) = viewModelScope.launch {
        fitnessLocationRepository.addLocation(fitnessLocation)
    }

    fun removeLocation(fitnessLocation: FitnessLocation) = viewModelScope.launch {
        fitnessLocationRepository.removeLocation(fitnessLocation)
    }
}
