package ir.beigirad.admediation

import android.content.Context
import ir.beigirad.admediation.logger.ILogger
import ir.beigirad.admediation.logger.Logger
import ir.beigirad.admediation.model.Either
import ir.beigirad.admediation.network.ApiService
import ir.beigirad.admediation.network.networkModule
import ir.beigirad.admediation.adapter.AdMediationAdapter
import ir.beigirad.admediation.adapter.AdMediationAdapterIterator
import ir.beigirad.admediation.adapter.wrappersModule
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
    private val adapterIterator by koinApp.inject<AdMediationAdapterIterator>()

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
            adapterIterator.initializeAdapters(context, adNetworks.data)
        }
    }
}