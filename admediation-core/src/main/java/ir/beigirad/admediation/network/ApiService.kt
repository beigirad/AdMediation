package ir.beigirad.admediation.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import io.ktor.http.appendPathSegments
import io.ktor.http.clone
import ir.beigirad.admediation.model.AdNetwork
import ir.beigirad.admediation.model.AdNetworksResponse
import ir.beigirad.admediation.model.Either
import ir.beigirad.admediation.model.Waterfall
import ir.beigirad.admediation.model.WaterfallResponse


interface ApiService {
    suspend fun getAdNetworks(): Either<List<AdNetwork>>
    suspend fun getWaterfall(): Either<List<Waterfall>>
}

class ApiServiceImpl(
    private val engine: HttpClient,
    private val baseUrl: Url,
) : ApiService {

    override suspend fun getAdNetworks(): Either<List<AdNetwork>> =
        // todo: handle failed status
        Either.Success(
            engine
                .get(URLBuilder(baseUrl).appendPathSegments("mock/api/mediator/adnets").build())
                .body<AdNetworksResponse>().adNetworks
        )

    override suspend fun getWaterfall(): Either<List<Waterfall>> =
        // todo: handle failed status
        Either.Success(
            engine
                .get(URLBuilder(baseUrl).appendPathSegments("mock/api/mediator/waterfall").build())
                .body<WaterfallResponse>().waterfall
        )
}