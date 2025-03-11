package ir.beigirad.admediation.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLBuilder
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    single { Json { ignoreUnknownKeys = true } }

    single { URLBuilder("https://mock.tapsell.ir/") }

    single<HttpClient> {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(json = get())
            }
        }
    }

    singleOf(::ApiServiceImpl) bind ApiService::class
}