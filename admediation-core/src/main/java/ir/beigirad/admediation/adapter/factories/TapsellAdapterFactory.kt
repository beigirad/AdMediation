package ir.beigirad.admediation.adapter.factories

import ir.beigirad.admediation.adapter.AdMediationAdapter
import ir.beigirad.admediation.logger.Logger
import ir.beigirad.admediation.model.AdNetwork

class TapsellAdapterFactory : AdMediationAdapter.Factory {
    override val slug: String = "tapsell"

    private val adapterClass = "ir.beigirad.admediation.tapsell.TapsellAdapter"

    override fun isAvailable(): Boolean =
        runCatching { Class.forName(adapterClass) }.isSuccess

    override fun create(adNetwork: AdNetwork): AdMediationAdapter? =
        runCatching {
            val clazz = Class.forName(adapterClass)
            val constructor = clazz.getDeclaredConstructor(
                AdMediationAdapter.Config::class.java
            )
            val config = AdMediationAdapter.Config(mapOf("tapsell_key" to adNetwork.id))
            constructor.newInstance(config) as AdMediationAdapter
        }
            .onFailure { Logger.i("\"tapsell\" module didn't found. $it") }
            .getOrNull()
}