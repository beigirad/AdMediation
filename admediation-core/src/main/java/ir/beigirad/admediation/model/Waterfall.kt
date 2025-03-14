package ir.beigirad.admediation.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Waterfall(
    val name: String,
    @SerialName("id")
    val zoneId: String,
)