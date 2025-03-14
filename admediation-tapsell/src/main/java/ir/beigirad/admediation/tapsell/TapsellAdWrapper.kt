package ir.beigirad.admediation.tapsell

import ir.beigirad.admediation.model.Ad

data class TapsellAdWrapper(
    override val slug: String,
    val zoneId: String,
    val adId: String,
) : Ad