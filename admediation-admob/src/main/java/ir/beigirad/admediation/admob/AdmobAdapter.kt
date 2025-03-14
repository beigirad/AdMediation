package ir.beigirad.admediation.admob

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import ir.beigirad.admediation.adapter.AdMediationAdapter
import ir.beigirad.admediation.logger.Logger
import ir.beigirad.admediation.model.Ad
import ir.beigirad.admediation.model.Either
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

    override suspend fun requestAd(context: Context, slug: String, zoneId: String): Either<Ad> =
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
                            continuation.resume(
                                Either.Success(
                                    AdmobAdWrapper(
                                        slug = slug,
                                        actualAd = ad,
                                    )
                                )
                            )
                        }
                    }
                )
            }
        }

    override suspend fun showAd(context: Context, ad: Ad): Either<Unit> {
        check(ad is AdmobAdWrapper) { "incompatible ad passed to admob adapter. $ad" }
        return suspendCoroutine<Either<Unit>> { continuation ->
            with(ad.actualAd) {
                fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdFailedToShowFullScreenContent(error: AdError) {
                        Logger.d("full-screen ad failed. $error")
                        continuation.resume(Either.Failure(error.message))
                    }
                }
                Logger.d("start showing ad by admob")
                show(context as Activity) {
                    continuation.resume(Either.Success(Unit))
                }
            }
        }
    }
}