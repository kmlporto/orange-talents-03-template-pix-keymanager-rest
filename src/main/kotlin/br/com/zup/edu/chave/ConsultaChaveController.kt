package br.com.zup.edu.chave

import br.com.zup.edu.ConsultaChaveRequest
import br.com.zup.edu.KeyManagerConsultaServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import java.util.*
import javax.inject.Inject

@Controller("/api/clients/{clientId}/keys/{keyId}")
class ConsultaChaveController(@Inject val grpcClient: KeyManagerConsultaServiceGrpc.KeyManagerConsultaServiceBlockingStub) {

    @Get
    fun consulta(clientId: UUID, keyId: UUID): HttpResponse<Any>{
        val consultaChaveResponse = grpcClient.consulta(
            ConsultaChaveRequest.newBuilder()
                .setByClient(
                    ConsultaChaveRequest
                        .ByClient.newBuilder()
                        .setClientId(clientId.toString())
                        .setPixId(keyId.toString())
                )
                .build()
        )
        val response = ConsultaResponse(consultaChaveResponse)

        return HttpResponse.ok(response)

    }

}