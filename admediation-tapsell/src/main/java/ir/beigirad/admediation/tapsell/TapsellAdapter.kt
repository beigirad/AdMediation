package ir.beigirad.admediation.tapsell

import android.app.Application
import android.content.Context
import ir.beigirad.admediation.adapter.AdMediationAdapter
import ir.beigirad.admediation.logger.Logger
import ir.beigirad.admediation.model.Ad
import ir.beigirad.admediation.model.Either
import ir.tapsell.sdk.Tapsell
import ir.tapsell.sdk.TapsellAdRequestListener
import ir.tapsell.sdk.TapsellAdRequestOptions
import ir.tapsell.sdk.TapsellAdShowListener
import ir.tapsell.sdk.TapsellShowOptions
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

// FIXME: this class is calling by reflection and must be keep by proguard-rules
class TapsellAdapter(private val config: AdMediationAdapter.Config) : AdMediationAdapter {
    override suspend fun initialize(context: Context) {
        val tapselKey = requireNotNull(config.data["tapsell_key"]) { "not found \"tapsell_key\"" }
        Tapsell.initialize(context.applicationContext as Application, tapselKey)
    }

    override suspend fun requestAd(context: Context, slug: String, zoneId: String): Either<Ad> =
        suspendCoroutine<Either<Ad>> { continuation ->
            Tapsell.requestAd(
                context,
                zoneId,
                TapsellAdRequestOptions(),
                object : TapsellAdRequestListener() {
                    override fun onError(p0: String?) {
                        super.onError(p0)
                        Logger.d("tapsell ad loading has issue. $p0")
                        continuation.resume(Either.Failure(p0.orEmpty()))
                    }

                    override fun onAdAvailable(adId: String) {
                        Logger.d("tapsell ad was loaded.")
                        continuation.resume(
                            Either.Success(
                                TapsellAdWrapper(
                                    slug = slug,
                                    zoneId = zoneId,
                                    adId = adId,
                                )
                            )
                        )
                    }
                }
            )
        }

    override suspend fun showAd(context: Context, ad: Ad): Either<Unit> {
        check(ad is TapsellAdWrapper) { "incompatible ad passed to tapsel adapter. $ad" }
        return suspendCoroutine { continuation ->
            Tapsell.showAd(
                context,
                ad.zoneId,
                ad.adId,
                TapsellShowOptions(),
                object : TapsellAdShowListener() {
                    override fun onOpened() {
                        Logger.d("tapsell ad onOpened")
                    }

                    override fun onClosed() {
                        Logger.d("tapsell ad onClosed")
                    }

                    override fun onAdClicked() {
                        Logger.d("tapsell ad onAdClicked")
                    }

                    override fun onError(message: String) {
                        Logger.d("tapsell ad onError($message)")
                        continuation.resume(Either.Failure(message))
                    }

                    override fun onRewarded(completed: Boolean) {
                        Logger.d("tapsell ad onOpened($completed)")
                        continuation.resume(Either.Success(Unit))
                    }
                }
            )
        }
    }
}