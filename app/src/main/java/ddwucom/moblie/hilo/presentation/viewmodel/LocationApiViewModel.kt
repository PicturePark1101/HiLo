package ddwucom.moblie.hilo.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ddwucom.moblie.hilo.data.LocDto
import ddwucom.moblie.hilo.data.model.request.LocationApiRequest
import ddwucom.moblie.hilo.data.model.response.LocationApiResponse
import ddwucom.moblie.hilo.data.repository.LocationApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LocationApiViewModel @Inject
constructor(private val repository: LocationApiRepository) : ViewModel() {

    val TAG = "LocationApiViewModel"

    private val _liveData = MutableLiveData<ArrayList<LocDto>>()
    val liveData: LiveData<ArrayList<LocDto>> get() = _liveData

    // 매개변수로 데이터를 받아오도록 바꾸기..
    fun loadData(locationApiRequest: LocationApiRequest) {
        Log.d(TAG, "호출이 왜 안되는지?")
        Log.d(TAG, "${locationApiRequest.city}호출이 왜 안되는지?")
        Log.d(TAG, "${locationApiRequest.town}호출이 왜 안되는지?")

        viewModelScope.launch {
            Log.d(TAG, "요청시작")

            val response: Response<LocationApiResponse> = withContext(Dispatchers.IO) {
                repository.getLocationInfo(locationApiRequest)
            }

            when (response.isSuccessful) {
                true -> {
                    Log.d(TAG, response.toString())
                    Log.d(TAG, response.body().toString())

                    val locs = ArrayList<LocDto>()

                    var itemList = response.body()?.response?.body?.items?.itemList

                    if (itemList != null) {
                        itemList.forEach {
                            if (it.faciStatNm != null) {
                                if (!it.faciStatNm.equals("폐업")) {
                                    Log.d(
                                        TAG,
                                        it.address + it.locName + it.lat + it.lot + it.locType,
                                    )
                                    if (it.address != null && it.locName != null && it.lat != null && it.lot != null && it.locType != null) {
                                        locs.add(
                                            LocDto(
                                                address = it.address,
                                                locName = it.locName,
                                                lat = it.lat,
                                                lot = it.lot,
                                                locType = it.locType,
                                            ),
                                        )
                                    }
                                }
                            }
                        }
                    }

                    _liveData.postValue(locs)
                }
                else -> {
                    Log.d(TAG, "통신오류~")
                }
            }
        }
    }

//    override fun onCleared() {
//        coroutineScope.cancel()
//    }
    fun clearData() {
        _liveData.value = ArrayList()
    }
}
