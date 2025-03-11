package ir.beigirad.admediation

import android.util.Log
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
    fun initialize() {
        runBlocking {
            Log.i("AdMediation", apiService.getAdNetworks().toString())
        }
    }
}