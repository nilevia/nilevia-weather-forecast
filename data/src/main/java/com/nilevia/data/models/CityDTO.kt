package com.nilevia.data.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "city")
data class CityDTO(

    @PrimaryKey
    @SerializedName("id")
    var id: String = "",

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("lat")
    var lat: String? = null,

    @SerializedName("lon")
    var lon: String? = null,

    @Ignore
    @SerializedName("country")
    var country: String? = null,

    @Ignore
    @SerializedName("state")
    var state: String? = null,

    @Ignore
    @SerializedName("local_names")
    var localName: LocalNames? = null

)
data class LocalNames(
    @SerializedName("id")
    var id: String? = null
)
