package ir.beigirad.admediation.adapter

import android.content.Context

interface AdMediationAdapter {
    suspend fun initialize(context: Context)
}