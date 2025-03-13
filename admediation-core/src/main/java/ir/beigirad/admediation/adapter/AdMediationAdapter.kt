package ir.beigirad.admediation.adapter

import android.content.Context
import ir.beigirad.admediation.model.AdNetwork

interface AdMediationAdapter {
    suspend fun initialize(context: Context)

    interface Factory {
        val slug: String
        fun create(adNetwork: AdNetwork): AdMediationAdapter?
    }

    data class Config(val data: Map<String, String>)
}