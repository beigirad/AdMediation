package ir.beigirad.admediation.adapter

import android.content.Context
import ir.beigirad.admediation.model.AdNetwork
import ir.beigirad.admediation.model.Either
import ir.beigirad.admediation.model.Ad

interface AdMediationAdapter {
    suspend fun initialize(context: Context)
    suspend fun requestAd(context: Context, zoneId: String): Either<Ad>

    interface Factory {
        val slug: String
        fun create(adNetwork: AdNetwork): AdMediationAdapter?
    }

    data class Config(val data: Map<String, String>)
}