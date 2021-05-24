package br.com.zup.edu.chave

import br.com.zup.edu.KeyManagerCadastraServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Inject
import javax.validation.Valid

@Controller("/api/clients/{clientId}/keys")
@Validated
class CadastraChaveController (@Inject val grpcClient: KeyManagerCadastraServiceGrpc.KeyManagerCadastraServiceBlockingStub){

    @Post
    fun cadastra(clientId: UUID,  @Valid cadastraRequest: CadastraRequest): HttpResponse<Any> {

        val keyResponse = grpcClient.cadastra(cadastraRequest.paraModeloGrpc(clientId))

        return HttpResponse.created(location(clientId, keyResponse.pixId))

    }

    private fun location(clientId: UUID, pixId: String) = HttpResponse.uri("/api/clients/${clientId}/key/${pixId}")
}