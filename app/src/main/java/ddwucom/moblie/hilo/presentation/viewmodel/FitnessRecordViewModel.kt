package ddwucom.moblie.hilo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ddwucom.moblie.hilo.data.model.entity.FitnessRecord
import ddwucom.moblie.hilo.data.repository.FitnessRecordRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FitnessRecordViewModel @Inject constructor(var fitnessRecordRepository: FitnessRecordRepository) : ViewModel() {
    var allLocation: LiveData<List<FitnessRecord>> = fitnessRecordRepository.allFitnessLocation.asLiveData()

    fun addLocation(fitnessRecord: FitnessRecord) = viewModelScope.launch {
        fitnessRecordRepository.addLocation(fitnessRecord)
    }

    fun removeLocation(fitnessRecord: FitnessRecord) = viewModelScope.launch {
        fitnessRecordRepository.removeLocation(fitnessRecord)
    }
    fun updateLocation(fitnessRecord: FitnessRecord) = viewModelScope.launch {
        fitnessRecordRepository.updateLocation(fitnessRecord)
    }
}
