package ir.beigirad.admediation.adapter

import android.content.Context

interface AdMediationAdapter {
    suspend fun initialize(context: Context)

    interface Factory {
        val slug: String
        fun create(): AdMediationAdapter?
    }
}