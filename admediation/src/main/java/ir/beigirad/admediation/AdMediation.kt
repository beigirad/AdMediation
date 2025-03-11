package ir.beigirad.admediation

import ir.beigirad.admediation.logger.ILogger
import ir.beigirad.admediation.logger.Logger
import ir.beigirad.admediation.network.ApiService
import ir.beigirad.admediation.network.networkModule
import kotlinx.coroutines.runBlocking
import org.koin.dsl.koinApplication

object AdMediation {
    private val koinApp by lazy {
        koinApplication { modules(networkModule) }.koin
    }

    private val apiService by koinApp.inject<ApiService>()

    @JvmStatic
    fun configure(logger: ILogger) {
        Logger.addPrinter(logger)
    }

    @JvmStatic
    fun initialize() {
        Logger.i("start initializing")
        runBlocking {
            apiService.getAdNetworks().toString().also {
                Logger.d("adNetworks: $it")
            }
        }
    }
}