package ddwucom.moblie.hilo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ddwucom.moblie.hilo.data.repository.MyFitnessShRepository
import javax.inject.Inject

@HiltViewModel
class MyFitnessShViewModel @Inject constructor(
    private val myFitnessShRepository: MyFitnessShRepository,
) : ViewModel() {

    private val _allData = MutableLiveData<Map<String, *>>()
    val allData: LiveData<Map<String, *>> get() = _allData

    init {
        _allData.value = myFitnessShRepository.getAllData()
    }

    fun readDate(key: String) {
        _allData.value = myFitnessShRepository.getAllData()
    }

    fun deleteData(key: String) {
        myFitnessShRepository.deleteData(key)
        _allData.value = myFitnessShRepository.getAllData()
    }

    // 데이터를 저장하는 메서드
    fun saveData(key: String, value: String) {
        myFitnessShRepository.saveData(key, value)
        _allData.value = myFitnessShRepository.getAllData()
    }
}
