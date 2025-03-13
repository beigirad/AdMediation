package ir.beigirad.admediation.adapter.factories

import ir.beigirad.admediation.adapter.AdMediationAdapter
import ir.beigirad.admediation.logger.Logger
import ir.beigirad.admediation.model.AdNetwork

class TapsellAdapterFactory : AdMediationAdapter.Factory {
    override val slug: String = "tapsell"

    override fun create(adNetwork: AdNetwork): AdMediationAdapter? =
        runCatching {
            // TODO avoid using reflection in future
            val clazz = Class.forName("ir.beigirad.admediation.tapsell.TapsellAdapter")
            val constructor = clazz.getDeclaredConstructor(
                AdMediationAdapter.Config::class.java
            )
            val config = AdMediationAdapter.Config(mapOf("tapsell_key" to adNetwork.id))
            constructor.newInstance(config) as AdMediationAdapter
        }
            .onFailure { Logger.i("\"tapsell\" module didn't found. $it") }
            .getOrNull()
}