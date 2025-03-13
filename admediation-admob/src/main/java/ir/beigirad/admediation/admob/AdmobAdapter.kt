package ir.beigirad.admediation.admob

import android.content.Context
import com.google.android.gms.ads.MobileAds
import ir.beigirad.admediation.adapter.AdMediationAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// FIXME: this class is calling by reflection and must be keep by proguard-rules
class AdmobAdapter : AdMediationAdapter {
    override suspend fun initialize(context: Context) {
        withContext(Dispatchers.Default) {
            MobileAds.initialize(context) {
            }
        }
    }
}