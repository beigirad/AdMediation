package ir.beigirad.admediation.tapsell

import android.app.Application
import android.content.Context
import ir.beigirad.admediation.adapter.AdMediationAdapter
import ir.beigirad.admediation.logger.Logger
import ir.tapsell.sdk.Tapsell

// FIXME: this class is calling by reflection and must be keep by proguard-rules
class TapsellAdapter(private val config: AdMediationAdapter.Config) : AdMediationAdapter {
    override suspend fun initialize(context: Context) {
        val tapselKey = requireNotNull(config.data["tapsell_key"]) { "not found \"tapsell_key\"" }
        Tapsell.initialize(context.applicationContext as Application, tapselKey)
    }
}