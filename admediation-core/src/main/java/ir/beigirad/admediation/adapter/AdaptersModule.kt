package ir.beigirad.admediation.adapter

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val wrappersModule = module {
    factoryOf(::AdMobAdapterFactory)
    factoryOf(::TapsellAdapterFactory)

    // provide adapters cause koin has no multibinding feature
    factory<Set<AdMediationAdapter.Factory>> {
        setOf(
            get<AdMobAdapterFactory>(),
            get<TapsellAdapterFactory>(),
        )
    }

    singleOf(::AdMediationAdapterIterator)
}