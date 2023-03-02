package com.nilevia.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class City(
    val id: String = "",
    val fullName: String,
    val lat: String,
    val lon: String,
): Parcelable

