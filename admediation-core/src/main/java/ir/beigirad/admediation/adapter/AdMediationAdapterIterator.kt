package ir.beigirad.admediation.adapter

import android.content.Context
import ir.beigirad.admediation.logger.Logger
import ir.beigirad.admediation.model.AdNetwork

class AdMediationAdapterIterator(
    private val adaptersFactory: Set<AdMediationAdapter.Factory>,
) {
    suspend fun initializeAdapters(context: Context, adNetworks: List<AdNetwork>) {
        adNetworks.forEach { adNetwork ->
            adaptersFactory.forEach { factory ->
                if (adNetwork.name.equals(factory.slug, ignoreCase = true))
                    factory.create(adNetwork)?.initialize(context)?.also {
                        Logger.i("\"${factory.slug}\" adapter initialized")
                    }
            }
        }
    }
}