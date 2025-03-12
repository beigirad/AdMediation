package ir.beigirad.admediation.adapter

import android.content.Context
import ir.beigirad.admediation.logger.Logger
import ir.beigirad.admediation.model.AdNetwork

class AdMediationAdapterIterator(
    private val adapters: Map<AdMediationAdapterIdentifier, AdMediationAdapter>,
) {
    suspend fun initializeAdapters(context: Context, adNetworks: List<AdNetwork>) {
        adNetworks.forEach { adNetwork ->
            val foundAdapterIdentifier =
                adapters.keys.find { it.name.equals(adNetwork.name, ignoreCase = true) }
            adapters[foundAdapterIdentifier]?.initialize(context)?.also {
                Logger.i("\"${foundAdapterIdentifier?.name}\" adapter initialized")
            }
        }
    }
}