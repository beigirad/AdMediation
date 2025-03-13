package ir.beigirad.admediation.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    single { Json { ignoreUnknownKeys = true } }

    single<Url> { URLBuilder("https://mock.tapsell.ir/").build() }

    single<HttpClient> {
        HttpClient(
            OkHttp.create {
                addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
            }
        ) {
            install(ContentNegotiation) {
                json(json = get())
            }
        }
    }

    singleOf(::ApiServiceImpl) bind ApiService::class
}