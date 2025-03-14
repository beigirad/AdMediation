package ir.beigirad.admediation.cache

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val cacheModule = module {
    singleOf(::AdPool)
}