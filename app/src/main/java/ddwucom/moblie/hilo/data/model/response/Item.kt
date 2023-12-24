package ddwucom.moblie.hilo.data.model.response


import com.google.gson.annotations.SerializedName

data class Item(

    @SerializedName("faci_stat_nm")
    val faciStatNm: String,

    @SerializedName("faci_addr")
    val address: String,

    @SerializedName("faci_nm")
    val locName: String,

    @SerializedName("faci_lat")
    val lat: String,

    @SerializedName("faci_lot")
    val lot: String,

    @SerializedName("fcob_nm")
    val locType: String,

//    @SerializedName("faci_road_addr")
//    val address: String,


    @SerializedName("addr_ctpv_nm") val addrCtpvNm: String,
    @SerializedName("nation_yn") val nationYn: String,
    @SerializedName("faci_daddr") val faciDaddr: String,
    @SerializedName("row_num") val rowNum: Int,
    @SerializedName("base_ymd") val baseYmd: String,
//    @SerializedName("faci_stat_nm") val faciStatNm: String,
    @SerializedName("faci_mng_type_cd") val faciMngTypeCd: String,
    @SerializedName("reg_dt") val regDt: String,
    @SerializedName("inout_gbn_nm") val inoutGbnNm: String,
//    @SerializedName("faci_lat") val faciLat: String,
    @SerializedName("faci_cd") val faciCd: String,
    @SerializedName("faci_zip") val faciZip: String,
    @SerializedName("faci_gfa") val faciGfa: Int,
    @SerializedName("faci_road_daddr") val faciRoadDaddr: String,
    @SerializedName("ftype_nm") val ftypeNm: String,
    @SerializedName("cpb_nm") val cpbNm: String,
    @SerializedName("addr_emd_nm") val addrEmdNm: String,
    @SerializedName("addr_cpb_nm") val addrCpbNm: String,
    @SerializedName("faci_road_zip") val faciRoadZip: String,
//    @SerializedName("faci_lot") val faciLot: String,
    @SerializedName("updt_dt") val updtDt: String,
    @SerializedName("faci_gb_nm") val faciGbNm: String,
    @SerializedName("cp_nm") val cpNm: String,
//    @SerializedName("faci_nm") val faciNm: String,
    @SerializedName("faci_road_addr") val faciRoadAddr: String,
    @SerializedName("atnm_chk_yn") val atnmChkYn: String,
//    @SerializedName("fcob_nm") val fcobNm: String,
//    @SerializedName("faci_addr") val faciAddr: String

)