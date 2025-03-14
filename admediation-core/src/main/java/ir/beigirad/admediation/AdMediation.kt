package ir.beigirad.admediation

import android.content.Context
import ir.beigirad.admediation.adapter.AdMediationAdapter
import ir.beigirad.admediation.adapter.wrappersModule
import ir.beigirad.admediation.cache.AdPool
import ir.beigirad.admediation.cache.cacheModule
import ir.beigirad.admediation.logger.ILogger
import ir.beigirad.admediation.logger.Logger
import ir.beigirad.admediation.model.Either
import ir.beigirad.admediation.network.ApiService
import ir.beigirad.admediation.network.networkModule
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import org.koin.dsl.koinApplication
import kotlin.coroutines.EmptyCoroutineContext

object AdMediation {
    private val mediationScope = CoroutineScope(EmptyCoroutineContext + SupervisorJob())
    private val koinApp by lazy {
        koinApplication {
            modules(
                networkModule,
                wrappersModule,
                cacheModule,
            )
        }.koin
    }

    private val apiService by koinApp.inject<ApiService>()
    private val adapterFactories by koinApp.inject<Set<AdMediationAdapter.Factory>>()
    private val cachedAdapters = mutableMapOf<String, AdMediationAdapter>()
    private val adPool by koinApp.inject<AdPool>()

    @JvmStatic
    fun configure(logger: ILogger) {
        Logger.addPrinter(logger)
    }

    @JvmStatic
    fun initialize(
        context: Context,
        onComplete: () -> Unit,
        onError: (String) -> Unit,
    ) {
        mediationScope.launch(
            CoroutineExceptionHandler { context, t -> onError(t.message.orEmpty()) }
        ) {
            Logger.i("start initializing")
            val adNetworks = apiService.getAdNetworks()

            if (adNetworks is Either.Failure) {
                Logger.i("fetching ad-networks failed! $adNetworks")
                return@launch
            }
            adNetworks as Either.Success
            Logger.i("received ad-networks: ${adNetworks.data}")

            Logger.i(
                adapterFactories.joinToString(
                    prefix = "${adapterFactories.size} mediation adapter found! [",
                    postfix = "]",
                    transform = AdMediationAdapter.Factory::slug
                )
            )

            adNetworks.data.mapNotNull { adNetwork ->
                val factory =
                    adapterFactories.find { adNetwork.name.equals(it.slug, ignoreCase = true) }
                        ?: return@mapNotNull null

                val adapter = factory.create(adNetwork) ?: return@mapNotNull null

                async {
                    Logger.i("${factory.slug} start initializing...")
                    adapter.initialize(context)
                    Logger.i("${factory.slug} adapter initialized.")
                    cachedAdapters.put(factory.slug, adapter)
                }
            }.awaitAll()
            onComplete()
        }
    }

    @JvmStatic
    fun requestAd(
        context: Context,
        onComplete: () -> Unit,
        onError: (String) -> Unit,
    ) {
        mediationScope.launch(
            CoroutineExceptionHandler { context, t -> onError(t.message.orEmpty()) }
        ) {
            Logger.i("request for new ad")
            val waterfall = apiService.getWaterfall()
            if (waterfall is Either.Failure) {
                Logger.d("receiving waterfall has issue: ${waterfall.error}")
                return@launch
            }
            waterfall as Either.Success
            Logger.d("received waterfall: ${waterfall.data}")

            waterfall.data.mapNotNull { drop ->
                val slug = drop.name.lowercase()
                val adapter = cachedAdapters[slug]
                    ?: return@mapNotNull null

                async {
                    Logger.d("$slug requesting for new ad...")
                    val requestAdResult = adapter.requestAd(context, slug, drop.zoneId)
                    Logger.i("$slug received new ad result: $requestAdResult")
                    if (requestAdResult is Either.Success) {
                        adPool.putNewAd(requestAdResult.data)
                    }
                }
            }.awaitAll()
            onComplete()
        }
    }

    @JvmStatic
    fun showAd(
        context: Context,
        onComplete: () -> Unit,
        onError: (String) -> Unit,
    ) {
        Logger.d("start showing ad")
        val candidateAd = adPool.popAd() ?: run {
            Logger.d("there is no prepared ad")
            return
        }

        mediationScope.launch(
            CoroutineExceptionHandler { context, t -> onError(t.message.orEmpty()) }
        ) {
            val showResult = cachedAdapters[candidateAd.slug]
                ?.showAd(context, candidateAd) ?: return@launch

            when (showResult) {
                is Either.Failure -> {
                    Logger.i("showing ad by ${candidateAd.slug} has issue. ${showResult.error}")
                    onError(showResult.error)
                }

                is Either.Success -> {
                    Logger.i("an ad by ${candidateAd.slug} has shown.")
                    onComplete()
                }
            }
        }
    }
}