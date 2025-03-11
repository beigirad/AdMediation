package ir.beigirad.admediation.model

import kotlinx.serialization.Serializable

@Serializable
data class AdNetworksResponse(
    val adNetworks: List<AdNetwork>,
)