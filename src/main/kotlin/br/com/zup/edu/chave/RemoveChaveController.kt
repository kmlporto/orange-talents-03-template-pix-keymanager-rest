package br.com.zup.edu.chave

import br.com.zup.edu.KeyManagerRemoveServiceGrpc
import br.com.zup.edu.RemoveChaveRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import java.util.*
import javax.inject.Inject

@Controller("/api/clients/{clientId}/keys/{keyId}")
class RemoveChaveController(@Inject val grpcClient: KeyManagerRemoveServiceGrpc.KeyManagerRemoveServiceBlockingStub) {

    @Delete
    fun remove(clientId: UUID, keyId: UUID): HttpResponse<Any> {
        grpcClient.remove(RemoveChaveRequest.newBuilder()
            .setClientId(clientId.toString())
            .setPixId(keyId.toString())
            .build())

        return HttpResponse.ok()
    }
}