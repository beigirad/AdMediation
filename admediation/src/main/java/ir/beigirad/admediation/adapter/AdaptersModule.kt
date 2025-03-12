package ir.beigirad.admediation.adapter

import ir.beigirad.admediation.logger.Logger
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module


val admobIdentifier = AdMediationAdapterIdentifier(
    name = "admob",
    packageName = "ir.beigirad.admediation.admob.AdmobAdapter"
)

val wrappersModule = module {
    factory<AdMediationAdapter?>(named(admobIdentifier.name)) {
        runCatching {
            // TODO avoid using reflection in future
            val clazz = Class.forName(admobIdentifier.packageName)
            val constructor = clazz.getDeclaredConstructor()
            constructor.newInstance() as AdMediationAdapter
        }
            .onFailure { Logger.i("admob module didn't found. $it") }
            .getOrNull()
    }

    factory<Map<AdMediationAdapterIdentifier, AdMediationAdapter>> {
        listOfNotNull(
            getOrNull<AdMediationAdapter>(named(admobIdentifier.name))?.let { admobIdentifier to it }
        ).toMap()
    }

    singleOf(::AdMediationAdapterIterator)
}