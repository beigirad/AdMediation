package ir.beigirad.admediation.adapter

import android.content.Context
import ir.beigirad.admediation.model.Ad
import ir.beigirad.admediation.model.AdNetwork
import ir.beigirad.admediation.model.Either

interface AdMediationAdapter {
    suspend fun initialize(context: Context)
    suspend fun requestAd(context: Context, slug: String, zoneId: String): Either<Ad>
    suspend fun showAd(context: Context, ad: Ad): Either<Unit>

    interface Factory {
        val slug: String
        fun create(adNetwork: AdNetwork): AdMediationAdapter?
    }

    data class Config(val data: Map<String, String>)
}