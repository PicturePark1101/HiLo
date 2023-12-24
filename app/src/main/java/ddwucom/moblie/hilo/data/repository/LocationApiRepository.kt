package ddwucom.moblie.hilo.data.repository

import ddwucom.moblie.hilo.data.model.request.LocationApiRequest
import ddwucom.moblie.hilo.data.model.response.LocationApiResponse
import ddwucom.moblie.hilo.data.service.LocationApiService
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class LocationApiRepository @Inject constructor(private val locationApiService: LocationApiService) {

    suspend fun getLocationInfo(locationRequest: LocationApiRequest): Response<LocationApiResponse> =
        locationApiService.getLocationInfo(
            locationRequest.serviceKey,
            locationRequest.pageNo,
            locationRequest.numOfRows,
            locationRequest.resultType,
            locationRequest.city,
            locationRequest.town,
        )
}
