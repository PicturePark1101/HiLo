package ddwucom.moblie.hilo.data.model.request

import retrofit2.http.Query

data class LocationApiRequest(

    @Query("serviceKey")
    val serviceKey: String,

    @Query("pageNo")
    var pageNo: String,

    @Query("numOfRows")
    var numOfRows: String,

    @Query("resultType")
    var resultType: String,

    @Query("cp_nm")
    var city: String,

    @Query("cpb_nm")
    var town: String,
)
