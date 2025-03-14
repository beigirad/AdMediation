package ir.beigirad.admediation.adapter.factories

import ir.beigirad.admediation.adapter.AdMediationAdapter
import ir.beigirad.admediation.logger.Logger
import ir.beigirad.admediation.model.AdNetwork

class AdMobAdapterFactory : AdMediationAdapter.Factory {
    override val slug: String = "admob"

    private val adapterClass = "ir.beigirad.admediation.admob.AdmobAdapter"

    override fun isAvailable(): Boolean =
        runCatching { Class.forName(adapterClass) }.isSuccess

    override fun create(adNetwork: AdNetwork): AdMediationAdapter? =
        runCatching {
            // TODO avoid using reflection in future
            val clazz = Class.forName(adapterClass)
            val constructor = clazz.getDeclaredConstructor()
            constructor.newInstance() as AdMediationAdapter
        }
            .onFailure { Logger.i("admob module didn't found. $it") }
            .getOrNull()
}