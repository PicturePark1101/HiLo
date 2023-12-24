package ddwucom.moblie.hilo.data.model.response


import com.google.gson.annotations.SerializedName

data class Items(
    @SerializedName("item")
    val itemList: List<Item>
)