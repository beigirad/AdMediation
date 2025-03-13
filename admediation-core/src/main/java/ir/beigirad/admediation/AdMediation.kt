package ir.beigirad.admediation

import android.content.Context
import ir.beigirad.admediation.adapter.AdMediationAdapter
import ir.beigirad.admediation.adapter.wrappersModule
import ir.beigirad.admediation.logger.ILogger
import ir.beigirad.admediation.logger.Logger
import ir.beigirad.admediation.model.Either
import ir.beigirad.admediation.network.ApiService
import ir.beigirad.admediation.network.networkModule
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.koin.dsl.koinApplication

object AdMediation {
    private val koinApp by lazy {
        koinApplication {
            modules(
                networkModule,
                wrappersModule
            )
        }.koin
    }

    private val apiService by koinApp.inject<ApiService>()
    private val adapterFactories by koinApp.inject<Set<AdMediationAdapter.Factory>>()
    private val cachedAdapters = mutableMapOf<String, AdMediationAdapter>()

    @JvmStatic
    fun configure(logger: ILogger) {
        Logger.addPrinter(logger)
    }

    @JvmStatic
    fun initialize(context: Context) {
        Logger.i("start initializing")
        runBlocking {
            val adNetworks = apiService.getAdNetworks().also { Logger.d("adNetworks: $it") }

            if (adNetworks is Either.Failure) return@runBlocking
            adNetworks as Either.Success

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
        }
    }
}