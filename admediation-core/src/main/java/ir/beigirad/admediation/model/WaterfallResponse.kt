package ir.beigirad.admediation.model

import kotlinx.serialization.Serializable

@Serializable
data class WaterfallResponse(
    val waterfall: List<Waterfall>,
)