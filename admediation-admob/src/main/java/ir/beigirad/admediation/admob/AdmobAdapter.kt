package ir.beigirad.admediation.admob

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import ir.beigirad.admediation.logger.Logger
import ir.beigirad.admediation.adapter.AdMediationAdapter
import ir.beigirad.admediation.model.Either
import ir.beigirad.admediation.model.Ad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

// FIXME: this class is calling by reflection and must be keep by proguard-rules
class AdmobAdapter : AdMediationAdapter {
    override suspend fun initialize(context: Context) {
        withContext(Dispatchers.Default) {
            MobileAds.initialize(context) {
            }
        }
    }

    override suspend fun requestAd(context: Context, zoneId: String): Either<Ad> =
        withContext(Dispatchers.Main) {
            suspendCoroutine<Either<Ad>> { continuation ->
                RewardedAd.load(
                    context,
                    zoneId,
                    AdRequest.Builder().build(),
                    object : RewardedAdLoadCallback() {
                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            Logger.d("admob ad has issue. $adError")
                            continuation.resume(Either.Failure(adError.message))
                        }

                        override fun onAdLoaded(ad: RewardedAd) {
                            Logger.d("admob ad was loaded.")
                            continuation.resume(Either.Success(Ad()))
                        }
                    }
                )
            }
        }
}