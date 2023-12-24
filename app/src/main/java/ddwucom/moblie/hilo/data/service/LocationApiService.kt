package ddwucom.moblie.hilo.data.service

import ddwucom.moblie.hilo.data.model.response.LocationApiResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApiService {

    @GET("TODZ_API_SFMS_FACI")
    suspend fun getLocationInfo(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNo: String,
        @Query("numOfRows") numOfRows: String,
        @Query("resultType") resultType: String,
        @Query("cp_nm") city: String,
        @Query("cpb_nm") town: String,
    ): Response<LocationApiResponse>
}
