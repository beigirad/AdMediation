package ir.beigirad.admediation.model

import kotlinx.serialization.Serializable

@Serializable
data class AdNetwork(
    val name: String,
    val id: String,
)