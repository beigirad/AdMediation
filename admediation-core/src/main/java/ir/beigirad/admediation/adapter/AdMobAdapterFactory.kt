package ir.beigirad.admediation.adapter

import ir.beigirad.admediation.logger.Logger

class AdMobAdapterFactory : AdMediationAdapter.Factory {
    override val slug: String = "admob"

    override fun create(): AdMediationAdapter? =
        runCatching {
            // TODO avoid using reflection in future
            val clazz = Class.forName("ir.beigirad.admediation.admob.AdmobAdapter")
            val constructor = clazz.getDeclaredConstructor()
            constructor.newInstance() as AdMediationAdapter
        }
            .onFailure { Logger.i("admob module didn't found. $it") }
            .getOrNull()
}