package ir.beigirad.admediation.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.appendPathSegments
import ir.beigirad.admediation.model.AdNetwork
import ir.beigirad.admediation.model.AdNetworksResponse
import ir.beigirad.admediation.model.Either


interface ApiService {
    suspend fun getAdNetworks(): Either<List<AdNetwork>>
}

class ApiServiceImpl(
    private val engine: HttpClient,
    private val urlBuilder: URLBuilder,
) : ApiService {

    override suspend fun getAdNetworks(): Either<List<AdNetwork>> =
        // todo: handle failed status
        Either.Success(
            engine
                .get(urlBuilder.appendPathSegments("mock/api/mediator/adnets").build())
                .body<AdNetworksResponse>().adNetworks
        )
}